package me.superckl.betteroceans.common.entity.tile;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.reference.ModFluids;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.RecipeHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.ItemFluidContainer;

public class TileEntityBoatbench extends TileEntity implements IInventory, IFluidHandler{

	public static final int LIQUID_INTAKE_TIME = 80;

	@Getter
	private final ItemStack[] inventory = new ItemStack[6];
	@Getter
	private EntityBOBoat activeSelection;
	@Getter
	@Setter
	private boolean shouldHandleFluids;
	@Getter
	private int cookTime;
	@Getter
	private int partBurnTime;
	@Getter
	private int liquidTime;
	@Getter
	private boolean takingInLiquid;
	@Getter
	@Setter
	private boolean noUseIngredients;
	private final IFluidTank tank = new FluidTank(4000);

	public TileEntityBoatbench() {}

	/**
	 * @param shouldHandleFluids determines if this te will handle lubricants and also check if parts require time to create
	 */
	public TileEntityBoatbench(final boolean shouldHandleFluids) {
		this.shouldHandleFluids = shouldHandleFluids;
	}

	public void setActiveSelection(final EntityBOBoat selection){
		if(selection.getBoatParts().size() != 1)
			throw new IllegalArgumentException("Active selection must be made of only one part!");
		this.activeSelection = selection;
		selection.setRenderWithRotation(true);
		this.checkRecipeCompletion();
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(final int slot) {
		return this.inventory[slot];
	}

	public void onCraftingSlotPick(){
		if(!this.noUseIngredients)
			RecipeHelper.removeItems(this.activeSelection.getBoatParts().get(0).getCraftingIngredients(), this.inventory, true);
		this.noUseIngredients = false;
		this.checkRecipeCompletion();
	}

	@Override
	public ItemStack decrStackSize(final int slot, final int amount) {
		if (this.inventory[slot] != null)
		{
			ItemStack itemstack;

			if (this.inventory[slot].stackSize <= amount)
			{
				itemstack = this.inventory[slot];
				this.inventory[slot] = null;
				this.markDirty();
				if(slot == 5)
					this.onCraftingSlotPick();
				else
					this.checkRecipeCompletion();
				return itemstack;
			}
			else
			{
				itemstack = this.inventory[slot].splitStack(amount);

				if (this.inventory[slot].stackSize == 0){
					if(slot == 5)
						this.onCraftingSlotPick();
					this.inventory[slot] = null;
				}

				this.markDirty();
				this.checkRecipeCompletion();
				return itemstack;
			}
		} else{
			this.checkRecipeCompletion();
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int slot) {
		if (this.inventory[slot] != null)
		{
			final ItemStack itemstack = this.inventory[slot];
			this.inventory[slot] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(final int slot, final ItemStack stack) {
		this.inventory[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();

		this.markDirty();
		if(this.activeSelection != null)
			this.checkRecipeCompletion();
	}

	public boolean checkRecipeCompletion(){
		if(this.activeSelection == null){
			this.inventory[5] = null;
			return false;
		}
		if(RecipeHelper.areItemsPresent(this.activeSelection.getBoatParts().get(0).getCraftingIngredients(), Arrays.copyOf(this.inventory, 3), true)){
			final BoatPart part = this.activeSelection.getBoatParts().get(0);
			if(part.getCreationTime() <= 0 || this.partBurnTime != 0 && this.partBurnTime <= this.cookTime)
				this.inventory[5] = part.getCraftingResult();
			else
				this.inventory[5] = null;
			return true;
		}else
			this.inventory[5] = null;
		return false;
	}

	/**
	 * Begins crafting the active selection
	 * @return Whether or not the crafting was successfully started.
	 */
	public boolean beginCrafting(){
		final BoatPart part = this.activeSelection.getBoatParts().get(0);
		if(part.getCreationTime() <= 0 || this.cookTime > 0 || this.tank.getFluid() == null || this.inventory[5] != null || !this.checkRecipeCompletion())
			return false;
		final int requiredF = ModFluids.getFuelUsageFor(this.tank.getFluid().getFluid(), part);
		if(this.tank.getFluid().amount < requiredF)
			return false;
		final FluidStack amount = this.tank.drain(requiredF, true);
		if(amount.amount < requiredF){
			this.tank.fill(amount, true);
			return false;
		}
		this.partBurnTime = part.getCreationTime();
		RecipeHelper.removeItems(part.getCraftingIngredients(), this.inventory, true);
		return true;
	}

	@Override
	public String getInventoryName() {
		return "Basic Workbench";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(final int slot, final ItemStack stack) {
		if(slot >= 4)
			return false;
		if(slot == 3)
			return this.shouldHandleFluids && FluidContainerRegistry.isContainer(stack);

		return true;
	}



	@Override
	public int fill(final ForgeDirection from, final FluidStack resource, final boolean doFill) {
		if(!this.shouldHandleFluids || !ModFluids.isFuel(resource.getFluid()))
			return 0;
		return this.tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(final ForgeDirection from, final FluidStack resource, final boolean doDrain) {
		if(!this.shouldHandleFluids || this.tank.getFluid().getFluid() != resource.getFluid())
			return null;
		return this.tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(final ForgeDirection from, final int maxDrain, final boolean doDrain) {
		if(!this.shouldHandleFluids)
			return null;
		return this.tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(final ForgeDirection from, final Fluid fluid) {
		if(!this.shouldHandleFluids || !ModFluids.isFuel(fluid))
			return false;
		return this.tank.getFluid().getFluid() == fluid && this.tank.getCapacity() > this.tank.getFluidAmount();
	}

	@Override
	public boolean canDrain(final ForgeDirection from, final Fluid fluid) {
		if(!this.shouldHandleFluids)
			return false;
		return this.tank.getFluid().getFluid() == fluid && this.tank.getFluidAmount() > 0;
	}

	@Override
	public FluidTankInfo[] getTankInfo(final ForgeDirection from) {
		return new FluidTankInfo[] {this.tank.getInfo()};
	}

	@Override
	public void readFromNBT(final NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		final NBTTagList tagList = tagCompound.getTagList("inventory", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++) {
			final NBTTagCompound tag = tagList.getCompoundTagAt(i);
			final byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < this.inventory.length)
				this.inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
		}
		this.cookTime = tagCompound.getInteger("cookTime");
		this.partBurnTime = tagCompound.getInteger("burnTime");
		if(tagCompound.hasKey("fluidID"))
			this.tank.fill(new FluidStack(FluidRegistry.getFluid(tagCompound.getInteger("fluidID")), tagCompound.getInteger("fluidAmount")), true);
		if(tagCompound.hasKey("activeSelection"))
			this.setActiveSelection(BoatPart.deserialize(tagCompound.getInteger("activeSelection")).getOnePartBoat(this.worldObj));
		this.shouldHandleFluids = tagCompound.getBoolean("handleFluids");
	}

	@Override
	public void writeToNBT(final NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		final NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < this.inventory.length-1; i++) {
			final ItemStack stack = this.inventory[i];
			if (stack != null) {
				final NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("inventory", itemList);
		tagCompound.setInteger("cookTime", this.cookTime);
		tagCompound.setInteger("burnTime", this.partBurnTime);
		if(this.tank.getFluid() != null && this.tank.getFluid().amount > 0){
			tagCompound.setInteger("fluidID", this.tank.getFluid().fluidID);
			tagCompound.setInteger("fluidAmount", this.tank.getFluidAmount());
		}
		if(this.activeSelection != null)
			tagCompound.setInteger("activeSelection", this.activeSelection.getBoatParts().get(0).getPartID());
		tagCompound.setBoolean("handleFluids", this.shouldHandleFluids);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		final NBTTagCompound comp = new NBTTagCompound();
		this.writeToNBT(comp);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, comp);
	}

	@Override
	public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity pkt)
	{
		LogHelper.debug("Received data packet");
		final NBTTagCompound comp = pkt.func_148857_g();
		this.readFromNBT(comp);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		this.handleCrafting();
		this.handleFluids();
	}

	private void handleCrafting(){
		if(this.partBurnTime <= 0)
			return;
		this.cookTime++;
		if(this.cookTime >= this.partBurnTime){
			this.inventory[5] = this.getActiveSelection().getBoatParts().get(0).getCraftingResult();
			this.cookTime = 0;
			this.partBurnTime = 0;
		}
	}

	private void handleFluids(){
		if(!this.shouldHandleFluids)
			return;
		final ItemStack stack = this.inventory[3];
		if(stack == null){
			this.liquidTime = 0;
			this.takingInLiquid = false;
			return;
		}
		boolean doIntake = false;
		if(this.isTakingInLiquid())
			if(++this.liquidTime < TileEntityBoatbench.LIQUID_INTAKE_TIME)
				return;
			else
				doIntake = true;
		final int maxDrain = this.tank.getCapacity()-this.tank.getFluidAmount();
		if(stack.getItem() instanceof ItemFluidContainer){

			final ItemFluidContainer fluidC = (ItemFluidContainer)stack.getItem();
			final FluidStack fStack = fluidC.getFluid(stack);
			if(fStack == null || !ModFluids.isFuel(fStack.getFluid())){
				if(fStack == null)
					if(FluidContainerRegistry.isBucket(stack))
						this.inventory[3] = FluidContainerRegistry.EMPTY_BUCKET.copy();
				return;
			}
			if(!doIntake){
				this.takingInLiquid = true;
				return;
			}
			final int amount = this.tank.fill(fluidC.drain(stack, maxDrain, true), true);
			if(fStack.amount <= amount){
				if(FluidContainerRegistry.isBucket(stack))
					this.inventory[3] = FluidContainerRegistry.EMPTY_BUCKET.copy();
				this.tryMoveBucket();
				this.liquidTime = 0;
				this.takingInLiquid = false;
			}
		}else if(stack.getItem() instanceof ItemBucket){

			final FluidStack fStack = FluidContainerRegistry.getFluidForFilledItem(stack);
			if(fStack == null || !ModFluids.isFuel(fStack.getFluid()))
				return;

			if(!doIntake){
				this.takingInLiquid = true;
				return;
			}

			if(maxDrain < fStack.amount)
				return;
			this.tank.fill(fStack, true);
			this.inventory[3] = FluidContainerRegistry.EMPTY_BUCKET.copy();
			this.tryMoveBucket();
			this.liquidTime = 0;
			this.takingInLiquid = false;
		}else{

			final FluidStack fStack = FluidContainerRegistry.getFluidForFilledItem(stack);
			if(fStack == null || !ModFluids.isFuel(fStack.getFluid()))
				return;

			LogHelper.error("FluidContainerRegistry reported "+stack.getItem().getClass().getCanonicalName()+" as a fluid container with "+fStack.amount+" of "+
					fStack.getFluid().getName()+", but it was not recognized! Please report this!");
			this.tryMoveBucket();
			this.liquidTime = 0;
			this.takingInLiquid = false;
		}
	}

	private void tryMoveBucket(){
		if(this.inventory[4] == null){
			this.inventory[4] = this.inventory[3];
			this.inventory[3] = null;
		}
	}

}

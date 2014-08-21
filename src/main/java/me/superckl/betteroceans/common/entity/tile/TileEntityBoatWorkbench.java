package me.superckl.betteroceans.common.entity.tile;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;
import me.superckl.betteroceans.common.IRenderRotatable;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.reference.ModFluids;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.RecipeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
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
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntityBoatWorkbench extends TileEntity implements IInventory, IFluidHandler{

	@Getter
	private final ItemStack[] inventory = new ItemStack[10];
	@Getter
	private EntityBOBoat activeSelection;
	@Getter
	@Setter
	private boolean shouldHandleFluids;
	private final IFluidTank tank = new FluidTank(4000);

	public TileEntityBoatWorkbench() {}

	public TileEntityBoatWorkbench(final boolean shouldHandleFluids) {
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
		RecipeHelper.removeItems(this.activeSelection.getBoatParts().get(0).getCraftingIngredients(), this.inventory, true);
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
				this.checkRecipeCompletion();
				return itemstack;
			}
			else
			{
				itemstack = this.inventory[slot].splitStack(amount);

				if (this.inventory[slot].stackSize == 0)
					this.inventory[slot] = null;

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

	private void checkRecipeCompletion(){
		if(this.activeSelection == null){
			this.inventory[9] = null;
			return;
		}
		if(RecipeHelper.areItemsPresent(this.activeSelection.getBoatParts().get(0).getCraftingIngredients(), Arrays.copyOf(this.inventory, 9), true))
			this.inventory[9] = this.activeSelection.getBoatParts().get(0).getCraftingResult();
		else
			this.inventory[9] = null;
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
		return slot < 9;
	}

	@Override
	public int fill(final ForgeDirection from, final FluidStack resource, final boolean doFill) {
		if(!this.shouldHandleFluids || !ModFluids.isLubricant(resource.getFluid()))
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
		if(!this.shouldHandleFluids || !ModFluids.isLubricant(fluid))
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
		if(tagCompound.hasKey("fluidID"))
			this.tank.fill(new FluidStack(FluidRegistry.getFluid(tagCompound.getInteger("fluidID")), tagCompound.getInteger("fluidAmount")), true);
		if(tagCompound.hasKey("activeSelection"))
			this.setActiveSelection(BoatPart.deserialize(tagCompound.getInteger("activeSelection")).getOnePartBoat(this.worldObj));
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
		if(this.tank.getFluid() != null && this.tank.getFluid().amount > 0){
			tagCompound.setInteger("fluidID", this.tank.getFluid().fluidID);
			tagCompound.setInteger("fluidAmount", this.tank.getFluidAmount());
		}
		if(this.activeSelection != null)
			tagCompound.setInteger("activeSelection", this.activeSelection.getBoatParts().get(0).getPartID());
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
		final TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(pkt.func_148856_c(), pkt.func_148855_d(), pkt.func_148854_e());
		if(te == null || !(te instanceof TileEntityBoatWorkbench)){
			LogHelper.error("Failed to deserialize TileEntity!");
			return;
		}
		((TileEntityBoatWorkbench)te).setActiveSelection(BoatPart.deserialize(comp.getInteger("activeSelection")).getOnePartBoat(Minecraft.getMinecraft().theWorld));
	}

}

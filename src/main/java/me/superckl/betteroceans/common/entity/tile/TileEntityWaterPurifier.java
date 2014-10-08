package me.superckl.betteroceans.common.entity.tile;

import lombok.Getter;
import me.superckl.betteroceans.common.reference.ModFluids;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityWaterPurifier extends TileEntity implements IInventory, IFluidHandler{

	@Getter
	private final FluidTank saltWaterTank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME*4);
	@Getter
	private final FluidTank freshWaterTank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME*4);
	@Getter
	private final ItemStack[] inventory = new ItemStack[4];
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(resource.getFluid() != ModFluids.saltWater)
			return 0;
		return this.saltWaterTank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		if(resource.getFluid() != this.freshWaterTank.getFluid().getFluid())
			return null;
		return this.freshWaterTank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return this.freshWaterTank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return fluid == ModFluids.saltWater && this.saltWaterTank.getCapacity() > this.saltWaterTank.getFluidAmount();
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return this.freshWaterTank.getFluid().getFluid() == fluid && this.freshWaterTank.getFluidAmount() > 0;
	}

	/**
	 * Returns an array {freshWaterTank info, saltWaterTank info}.
	 */
	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] {this.freshWaterTank.getInfo(), this.saltWaterTank.getInfo()};
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.inventory[slot] = stack;
	}

	@Override
	public String getInventoryName() {
		return "Water Purifier";
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
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 1 || slot == 3)
			return false;
		return FluidContainerRegistry.isContainer(stack);
	}

}

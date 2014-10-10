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
	public int fill(final ForgeDirection from, final FluidStack resource, final boolean doFill) {
		if(resource.getFluid() != ModFluids.saltWater)
			return 0;
		return this.saltWaterTank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(final ForgeDirection from, final FluidStack resource,
			final boolean doDrain) {
		if(resource.getFluid() != this.freshWaterTank.getFluid().getFluid())
			return null;
		return this.freshWaterTank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(final ForgeDirection from, final int maxDrain, final boolean doDrain) {
		return this.freshWaterTank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(final ForgeDirection from, final Fluid fluid) {
		return fluid == ModFluids.saltWater && this.saltWaterTank.getCapacity() > this.saltWaterTank.getFluidAmount();
	}

	@Override
	public boolean canDrain(final ForgeDirection from, final Fluid fluid) {
		return this.freshWaterTank.getFluid().getFluid() == fluid && this.freshWaterTank.getFluidAmount() > 0;
	}

	/**
	 * Returns an array {freshWaterTank info, saltWaterTank info}.
	 */
	@Override
	public FluidTankInfo[] getTankInfo(final ForgeDirection from) {
		return new FluidTankInfo[] {this.freshWaterTank.getInfo(), this.saltWaterTank.getInfo()};
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(final int slot) {
		return this.inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(final int p_70298_1_, final int p_70298_2_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int p_70304_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(final int slot, final ItemStack stack) {
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
	public boolean isUseableByPlayer(final EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(final int slot, final ItemStack stack) {
		if(slot == 1 || slot == 3)
			return false;
		return FluidContainerRegistry.isContainer(stack);
	}

}

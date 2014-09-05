package me.superckl.betteroceans.common.container.components;

import lombok.Setter;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class FluidContainerSlot extends Slot{

	@Setter
	private Slot dependency;
	private TileEntityBoatbench te;

	public FluidContainerSlot(final IInventory inv, final int id, final int x, final int y) {
		super(inv, id, x, y);
		if(inv instanceof TileEntityBoatbench)
			this.te = (TileEntityBoatbench) inv;
	}

	@Override
	public boolean isItemValid(final ItemStack stack) {
		return /*(this.dependency == null || !this.dependency.getHasStack()) && */FluidContainerRegistry.isContainer(stack);
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		if(this.te != null)
			this.te.setWeHaveAProblemHere(false);
	}



}

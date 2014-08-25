package me.superckl.betteroceans.common.container.components;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class NoPutSlot extends Slot{

	public NoPutSlot(final IInventory inv, final int id, final int x, final int y) {
		super(inv, id, x, y);
	}

	@Override
	public boolean isItemValid(final ItemStack stack) {
		return false;
	}

}

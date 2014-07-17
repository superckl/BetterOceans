package me.superckl.betteroceans.common.container.components;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class BOSlot extends Slot{

	private ISlotListener listener;

	public BOSlot(final IInventory inventory, final int slot,
			final int x, final int y) {
		super(inventory, slot, x, y);
		if(inventory instanceof ISlotListener)
			this.listener = (ISlotListener) inventory;
	}

	@Override
	public void onPickupFromSlot(final EntityPlayer player, final ItemStack stack)
	{
		super.onSlotChanged();
		if(this.listener != null)
			this.listener.onSlotPickup(player, stack, this);
	}

}

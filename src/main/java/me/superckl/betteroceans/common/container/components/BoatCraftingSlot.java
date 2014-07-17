package me.superckl.betteroceans.common.container.components;

import me.superckl.betteroceans.common.entity.tile.TileEntityBoatWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class BoatCraftingSlot extends BOSlot{

	private final TileEntityBoatWorkbench te;

	public BoatCraftingSlot(final TileEntityBoatWorkbench te, final int slot,
			final int x, final int y) {
		super(te, slot, x, y);
		this.te = te;
	}

	@Override
	public boolean isItemValid(final ItemStack p_75214_1_)
	{
		return false;
	}

	@Override
	public void onPickupFromSlot(final EntityPlayer player, final ItemStack stack)
	{
		this.te.onCraftingSlotPick();
		super.onSlotChanged();
	}

}

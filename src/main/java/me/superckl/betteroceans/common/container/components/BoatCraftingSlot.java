package me.superckl.betteroceans.common.container.components;

import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BoatCraftingSlot extends BOSlot{

	private final TileEntityBoatbench te;

	public BoatCraftingSlot(final TileEntityBoatbench te, final int slot,
			final int x, final int y) {
		super(te, slot, x, y);
		this.te = te;
	}

	@Override
	public boolean isItemValid(final ItemStack stack)
	{
		return stack == null || stack.getItem() == Item.getItemFromBlock(Blocks.air);
	}

	@Override
	public void onPickupFromSlot(final EntityPlayer player, final ItemStack stack)
	{
		super.onSlotChanged();
		this.te.onCraftingSlotPick();
	}

}

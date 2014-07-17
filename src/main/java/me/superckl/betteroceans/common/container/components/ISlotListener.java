package me.superckl.betteroceans.common.container.components;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ISlotListener {

	public void onSlotPickup(final EntityPlayer player, final ItemStack stack, final BOSlot slot);

}

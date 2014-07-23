package me.superckl.betteroceans.common.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;

public class ItemStackHelper {

	public static List<ItemStack> deepClone(final Collection<ItemStack> items){
		final ArrayList<ItemStack> newItems = new ArrayList<ItemStack>();
		for(final ItemStack item:items)
			newItems.add(item.copy());
		return newItems;
	}

	public static ItemStack[] deepClone(final ItemStack[] items){
		final ItemStack[] newItems = new ItemStack[items.length];
		for(int i = 0; i < items.length; i++)
			newItems[i] = items[i] == null ? null:items[i].copy();
		return newItems;
	}


}

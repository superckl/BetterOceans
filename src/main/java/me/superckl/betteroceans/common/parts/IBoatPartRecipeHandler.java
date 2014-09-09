package me.superckl.betteroceans.common.parts;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IBoatPartRecipeHandler {

	public List<ItemStack> getRequiredItemsFor(final BoatPart part);
	public boolean shouldHandle(final BoatPart part);
}

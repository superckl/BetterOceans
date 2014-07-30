package me.superckl.betteroceans.common;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface BoatPart {

	public Type getType();
	public List<ItemStack> getCraftingIngredients();
	public Material getMaterial();
	public double getSpeedModifier();
	public Item asItem();

	public static enum Type{
		BOTTOM,
		SIDE,
		END;
	}

	public static enum Material{
		WOOD,
		IRON,
		GLASS;
	}

}

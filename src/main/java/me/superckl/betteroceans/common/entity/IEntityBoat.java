package me.superckl.betteroceans.common.entity;

import java.util.List;

import me.superckl.betteroceans.common.nets.INet;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IEntityBoat{

	public void attachNet(final INet net);
	public boolean hasNetAttatched();
	public INet getAttatchedNet();
	public List<ItemStack> getCraftingIngredients();
	public Item getItem();

}

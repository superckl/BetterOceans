package me.superckl.betteroceans.item;

import me.superckl.betteroceans.reference.ModData;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public abstract class ItemFoodBO extends ItemFood{

	public ItemFoodBO(final int hunger, final float saturation, final boolean doesWolfLike) {
		super(hunger, saturation, doesWolfLike);
	}

	public ItemFoodBO(final int hunger, final boolean doesWolfLike) {
		super(hunger, doesWolfLike);
	}

	@Override
	public String getUnlocalizedName(){
		return String.format("item.%s%s%s", ModData.MOD_ID.toLowerCase(), ":", this.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack){
		return String.format("item.%s%s%s", ModData.MOD_ID.toLowerCase(), ":", this.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	private String getUnwrappedUnlocalizedName(final String unlocalizedName){
		return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
	}
}

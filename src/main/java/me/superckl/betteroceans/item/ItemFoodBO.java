package me.superckl.betteroceans.item;

import me.superckl.betteroceans.reference.ModData;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public abstract class ItemFoodBO extends ItemFood{

	public ItemFoodBO(int hunger, float saturation, boolean doesWolfLike) {
		super(hunger, saturation, doesWolfLike);
	}

	public ItemFoodBO(int hunger, boolean doesWolfLike) {
		super(hunger, doesWolfLike);
	}
	
	@Override
	public String getUnlocalizedName(){
		return String.format("item.%s%s%s", ModData.MOD_ID.toLowerCase(), ":", this.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack){
		return String.format("item.%s%s%s", ModData.MOD_ID.toLowerCase(), ":", this.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	private String getUnwrappedUnlocalizedName(String unlocalizedName){
		return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
	}
}

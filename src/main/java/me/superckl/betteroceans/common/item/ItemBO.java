package me.superckl.betteroceans.common.item;

import me.superckl.betteroceans.common.reference.ModData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ItemBO extends Item{

	public ItemBO(){
		super();
	}

	@Override
	public String getUnlocalizedName(){
		return String.format("item.%s%s%s", ModData.MOD_ID.toLowerCase(), ":", this.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack){
		return String.format("item.%s%s%s", ModData.MOD_ID.toLowerCase(), ":", this.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	protected String getUnwrappedUnlocalizedName(final String unlocalizedName){
		return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
	}
}

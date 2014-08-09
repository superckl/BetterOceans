package me.superckl.betteroceans.common.item;

import me.superckl.betteroceans.common.reference.ModData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ItemBO extends Item{

	public ItemBO(){
		super();
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack){
		return String.format("item.%s%s%s", ModData.MOD_ID.toLowerCase(), ":", this.getUnwrappedUnlocalizedName(super.getUnlocalizedName(), stack.getItemDamage()));
	}

	protected String getUnwrappedUnlocalizedName(final String unlocalizedName, final int damage){
		return unlocalizedName.substring(unlocalizedName.indexOf(".")+1).concat(this.isNameDamageReliant() ? Integer.toString(damage).concat(":"):"");
	}

	protected boolean isNameDamageReliant(){
		return false;
	}

}

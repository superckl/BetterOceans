package me.superckl.betteroceans.common.block.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public abstract class ItemBlockBO extends ItemBlock{

	public ItemBlockBO(final Block block){
		super(block);
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack){
		return String.format("item.%s", this.getUnwrappedUnlocalizedName(super.getUnlocalizedName(), stack.getItemDamage()));
	}

	private String getUnwrappedUnlocalizedName(final String unlocalizedName, final int damage){
		return unlocalizedName.substring(unlocalizedName.indexOf(".")+1).concat(this.isNameDamageReliant() ? ":"+Integer.toString(damage):"");
	}

	protected boolean isNameDamageReliant(){
		return false;
	}

}

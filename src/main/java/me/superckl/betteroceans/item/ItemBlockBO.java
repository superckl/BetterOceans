package me.superckl.betteroceans.item;

import me.superckl.betteroceans.reference.ModData;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public abstract class ItemBlockBO extends ItemBlock{

	public ItemBlockBO(Block block){
		super(block);
	}
	
	@Override
	public String getUnlocalizedName(){
		return String.format("item.%s", this.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack){
		return String.format("item.%s", this.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	private String getUnwrappedUnlocalizedName(String unlocalizedName){
		return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
	}
	
}

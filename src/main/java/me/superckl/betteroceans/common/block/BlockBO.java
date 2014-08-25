package me.superckl.betteroceans.common.block;

import me.superckl.betteroceans.common.reference.ModData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockBO extends Block{

	public BlockBO(final Material material){
		super(material);
	}

	@Override
	public String getUnlocalizedName(){
		return String.format("tile.%s%s%s", ModData.MOD_ID.toLowerCase(), ":", this.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	private String getUnwrappedUnlocalizedName(final String unlocalizedName){
		return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
	}

	public String getUnlocalizedName(final int meta){
		return String.format("tile.%s%s%s", ModData.MOD_ID.toLowerCase(), ":", this.getUnwrappedUnlocalizedName(super.getUnlocalizedName(), meta));
	}

	private String getUnwrappedUnlocalizedName(final String unlocalizedName, final int meta){
		return unlocalizedName.substring(unlocalizedName.indexOf(".")+1)+":"+meta;
	}

}

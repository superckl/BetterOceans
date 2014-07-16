package me.superckl.betteroceans.common.block;

import me.superckl.betteroceans.common.reference.ModData;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public abstract class BlockContainerBO extends BlockContainer{

	protected BlockContainerBO(final Material material) {
		super(material);
	}

	@Override
	public String getUnlocalizedName(){
		return String.format("tile.%s%s%s", ModData.MOD_ID.toLowerCase(), ":", this.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	private String getUnwrappedUnlocalizedName(final String unlocalizedName){
		return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
	}

}

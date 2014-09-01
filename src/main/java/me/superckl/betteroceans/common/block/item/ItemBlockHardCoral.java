package me.superckl.betteroceans.common.block.item;

import net.minecraft.block.Block;

public class ItemBlockHardCoral extends ItemBlockBO{

	public ItemBlockHardCoral(final Block block) {
		super(block);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(final int meta)
	{
		return meta;
	}

}

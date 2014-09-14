package me.superckl.betteroceans.common.block.item;

import net.minecraft.block.Block;

public class ItemBlockLantern extends ItemBlockBO{

	public ItemBlockLantern(final Block block) {
		super(block);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(final int meta)
	{
		return meta;
	}

	@Override
	protected boolean isNameDamageReliant() {
		return true;
	}



}

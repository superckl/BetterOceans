package me.superckl.betteroceans.common.block.item;

import net.minecraft.block.Block;

public class ItemBlockBoatbench extends ItemBlockBO{

	public ItemBlockBoatbench(final Block block) {
		super(block);
		this.setHasSubtypes(true);
	}

	@Override
	protected boolean isNameDamageReliant() {
		return true;
	}

	@Override
	public int getMetadata(final int meta)
	{
		return meta;
	}

}

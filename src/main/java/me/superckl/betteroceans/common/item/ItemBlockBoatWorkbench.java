package me.superckl.betteroceans.common.item;

import net.minecraft.block.Block;

public class ItemBlockBoatWorkbench extends ItemBlockBO{

	public ItemBlockBoatWorkbench(final Block block) {
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

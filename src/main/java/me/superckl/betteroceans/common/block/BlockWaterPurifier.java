package me.superckl.betteroceans.common.block;

import me.superckl.betteroceans.common.entity.tile.TileEntityWaterPurifier;
import me.superckl.betteroceans.common.reference.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWaterPurifier extends BlockContainerBO{

	public BlockWaterPurifier() {
		super(Material.iron);
		this.setBlockName("purifier").setStepSound(Block.soundTypeMetal).setCreativeTab(ModTabs.tabBlocks).setHarvestLevel("pickaxe", 2);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityWaterPurifier();
	}

}

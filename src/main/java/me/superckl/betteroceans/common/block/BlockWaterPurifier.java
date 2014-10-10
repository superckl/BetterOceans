package me.superckl.betteroceans.common.block;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.entity.tile.TileEntityWaterPurifier;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWaterPurifier extends BlockContainerBO{

	public BlockWaterPurifier() {
		super(Material.iron);
		this.setBlockName("purifier").setStepSound(Block.soundTypeMetal).setCreativeTab(ModTabs.tabBlocks).setHarvestLevel("pickaxe", 2);
	}

	@Override
	public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_)
	{
		final TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking())
			return false;
		player.openGui(BetterOceans.getInstance(), ModData.GUIIDs.WATER_PURIFIER, world, x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
		return new TileEntityWaterPurifier();
	}

}

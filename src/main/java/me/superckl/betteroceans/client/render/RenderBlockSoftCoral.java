package me.superckl.betteroceans.client.render;

import me.superckl.betteroceans.common.reference.RenderData;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockSoftCoral implements ISimpleBlockRenderingHandler{

	@Override
	public void renderInventoryBlock(final Block block, final int metadata, final int modelId,
			final RenderBlocks renderer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z,
			final Block block, final int modelId, final RenderBlocks renderer) {
		renderer.setRenderBounds(0, 0, 0, .5D, .5D, .5D);
		renderer.renderStandardBlock(block, x, y, z);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(final int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return RenderData.softCoralID;
	}

}

package me.superckl.betteroceans.common.gen;

import java.util.Random;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.block.BlockSeaweed;
import me.superckl.betteroceans.common.reference.ModBlocks;
import me.superckl.betteroceans.common.utility.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneratorSeaweed implements IWorldGenerator{

	@Override
	public void generate(final Random random, final int chunkX, final int chunkZ, final World world,
			final IChunkProvider chunkGenerator, final IChunkProvider chunkProvider) {
		if(!BetterOceans.getInstance().getConfig().isGenSeaweed())
			return;
		Block block = null;
		int tries = BetterOceans.getInstance().getConfig().getSeaweedWaterBlockTries();
		int baseX = chunkX, baseZ = chunkZ;
		while(block != Blocks.water && tries-- > 0){
			baseX = (chunkX << 4) + random.nextInt(16)+8;
			baseZ = (chunkZ << 4) + random.nextInt(16)+8;
			final int y = world.getTopSolidOrLiquidBlock(baseX, baseZ);
			block = world.getBlock(baseX, y, baseZ);
		}
		if(block != Blocks.water)
			return;
		for (int i = 0; i < BetterOceans.getInstance().getConfig().getSeaweedTries(); ++i)
		{
			final int newX = baseX + random.nextInt(8) - random.nextInt(8);
			final int newZ = baseZ + random.nextInt(8) - random.nextInt(8);
			final int newY = world.getTopSolidOrLiquidBlock(newX, newZ);
			block = world.getBlock(newX, newY, newZ);
			if(block != Blocks.water)
				continue;
			final int depth = BlockHelper.getHeight(world, newX, newY, newZ, false);
			if(depth < 3)
				continue;
			if(!ModBlocks.seaweed.canPlaceBlockAt(world, newX, newY, newZ))
				continue;
			final int maxStack = Math.min(depth-2, 2);
			int toStack = 0;
			if(maxStack > 0)
				toStack = (int) Math.round(random.nextDouble()*maxStack);
			final int height = toStack+1;
			int startY = newY;
			world.setBlock(newX, startY, newZ, ModBlocks.seaweed, BlockSeaweed.getMetaFor(height, 0), 1 + 2);
			while(toStack > 0){
				world.setBlock(newX, ++startY, newZ, ModBlocks.seaweed, BlockSeaweed.getMetaFor(height, height-toStack), 1 + 2);
				toStack--;
			}
		}
	}
}

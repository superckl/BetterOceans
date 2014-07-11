package me.superckl.betteroceans.gen;

import java.util.Random;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.utility.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class TrenchGenerator implements IWorldGenerator{

	@Override
	public void generate(final Random random, final int chunkX, final int chunkZ, final World world,
			final IChunkProvider chunkGenerator, final IChunkProvider chunkProvider) {
		if(!BetterOceans.getInstance().getConfig().isGenTrenches())
			return;
		if(!BlockHelper.isOcean(world, chunkX, chunkZ))
			return;
		if(BlockHelper.getMinHeightInChunk(world, chunkX, chunkZ, Blocks.water) < 15)
			return; //We aren't generating a trench in that shallow water...
		if(random.nextInt(200) != 0)
			return;
		final int minY = BlockHelper.getOceanFloorInChunk(world, chunkX, chunkZ);
		final int depth = (int) Math.round(Math.max(0.91D, random.nextDouble()+.1D)*(minY-7));
		final int width = Math.max(20, random.nextInt(30));
		final int length = Math.max(80, random.nextInt(250));
		final int ledges = width > 10 ? 3:2;
		final int[] ledgeHeights = new int[ledges];
		final int averageHeight = depth/ledges;
		for(int i = 0; i < ledges; i++){
			if(i == ledges-1){
				int sum = 0;
				for(final int j:ledgeHeights)
					sum += j;
				ledgeHeights[i] = depth-sum;
				break;
			}
			ledgeHeights[i] = (int) Math.round(Math.max(random.nextDouble()+.2D, 0.8D)*averageHeight);
		}
		int startX = (chunkX << 4) + random.nextInt(10);
		int startZ = (chunkZ << 4) + random.nextInt(10);
		final boolean genAcrossX = random.nextBoolean();
		int currentLedge;
		for(int i = 0; i < length; i++){
			currentLedge = 0;
			if(genAcrossX){
				startZ++;
				startX+=random.nextInt(3)-2;
			}else{
				startX++;
				startZ+=random.nextInt(3)-2;
			}
			if(!BlockHelper.isOcean(world, startX >> 4, startZ >> 4))
				return; //Woops!, we've gone to far!
			//Generate ledges
			while(currentLedge < ledges){
				for(int j = currentLedge; j < width-currentLedge; j++){
					int y;
					if(genAcrossX)
						y = world.getTopSolidOrLiquidBlock(startX+j, startZ)-1;
					else
						y = world.getTopSolidOrLiquidBlock(startX, startZ+j)-1;
					final int diff = minY-y;
					for(int k = diff; k < ledgeHeights[currentLedge]; k++)
						if(genAcrossX)
							world.setBlock(startX+j, minY-k, startZ,
									world.getBlock(startX+j, minY-k, startZ) == Blocks.air ? Blocks.air:Blocks.water);
						else
							world.setBlock(startX, minY-k, startZ+j,
									world.getBlock(startX, minY-k, startZ+j) == Blocks.air ? Blocks.air:Blocks.water);
				}
				currentLedge++;
			}

			//Generate floor
			for(int j = ledges; j < width-ledges; j++){
				final int y = world.getTopSolidOrLiquidBlock(startX, startZ)-1;
				final int diff = minY-y;
				for(int k = diff; k < depth; k++)
					if(genAcrossX)
						world.setBlock(startX+j, minY-k, startZ,
								world.getBlock(startX+j, minY-k, startZ) == Blocks.air ? Blocks.air:Blocks.water);
					else
						world.setBlock(startX, minY-k, startZ+j,
								world.getBlock(startX, minY-k, startZ+j) == Blocks.air ? Blocks.air:Blocks.water);
			}
		}
	}
}

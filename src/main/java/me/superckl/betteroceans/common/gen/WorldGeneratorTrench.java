package me.superckl.betteroceans.common.gen;

import java.util.Random;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.utility.BiomeHelper;
import me.superckl.betteroceans.common.utility.BlockHelper;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.NumberHelper;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneratorTrench implements IWorldGenerator{

	/*@Override
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
		final int depth = (int) Math.round(Math.max(0.91D, random.nextDouble()+.9D)*(minY-7));
		final int width = Math.max(75, random.nextInt(150));
		final int length = Math.max(200, random.nextInt(400));
		final int ledges = Math.max(4, random.nextInt(8));
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
	}*/

	//TODO taper ends
	@Override
	public void generate(final Random random, final int chunkX, final int chunkZ, final World world,
			final IChunkProvider chunkGenerator, final IChunkProvider chunkProvider) {
		if(!BetterOceans.getInstance().getConfig().isGenTrenches())
			return;
		if(!BiomeHelper.isOcean(world, chunkX, chunkZ))
			return;
		if(BlockHelper.getMinHeightInChunk(world, chunkX, chunkZ, Blocks.water) < 15)
			return; //We aren't generating a trench in that shallow water...
		if(BiomeHelper.distanceToNearestNonOcean(world, chunkX, chunkZ, 7) < 7)
			return; //To close to shore;
		if(random.nextInt(50) != 0) //200
			return;
		LogHelper.warn("Generating trench, expect lag. This can get a bit intense...");
		final int from0 = 5 + random.nextInt(3);
		final int width = Math.max(75, random.nextInt(150));
		final int length = Math.max(200, random.nextInt(400));
		final int ledges = Math.max(4, random.nextInt(8));
		int startX = (chunkX << 4) + random.nextInt(10);
		int startZ = (chunkZ << 4) + random.nextInt(10);
		LogHelper.info(startX+":"+startZ);
		final boolean genAcrossX = random.nextBoolean();
		int currentLedge;
		int offsetY = 0;
		int offsetYCounter = 0;
		int ledgeWidth = 1;
		int ledgeWidthCounter = 0;
		for(int i = 0; i < length; i++){
			currentLedge = 0;
			if(offsetYCounter <= 0){
				offsetY += Math.min(random.nextInt(2)-random.nextInt(2), 6);
				offsetYCounter = Math.max(5, random.nextInt(30));
			}
			if(ledgeWidthCounter <= 0){
				ledgeWidth = random.nextInt(4)+2;
				ledgeWidthCounter = Math.max(5, random.nextInt(30));
			}
			if(genAcrossX){
				startZ++;
				startX+=random.nextInt(3)-2;
			}else{
				startX++;
				startZ+=random.nextInt(3)-2;
			}
			if(!BiomeHelper.isOcean(world, startX >> 4, startZ >> 4))
				return; //Woops!, we've gone to far!
			final int floorY = from0+offsetY;
			//Generate floor
			for(int j = ledges; j < width-ledges; j++){
				final int y = genAcrossX ? world.getTopSolidOrLiquidBlock(startX+j, startZ):world.getTopSolidOrLiquidBlock(startX, startZ+j);
				for(int k = (int) (floorY+Math.max(0, Math.floor(Math.pow(random.nextDouble(), 15D))*2.5)); k < y; k++)
					if(genAcrossX)
						world.setBlock(startX+j, k, startZ,
								world.getBlock(startX+j, k, startZ) == Blocks.air ? Blocks.air:Blocks.water);
					else
						world.setBlock(startX, k, startZ+j,
								world.getBlock(startX, k, startZ+j) == Blocks.air ? Blocks.air:Blocks.water);
			}
			int topHeight;
			if(genAcrossX)
				topHeight = world.getTopSolidOrLiquidBlock(startX-1, startZ);
			else
				topHeight = world.getTopSolidOrLiquidBlock(startX, startZ-1);
			final int averageHeight = topHeight/ledges;
			final int[] ledgeHeights = new int[ledges];
			for(int h = 0; h < ledges; h++){
				if(h == ledges-1){
					int sum = 0;
					for(final int j:ledgeHeights)
						sum += j;
					ledgeHeights[h] = topHeight-sum;
					break;
				}
				ledgeHeights[h] = (int) Math.round(Math.max(random.nextDouble()+.2D, 0.8D)*averageHeight);
			}
			int ledgeX = startX;
			int ledgeZ = startZ;
			//Generate ledges
			//TODO ledges of width > 1
			while(currentLedge < ledges){
				for(int l = 0; l < ledgeWidth; l++){
					if(genAcrossX)
						ledgeX-=l;
					else
						ledgeZ-=l;
					for(int j = ledges-1; j >= 0; j--){
						int y;

						//First side
						if(genAcrossX)
							y = world.getTopSolidOrLiquidBlock(ledgeX+j, ledgeZ);
						else
							y = world.getTopSolidOrLiquidBlock(ledgeX, ledgeZ+j);
						final int sum = NumberHelper.sum(currentLedge, ledgeHeights);
						for(int k = from0+ledgeHeights[currentLedge]+sum; k < y; k++)
							if(genAcrossX)
								world.setBlock(ledgeX+j, k, ledgeZ,
										world.getBlock(ledgeX+j, k, ledgeZ) == Blocks.air ? Blocks.air:Blocks.water);
							else
								world.setBlock(ledgeX, k, ledgeZ+j,
										world.getBlock(ledgeX, k, ledgeZ+j) == Blocks.air ? Blocks.air:Blocks.water);
						//Second side
						int nudge;
						if(genAcrossX)
							nudge = (startX-ledgeX)*2+width;
						else
							nudge = (startZ-ledgeZ)*2+width;
						if(genAcrossX)
							y = world.getTopSolidOrLiquidBlock(ledgeX+nudge+j, ledgeZ);
						else
							y = world.getTopSolidOrLiquidBlock(ledgeX, ledgeZ+nudge+j);
						for(int k = from0+ledgeHeights[currentLedge]+sum; k < y; k++)
							if(genAcrossX)
								world.setBlock(ledgeX+nudge+j, k, ledgeZ,
										world.getBlock(ledgeX+nudge+j, k, ledgeZ) == Blocks.air ? Blocks.air:Blocks.water);
							else
								world.setBlock(ledgeX, k, ledgeZ+nudge+j,
										world.getBlock(ledgeX, k, ledgeZ+nudge+j) == Blocks.air ? Blocks.air:Blocks.water);
					}
				}
				currentLedge++;
			}
		}
	}
}

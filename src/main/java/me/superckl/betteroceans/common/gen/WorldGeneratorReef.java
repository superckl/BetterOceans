package me.superckl.betteroceans.common.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.reference.ModBlocks;
import me.superckl.betteroceans.common.utility.BiomeHelper;
import me.superckl.betteroceans.common.utility.BlockHelper;
import me.superckl.betteroceans.common.utility.LogHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneratorReef implements IWorldGenerator{

	private final List<Vec3> toSplotch = new ArrayList<Vec3>();

	@Override
	public void generate(final Random random, final int chunkX, final int chunkZ, final World world,
			final IChunkProvider chunkGenerator, final IChunkProvider chunkProvider) {
		if(!BetterOceans.getInstance().getConfig().isGenReefs())
			return;
		if(!BiomeHelper.isOcean(world, chunkX, chunkZ))
			return;
		if(BlockHelper.getMinHeightInChunk(world, chunkX, chunkZ, Blocks.water) > 20)
			return; //We aren't generating a reef in that deep water...
		if(BiomeHelper.distanceToNearestNonOcean(world, chunkX, chunkZ, 2) < 2)
			return; //To close to shore;
		if(random.nextInt(50) != 0) //200
			return;
		LogHelper.debug("Generating reef...");
		final boolean genAcrossX = random.nextBoolean();
		int startX = (chunkX << 4) + random.nextInt(10);
		int startZ = (chunkZ << 4) + random.nextInt(10);
		//LogHelper.info(startX+":"+startZ);
		final int length = 15 + random.nextInt(30);
		final int width = 5 + random.nextInt(25);
		final int height = 3 + random.nextInt(3);

		for(int i = 0; i < length; i++){
			if(genAcrossX){
				startZ++;
				startX+=random.nextInt(3)-2;
			}else{
				startX++;
				startZ+=random.nextInt(3)-2;
			}
			int currentWidth = width;
			int subbed = 0;
			for(int h = 0; h < height; h++){
				int flux = random.nextInt(5)-2;
				while(currentWidth+flux >= currentWidth+subbed)
					flux--;
				int offset = flux / 2;
				if(flux % 2 != 0)
					offset += flux % 2;
				for(int j = 0; j < currentWidth+flux; j++){
					if(currentWidth <= 0)
						break;
					if(genAcrossX){
						final int x = startX-offset+j;
						final int z = startZ;
						final int y = world.getTopSolidOrLiquidBlock(x, z);
						if(random.nextDouble() < .8)
							world.setBlock(x, y, z, ModBlocks.hardCoral, 0, 2);
						if(h == height-1 && random.nextDouble() < .2)
							world.setBlock(x, y+1, z, ModBlocks.softCoral, random.nextInt(1), 2);
						if(random.nextDouble() < .002)
							this.toSplotch.add(Vec3.createVectorHelper(x, y, z));
					}else{
						final int x = startX;
						final int z = startZ-offset+j;
						final int y = world.getTopSolidOrLiquidBlock(x, z);
						if(random.nextDouble() < .8)
							world.setBlock(x, y, z, ModBlocks.hardCoral, 0, 2);
						if(h == height-1 && random.nextDouble() < .2)
							world.setBlock(x, y+1, z, ModBlocks.softCoral, random.nextInt(1), 2);
						if(random.nextDouble() < .002)
							this.toSplotch.add(Vec3.createVectorHelper(x, y, z));
					}
				}
				if(h == height-1)
					break;
				subbed = random.nextInt(3)+1;
				currentWidth -= subbed;
			}
		}
		//Let's splotch this shit
		switch(random.nextInt(4)){
		case 0:

			break;
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;
		}
	}

}

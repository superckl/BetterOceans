package me.superckl.betteroceans.gen;

import java.util.Random;

import me.superckl.betteroceans.utility.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeManager;
import cpw.mods.fml.common.IWorldGenerator;

public class TrenchGenerator implements IWorldGenerator{

	@Override
	public void generate(final Random random, final int chunkX, final int chunkZ, final World world,
			final IChunkProvider chunkGenerator, final IChunkProvider chunkProvider) {
		boolean match = false;
		for(int i = 0; i <= 15; i++){
			final int biomeId = world.getBiomeGenForCoords(chunkX*16+i, chunkZ*16+i).biomeID;
			for(final BiomeGenBase gen:BiomeManager.oceanBiomes)
				if(gen.biomeID == biomeId){
					match = true;
					break;
				}
		}
		if(!match)
			return;
		if(BlockHelper.getMinHeightInChunk(world, chunkX, chunkZ, Blocks.water) < 15)
			return; //We aren't generating a trench that shallow...
		//TODO
	}

}

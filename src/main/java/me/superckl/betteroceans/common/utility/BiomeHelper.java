package me.superckl.betteroceans.common.utility;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeHelper {

	public static boolean isOcean(final World world, final int chunkX, final int chunkZ){
		final int baseX = chunkX << 4; final int baseZ = chunkZ << 4;
		for(int i = 0; i < 16; i++){
			final int id = world.getBiomeGenForCoords(baseX+i, baseZ+i).biomeID;
			if(id != BiomeGenBase.ocean.biomeID && id != BiomeGenBase.deepOcean.biomeID)
				return false;
		}
		return true;
	}

	public static boolean isBeach(final World world, final int chunkX, final int chunkZ){
		final int baseX = chunkX << 4; final int baseZ = chunkZ << 4;
		for(int i = 0; i < 16; i++){
			final int id = world.getBiomeGenForCoords(baseX+i, baseZ+i).biomeID;
			if(id != BiomeGenBase.beach.biomeID)
				return false;
		}
		return true;
	}

	public static int distanceToNearestNonOcean(final World world, final int chunkX, final int chunkZ, final int cap){
		for(int i = 1; i <= cap; i++){
			int x = chunkX-i;
			int z = chunkZ-i;
			for(;z <= chunkZ+i;z++)
				if(!BiomeHelper.isOcean(world, x, z))
					return i;
			x++;
			for(;x <= chunkX+i;x++)
				if(!BiomeHelper.isOcean(world, x, z))
					return i;
			z--;
			for(;z >= chunkZ-i;z--)
				if(!BiomeHelper.isOcean(world, x, z))
					return i;
			x--;
			for(;x > chunkX-i;x--)
				if(!BiomeHelper.isOcean(world, x, z))
					return i;
		}
		return cap;
	}

}

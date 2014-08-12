package me.superckl.betteroceans.common.utility;

import java.util.ArrayList;
import java.util.List;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.asm.ClassTransformer;
import me.superckl.betteroceans.common.gen.BiomeGenBetterDeepOcean;
import me.superckl.betteroceans.common.gen.BiomeGenBetterOcean;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeHelper {

	public static final List<Integer> oceanBiomeIDs = new ArrayList<Integer>();

	public static final void replaceOceanBiomes(){
		if(!BetterOceans.getInstance().getConfig().isOverrideOcean()){
			LogHelper.warn("Ocean overriding is disabled! Loading worlds that were generated with this enabled may be unstable!");
			return;
		}
		final BiomeGenBetterOcean boO = new BiomeGenBetterOcean(BiomeGenBase.ocean.biomeID);
		final BiomeGenBetterDeepOcean boDO = new BiomeGenBetterDeepOcean(BiomeGenBase.deepOcean.biomeID);
		BOReflectionHelper.setPrivateFinalValue(BiomeGenBase.class, null, boO, ClassTransformer.field_ocean);
		BOReflectionHelper.setPrivateFinalValue(BiomeGenBase.class, null, boDO, ClassTransformer.field_deepOcean);
		BiomeHelper.oceanBiomeIDs.add(boO.biomeID);
		BiomeHelper.oceanBiomeIDs.add(boDO.biomeID);
		LogHelper.debug("Succesfully replaced Ocean biomes!");
	}

	public static boolean isOcean(final World world, final int chunkX, final int chunkZ, final int ... additions){
		final int baseX = chunkX << 4; final int baseZ = chunkZ << 4;
		for(int i = 0; i < 16; i++){
			final int id = world.getBiomeGenForCoords(baseX+i, baseZ+i).biomeID;
			boolean match = false;
			for(final Integer integ:BiomeHelper.oceanBiomeIDs)
				if(id == integ.intValue()){
					match = true;
					break;
				}
			if(!match)
				for(final int j:additions)
					if(id == j){
						match = true;
						break;
					}
			if(!match)
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

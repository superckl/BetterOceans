package me.superckl.betteroceans.common.utility;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.gen.BiomeGenBetterDeepOcean;
import me.superckl.betteroceans.common.gen.BiomeGenBetterOcean;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BiomeHelper {

	public static final void replaceOceanBiomes(){
		if(!BetterOceans.getInstance().getConfig().isOverrideOcean()){
			LogHelper.warn("Ocean overriding is disabled! Loading worlds that were generated with this enabled may be unstable!");
			return;
		}
		final BiomeGenBetterOcean boO = new BiomeGenBetterOcean(BiomeGenBase.ocean.biomeID);
		final BiomeGenBetterDeepOcean boDO = new BiomeGenBetterDeepOcean(BiomeGenBase.deepOcean.biomeID);
		BiomeGenBase.getBiomeGenArray()[BiomeGenBase.ocean.biomeID] = boO;
		BiomeGenBase.getBiomeGenArray()[BiomeGenBase.deepOcean.biomeID] = boDO;
		BiomeDictionary.registerBiomeType(boO, Type.OCEAN);
		BiomeDictionary.registerBiomeType(boDO, Type.OCEAN);
		BOReflectionHelper.setPrivateFinalValue(BiomeGenBase.class, null, boO, "ocean");
		BOReflectionHelper.setPrivateFinalValue(BiomeGenBase.class, null, boDO, "deepOcean");
		LogHelper.debug("Succesfully replaced Ocean biomes!");
		/*if(!ReflectionHelper.setFinalStatic(BiomeGenBase.class, boO, true, "ocean", "field_76771_b"))
				LogHelper.fatal("Failed to override ocean biome! Loading worlds generated with Better Oceans may have unpredictable results!");
			else
				BiomeGenBase.getBiomeGenArray()[boO.biomeID] = boO;
			if(!ReflectionHelper.setFinalStatic(BiomeGenBase.class, boDO, true, "deepOcean", "field_150575_M"))
				LogHelper.fatal("Failed to override deep ocean biome! Loading worlds generated with Better Oceans may have unpredictable results!");
			else
				BiomeGenBase.getBiomeGenArray()[boDO.biomeID] = boDO;*/
	}

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

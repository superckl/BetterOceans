package me.superckl.betteroceans.common.utility;

import java.util.ArrayList;
import java.util.List;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.asm.ClassTransformer;
import me.superckl.betteroceans.common.gen.BiomeGenBetterDeepOcean;
import me.superckl.betteroceans.common.gen.BiomeGenBetterOcean;
import me.superckl.betteroceans.common.reference.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

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
		BiomeDictionary.registerBiomeType(boO, Type.OCEAN);
		BiomeDictionary.registerBiomeType(boDO, Type.OCEAN);
		BiomeHelper.oceanBiomeIDs.add(boO.biomeID);
		BiomeHelper.oceanBiomeIDs.add(boDO.biomeID);
		LogHelper.debug("Succesfully replaced Ocean biomes!");
	}

	public static boolean isOcean(final World world, final int chunkX, final int chunkZ, final int ... additions){
		final int baseX = chunkX << 4; final int baseZ = chunkZ << 4;
		for(int i = 0; i < 16; i++)
			for(int j = 0; j < 16; j++)
				if(!BiomeHelper.isOceanPrecise(world, baseX+i, baseZ+j, additions))
					return false;
		return true;
	}

	public static boolean isOceanPrecise(final World world, final int x, final int z, final int ... additions){
		final int id = world.getBiomeGenForCoords(x, z).biomeID;
		for(final Integer integ:BiomeHelper.oceanBiomeIDs)
			if(id == integ.intValue())
				return true;
		for(final int j:additions)
			if(id == j)
				return true;
		return false;
	}

	public static boolean isBeach(final World world, final int chunkX, final int chunkZ){
		final int baseX = chunkX << 4; final int baseZ = chunkZ << 4;
		for(int i = 0; i < 16; i++)
			for(int j = 0; j < 16; j++)
				if(!BiomeHelper.isBeachPrecise(world, baseX+i, baseZ+j))
					return false;
		return true;
	}

	public static boolean isBeachPrecise(final World world, final int x, final int z){
		return world.getBiomeGenForCoords(x, z).biomeID == BiomeGenBase.beach.biomeID;
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

	public static boolean isWaterSalineAt(final World world, final int x, final int z){
		return BiomeHelper.isOceanPrecise(world, x, z) || BiomeHelper.isBeachPrecise(world, x, z);
	}

	public static Block getWaterBlockFor(final World world, final int x, final int z){
		return BiomeHelper.isWaterSalineAt(world, x, z) ? ModBlocks.saltWater:Blocks.water;
	}

	public static Block getWaterBlockFor(final World world, int blockArrayIndex, final int chunkX, final int chunkZ){
		blockArrayIndex /= 256;
		final int z = blockArrayIndex / 16;
		final int x = blockArrayIndex % 16;
		return BiomeHelper.getWaterBlockFor(world, x+(chunkX << 4), z+(chunkZ << 4));
	}

	public static Block getWaterBlockFor(final BiomeGenBase biome){
		for(final Integer i:BiomeHelper.oceanBiomeIDs)
			if(i.intValue() == biome.biomeID)
				return ModBlocks.saltWater;
		return biome.biomeID == BiomeGenBase.beach.biomeID ? ModBlocks.saltWater:Blocks.water;
	}

}

package me.superckl.betteroceans.gen;

import java.util.Random;

import me.superckl.betteroceans.reference.ModData;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenBetterOceansDeepOcean extends BiomeGenBase{

	public BiomeGenBetterOceansDeepOcean() {
		super(BiomeGenBase.deepOcean.biomeID);
		this.setColor(48).setBiomeName(ModData.DEEP_OCEAN_BIOME_NAME).setHeight(BiomeGenBase.height_DeepOceans);
		this.spawnableCreatureList.clear();
		this.topBlock = Blocks.sand;
		this.rootHeight = -1.2F;
		this.heightVariation = 0.27F;
	}

	@Override
	public void decorate(final World par1World, final Random par2Random, final int par3, final int par4){
		super.decorate(par1World, par2Random, par3, par4);
	}

	@Override
	public BiomeGenBase.TempCategory getTempCategory()
	{
		return BiomeGenBase.TempCategory.OCEAN;
	}

	@Override
	public void genTerrainBlocks(final World p_150573_1_, final Random p_150573_2_, final Block[] p_150573_3_, final byte[] p_150573_4_, final int p_150573_5_, final int p_150573_6_, final double p_150573_7_)
	{
		super.genTerrainBlocks(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
	}

}

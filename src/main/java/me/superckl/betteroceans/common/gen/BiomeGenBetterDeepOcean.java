package me.superckl.betteroceans.common.gen;

import java.util.Random;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.reference.ModData;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenBetterDeepOcean extends BiomeGenBase{

	public BiomeGenBetterDeepOcean() {
		super(BiomeGenBase.deepOcean.biomeID);
		this.setColor(48).setBiomeName(ModData.DEEP_OCEAN_BIOME_NAME).setHeight(BiomeGenBase.height_DeepOceans);
		this.spawnableCreatureList.clear();
		this.topBlock = Blocks.sand;
		this.fillerBlock = Blocks.sand;
		this.rootHeight = -1.2F;
		this.heightVariation = 0.27F;
	}

	@Override
	public void decorate(final World world, final Random random, final int chunkX, final int chunkZ){
		super.decorate(world, random, chunkX, chunkZ);
	}

	@Override
	public BiomeDecorator createBiomeDecorator(){
		return BetterOceans.getInstance().getConfig().isOtherDecoration() ? this.getModdedBiomeDecorator(new BiomeDecoratorBetterOcean()):new BiomeDecoratorBetterOcean();
	}

	@Override
	public BiomeGenBase.TempCategory getTempCategory()
	{
		return BiomeGenBase.TempCategory.OCEAN;
	}

	@Override
	public void genTerrainBlocks(final World world, final Random random, final Block[] p_150573_3_, final byte[] p_150573_4_, final int p_150573_5_, final int p_150573_6_, final double p_150573_7_)
	{
		super.genTerrainBlocks(world, random, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
	}

}

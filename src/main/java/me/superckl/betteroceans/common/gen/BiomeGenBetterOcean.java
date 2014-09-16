package me.superckl.betteroceans.common.gen;

import java.util.Random;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.reference.ModData;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenBetterOcean extends BiomeGenBase{

	public BiomeGenBetterOcean(final int id) {
		super(id);
		this.setColor(112).setBiomeName(ModData.OCEAN_BIOME_NAME).setHeight(BiomeGenBase.height_Oceans);
		this.spawnableCreatureList.clear();
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

}

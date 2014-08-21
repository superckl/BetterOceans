package me.superckl.betteroceans.common.gen;

import net.minecraft.world.biome.BiomeDecorator;

public class BiomeDecoratorBetterOcean extends BiomeDecorator{

	public BiomeDecoratorBetterOcean() {
		super();
		this.clayGen = new WorldGenBetterOceansClay();
	}

	@Override
	protected void generateOres(){/*No ores for you*/}

}

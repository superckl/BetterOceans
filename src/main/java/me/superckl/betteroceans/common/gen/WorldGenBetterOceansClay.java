package me.superckl.betteroceans.common.gen;

import java.util.Random;

import me.superckl.betteroceans.common.utility.BiomeHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenClay;

public class WorldGenBetterOceansClay extends WorldGenClay{

	public WorldGenBetterOceansClay() {
		super(4);
	}

	@Override
	public boolean generate(final World world, final Random random, final int x, final int y, final int z){
		if(BiomeHelper.isOcean(world, x >> 4, z >> 4) || BiomeHelper.isBeach(world, x >> 4, z >> 4))
			return false;
		return super.generate(world, random, x, y, z);
	}


}

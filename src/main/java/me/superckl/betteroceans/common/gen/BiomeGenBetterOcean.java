package me.superckl.betteroceans.common.gen;

import java.util.Random;

import me.superckl.betteroceans.common.reference.ModData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenBetterOcean extends BiomeGenBase{

	public BiomeGenBetterOcean() {
		super(BiomeGenBase.ocean.biomeID);
		this.setColor(112).setBiomeName(ModData.OCEAN_BIOME_NAME).setHeight(BiomeGenBase.height_Oceans);
		this.spawnableCreatureList.clear();
		this.topBlock = Blocks.sand;
		this.fillerBlock = Blocks.sand;
		this.rootHeight = -.8F;
		this.heightVariation = .18F;
	}

	@Override
	public void decorate(final World world, final Random random, final int chunkX, final int chunkZ){
		super.decorate(world, random, chunkX, chunkZ);
	}

	@Override
	public BiomeDecorator createBiomeDecorator(){
		return new BiomeDecoratorBetterOcean();
	}

	@Override
	public BiomeGenBase.TempCategory getTempCategory()
	{
		return BiomeGenBase.TempCategory.OCEAN;
	}

	@Override
	public void genTerrainBlocks(final World world, final Random random, final Block[] p_150560_3_, final byte[] p_150560_4_, final int p_150560_5_, final int p_150560_6_, final double p_150560_7_)
	{
		Block block = this.topBlock;
		byte b0 = (byte)(this.field_150604_aj & 255);
		Block block1 = this.fillerBlock;
		int k = -1;
		final int l = (int)(p_150560_7_ / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		final int i1 = p_150560_5_ & 15;
		final int j1 = p_150560_6_ & 15;
		final int k1 = p_150560_3_.length / 256;

		for (int l1 = 255; l1 >= 0; --l1)
		{
			final int i2 = (j1 * 16 + i1) * k1 + l1;

			if (l1 <= 0 + random.nextInt(5))
				p_150560_3_[i2] = Blocks.bedrock;
			else
			{
				final Block block2 = p_150560_3_[i2];

				if (block2 != null && block2.getMaterial() != Material.air)
				{
					if (block2 == Blocks.stone)
						if (k == -1)
						{
							if (l <= 0)
							{
								block = null;
								b0 = 0;
								block1 = Blocks.stone;
							}
							else if (l1 >= 59 && l1 <= 64)
							{
								block = this.topBlock;
								b0 = (byte)(this.field_150604_aj & 255);
								block1 = this.fillerBlock;
							}

							if (l1 < 63 && (block == null || block.getMaterial() == Material.air))
								if (this.getFloatTemperature(p_150560_5_, l1, p_150560_6_) < 0.15F)
								{
									block = Blocks.ice;
									b0 = 0;
								}
								else
								{
									block = Blocks.water;
									b0 = 0;
								}

							k = l;

							if (l1 >= 62)
							{
								p_150560_3_[i2] = block;
								p_150560_4_[i2] = b0;
							}
							else if (l1 < 56 - l)
							{
								block = null;
								block1 = Blocks.stone;
								p_150560_3_[i2] = Blocks.sand;
							} else
								p_150560_3_[i2] = block1;
						}
						else if (k > 0)
						{
							--k;
							p_150560_3_[i2] = block1;

							if (k == 0 && block1 == Blocks.sand)
							{
								k = random.nextInt(4) + Math.max(0, l1 - 63);
								block1 = Blocks.sandstone;
							}
						}
				} else
					k = -1;
			}
		}
	}

}

package me.superckl.betteroceans.gen;

import java.util.Random;

import lombok.Getter;
import lombok.Setter;
import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.block.BlockSeaweed;
import me.superckl.betteroceans.reference.ModBlocks;
import me.superckl.betteroceans.utility.BlockHelper;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

public class SeaweedDecorator implements IWorldGenerator{
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(!BetterOceans.getInstance().getConfig().isGenSeaweed())
			return;
		Block block = null;
		int tries = BetterOceans.getInstance().getConfig().getSeaweedWaterBlockTries();
		int baseX = chunkX, baseZ = chunkZ;
		while(block != Blocks.water && tries-- > 0){
			baseX = chunkX*16 + random.nextInt(16)+8;
			baseZ = chunkZ*16 + random.nextInt(16)+8;
			int y = world.getTopSolidOrLiquidBlock(baseX, baseZ);
			block = world.getBlock(baseX, y, baseZ);
		}
		if(block != Blocks.water)
			return;
		for (int i = 0; i < BetterOceans.getInstance().getConfig().getSeaweedTries(); ++i)
        {
            int newX = baseX + random.nextInt(8) - random.nextInt(8);
            int newZ = baseZ + random.nextInt(8) - random.nextInt(8);
            int newY = world.getTopSolidOrLiquidBlock(newX, newZ);
            block = world.getBlock(newX, newY, newZ);
            if(block != Blocks.water)
            	continue;
            int depth = BlockHelper.getHeight(world, newX, newY, newZ, false);
            if(depth < 3)
            	continue;
            int maxStack = Math.min(depth-2, 2);
            int toStack = 0;
            if(maxStack > 0)
            	toStack = (int) Math.round(random.nextDouble()*maxStack);
            int height = toStack+1;
            int startY = newY;
            world.setBlock(newX, startY, newZ, ModBlocks.seaweed, BlockSeaweed.getMetaFor(height, 0), 1 & 2);
            while(toStack > 0){
            	world.setBlock(newX, ++startY, newZ, ModBlocks.seaweed, BlockSeaweed.getMetaFor(height, height-toStack), 1 & 2);
            	toStack--;
            }
        }
	}
}

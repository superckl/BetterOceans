package me.superckl.betteroceans.gen;

import me.superckl.betteroceans.utility.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenCaves;

public class MapGenBetterOceansCaves extends MapGenCaves{

	@Override
	public void func_151539_a(final IChunkProvider provider, final World world, final int chunkX, final int chunkZ, final Block[] viableBlocks){
		if(BlockHelper.isOcean(world, chunkX, chunkZ))
			return;
		super.func_151539_a(provider, world, chunkX, chunkZ, viableBlocks);
	}


}

package me.superckl.betteroceans.common.gen;

import lombok.RequiredArgsConstructor;
import me.superckl.betteroceans.common.utility.BiomeHelper;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;

@RequiredArgsConstructor
public class MapGenBetterOceansCaves extends MapGenCaves{

	private final MapGenBase parent;

	@Override
	public void func_151539_a(final IChunkProvider provider, final World world, final int chunkX, final int chunkZ, final Block[] viableBlocks){
		if(BiomeHelper.isOcean(world, chunkX, chunkZ) || BiomeHelper.isBeach(world, chunkX, chunkZ))
			return;
		this.parent.func_151539_a(provider, world, chunkX, chunkZ, viableBlocks);
	}


}

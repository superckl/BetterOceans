package me.superckl.betteroceans.utility;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockHelper {

	public static boolean isWaterSourceAt(World world, int x, int y, int z, Block ... exceptions){
		Block block = world.getBlock(x, y, z);
		if(Blocks.water == block)
			return true;
		for(Block block1:exceptions)
			if(block == block1)
				return true;
		return false;
	}
	
	/**
	 * Counts how many of the same block of the same type are underneath the specified block, counting the specified block
	 */
	public static int getHeight(World world, int x, int y, int z, boolean countDown){
		Block block = world.getBlock(x, y, z);
		int count = 0;
		while(world.getBlock(x, y, z) == block){
			count++;
			if(countDown)
				y--;
			else
				y++;
			if(count > 256)
				break; //Infinite loop???
		}
		return count;
	}
	
}

package me.superckl.betteroceans.common.utility;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BlockHelper {

	public static boolean isWaterSourceAt(final World world, final int x, final int y, final int z, final Block ... exceptions){
		final Block block = world.getBlock(x, y, z);
		if(Blocks.water == block)
			return true;
		for(final Block block1:exceptions)
			if(block == block1)
				return true;
		return false;
	}

	/**
	 * Counts how many of the same block of the same type are underneath the specified block, counting the specified block
	 */
	public static int getHeight(final World world, final int x, int y, final int z, final boolean countDown){
		final Block block = world.getBlock(x, y, z);
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

	public static int getMinHeightInChunk(final World world, final int chunkX, final int chunkZ, final Block block){
		final int baseX = chunkX << 4; final int baseZ = chunkZ << 4;
		int minHeight = 256;
		for(int i = 0; i <= 15; i++){
			final int x = baseX+i; final int z = baseZ+i;
			final int y = world.getTopSolidOrLiquidBlock(x, z);
			if(world.getBlock(x, y, z) != block)
				return 0;
			final int height = BlockHelper.getHeight(world, x, y, z, false);
			if(height < minHeight)
				minHeight = height;
		}
		return minHeight;
	}

	public static int getMaxHeightInChunk(final World world, final int chunkX, final int chunkZ, final Block block){
		final int baseX = chunkX << 4; final int baseZ = chunkZ << 4;
		int maxHeight = 0;
		for(int i = 0; i <= 15; i++){
			final int x = baseX+i; final int z = baseZ+i;
			final int y = world.getTopSolidOrLiquidBlock(x, z);
			if(world.getBlock(x, y, z) != block)
				continue;
			final int height = BlockHelper.getHeight(world, x, y, z, false);
			if(height > maxHeight)
				maxHeight = height;
		}
		return maxHeight;
	}

	public static int getOceanFloorInChunk(final World world, final int chunkX, final int chunkZ){
		final int baseX = chunkX << 4; final int baseZ = chunkZ << 4;
		int maxHeight = 0;
		int minY = 0;
		for(int i = 0; i <= 15; i++){
			final int x = baseX+i; final int z = baseZ+i;
			final int y = world.getTopSolidOrLiquidBlock(x, z);
			if(world.getBlock(x, y, z) != Blocks.water)
				continue;
			final int height = BlockHelper.getHeight(world, x, y, z, false);
			if(height > maxHeight){
				maxHeight = height;
				minY = y-1;
			}
		}
		return minY;
	}

	public static int getFluidDepth(final World world, final int x, int y, final int z){
		final Fluid fluid = FluidRegistry.lookupFluidForBlock(world.getBlock(x, y, z));
		if(fluid == null)
			return 0;
		Fluid newFluid = null;
		int count = 0;
		do{
			count++;
			newFluid = FluidRegistry.lookupFluidForBlock(world.getBlock(x, --y, z));
		}while(fluid == newFluid);
		return count;
	}

	public static boolean isSwimming(final EntityPlayer player){
		return !player.onGround && BlockHelper.getFluidDepth(player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ) > 0;
	}

}

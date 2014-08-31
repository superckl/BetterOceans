package me.superckl.betteroceans.common.utility;

import java.util.ArrayList;
import java.util.List;

import me.superckl.betteroceans.common.reference.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BlockHelper {

	public static boolean isWaterSourceAt(final World world, final int x, final int y, final int z, final Block ... exceptions){
		final Block block = world.getBlock(x, y, z);
		return BlockHelper.isWaterSource(block, exceptions);
	}

	public static boolean isWaterSource(final Block block, final Block ... exceptions){
		if(Blocks.water == block || ModBlocks.saltWater == block)
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

			if(!BlockHelper.isWaterSourceAt(world, x, y, z))
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
		Fluid newFluid;
		int count = 0;
		do{
			count++;
			newFluid = FluidRegistry.lookupFluidForBlock(world.getBlock(x, --y, z));
		}while(fluid == newFluid);
		return count;
	}

	public static Block getWaterReplacement(final World world, final int x, final int y, final int z){
		int water = 0;
		int sWater = 0;
		for(final Block block:BlockHelper.getBlocksAround(world, x, y, z))
			if(block == Blocks.water)
				water++;
			else
				sWater++;
		return water > sWater ? Blocks.water:ModBlocks.saltWater;
	}

	public static Vec3 shiftLoc(final Vec3 position, final ForgeDirection direction){
		switch(direction){
		case DOWN:
			return position.addVector(0D, -1D, 0D);
		case EAST:
			return position.addVector(1D, 0D, 0D);
		case NORTH:
			return position.addVector(0D, 0D, -1D);
		case SOUTH:
			return position.addVector(0D, 0D, 1D);
		case UNKNOWN:
			return position;
		case UP:
			return position.addVector(0D, 1D, 0D);
		case WEST:
			return position.addVector(1D, 0D, 0D);
		default:
			return position;
		}
	}

	public static List<Block> getBlocksAround(final World world, final int x, final int y, final int z){
		final List<Block> blocks = new ArrayList<Block>();
		final Vec3 base = Vec3.createVectorHelper(x, y, z);

		Vec3 shift = BlockHelper.shiftLoc(base, ForgeDirection.NORTH);
		blocks.add(world.getBlock((int) shift.xCoord, (int) shift.yCoord, (int) shift.zCoord));

		shift = BlockHelper.shiftLoc(base, ForgeDirection.NORTH);
		shift = BlockHelper.shiftLoc(shift, ForgeDirection.EAST);
		blocks.add(world.getBlock((int) shift.xCoord, (int) shift.yCoord, (int) shift.zCoord));

		shift = BlockHelper.shiftLoc(base, ForgeDirection.EAST);
		blocks.add(world.getBlock((int) shift.xCoord, (int) shift.yCoord, (int) shift.zCoord));

		shift = BlockHelper.shiftLoc(base, ForgeDirection.EAST);
		shift = BlockHelper.shiftLoc(shift, ForgeDirection.SOUTH);
		blocks.add(world.getBlock((int) shift.xCoord, (int) shift.yCoord, (int) shift.zCoord));

		shift = BlockHelper.shiftLoc(base, ForgeDirection.SOUTH);
		blocks.add(world.getBlock((int) shift.xCoord, (int) shift.yCoord, (int) shift.zCoord));

		shift = BlockHelper.shiftLoc(base, ForgeDirection.SOUTH);
		shift = BlockHelper.shiftLoc(shift, ForgeDirection.WEST);
		blocks.add(world.getBlock((int) shift.xCoord, (int) shift.yCoord, (int) shift.zCoord));

		shift = BlockHelper.shiftLoc(base, ForgeDirection.WEST);
		blocks.add(world.getBlock((int) shift.xCoord, (int) shift.yCoord, (int) shift.zCoord));

		shift = BlockHelper.shiftLoc(base, ForgeDirection.WEST);
		shift = BlockHelper.shiftLoc(shift, ForgeDirection.NORTH);
		blocks.add(world.getBlock((int) shift.xCoord, (int) shift.yCoord, (int) shift.zCoord));

		return blocks;
	}

	public static boolean isSwimming(final EntityPlayer player){
		return !player.onGround && BlockHelper.getFluidDepth(player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ) > 0;
	}

}

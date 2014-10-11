package me.superckl.betteroceans.common.block;

import java.util.List;
import java.util.Random;

import lombok.experimental.ExtensionMethod;
import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.reference.ModBlocks;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.utility.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@ExtensionMethod(BlockHelper.class)
public class BlockSeaweed extends BlockBO{

	public BlockSeaweed(){
		super(Material.water);
		this.setBlockName("seaweed").setHardness(0F).setTickRandomly(true);
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}



	@Override
	public ItemStack getPickBlock(final MovingObjectPosition target, final World world,
			final int x, final int y, final int z) {
		return new ItemStack(ModItems.itemSeaweed);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final int x, final int y, final int z, final int s)
	{
		return !BlockHelper.isWaterSource(blockAccess.getBlock(x, y, z));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_)
	{
		return null;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return true;
	}

	@Override
	public int getRenderType(){
		return 1;
	}

	@Override
	public int quantityDropped(final Random rand)
	{
		return 1+(int)Math.round(2*rand.nextDouble());
	}

	@Override
	public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_)
	{
		return ModItems.itemSeaweed;
	}

	@Override
	public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_)
	{
		return false;
	}

	@Override
	public void onBlockDestroyedByPlayer(final World world, final int x, final int y, final int z, final int meta) {
		world.setBlock(x, y, z, BetterOceans.getInstance().getConfig().isFluidReplace() ? BlockHelper.getWaterReplacement(world, x, y, z):Blocks.air);
	}


	@Override
	public void breakBlock(final World world, final int x, final int y, final int z, final Block p_149749_5_, final int p_149749_6_)
	{
		if(!this.isTop(world, x, y, z)){
			this.dropBlockAsItem(world, x, y+1, z, world.getBlockMetadata(x, y+1, z), 0);
			this.breakBlock(world, x, y+1, z, p_149749_5_, p_149749_6_);
			world.setBlock(x, y+1, z, BetterOceans.getInstance().getConfig().isFluidReplace() ? BlockHelper.getWaterReplacement(world, x, y+1, z):Blocks.air);
		}
		super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
	}

	@Override
	public void updateTick(final World world, final int x, final int y, final int z, final Random rand) {
		if(world.isRemote)
			return;
		if(!this.isTop(world, x, y, z))
			return;
		final int height = this.getHeight(world, x, y, z);
		final int meta = world.getBlockMetadata(x, y, z);
		final int maxHeight = meta == 0 ? 1:(meta & 1) == 1 ? 2:3;
		if(maxHeight <= height || rand.nextInt(15) != 0)
			return;
		if(!this.canBlockStay(world, x, y+1, z))
			return;
		final int newMeta = BlockSeaweed.getMetaFor(height, height);
		world.setBlock(x, y+1, z, ModBlocks.seaweed, newMeta, 1 & 2);
	}

	@Override
	public void onNeighborBlockChange(final World world, final int x, final int y, final int z, final Block block) {
		if(!this.canBlockStay(world, x, y, z)){
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			this.breakBlock(world, x, y, z, block, world.getBlockMetadata(x, y, z));
			world.setBlock(x, y, z, BetterOceans.getInstance().getConfig().isFluidReplace() ? BlockHelper.getWaterReplacement(world, x, y, z):Blocks.air);
		}
	}



	@Override
	public int onBlockPlaced(final World world, final int x,
			final int y, final int z, final int side,
			final float hitX, final float hitY, final float hitZ,
			final int meta) {
		if(world.getBlock(x, y-1, z) == this){
			final int metaBelow = world.getBlockMetadata(x, y-1, z);
			if(metaBelow == 2)
				return meta;
			else if((metaBelow & 1) == 1 && (metaBelow & 4) == 4)
				return 1 + 8;
			else if((metaBelow & 2) == 2)
				if((metaBelow & 4) == 4)
					return 2 + 8;
				else if((metaBelow & 8) == 8)
					return 2;
		}
		return meta;
	}

	@Override
	public boolean isReplaceable(final IBlockAccess world, final int x, final int y, final int z)
	{
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(final World world, final int x, final int y, final int z){
		return this.canBlockStay(world, x, y, z);
	}

	@Override
	public boolean canBlockStay(final World world, final int x, final int y, final int z){
		final Block below = world.getBlock(x, y-1, z);
		//LogHelper.info(below.getUnlocalizedName());
		if(!(below == Blocks.sand || below == Blocks.dirt || below == Blocks.gravel || below == this))
			return false;
		final Block above = world.getBlock(x, y+1, z);
		//LogHelper.info(above.getUnlocalizedName());
		if(!BlockHelper.isWaterSource(above, this))
			return false;
		final List<Block> blocks = BlockHelper.getBlocksAround(world, x, y, z);
		for(int i = 0; i < blocks.size(); i++){
			final boolean corner0 = BlockHelper.isWaterSource(blocks.get(i++));
			final boolean corner1 = BlockHelper.isWaterSource(blocks.get(i++));
			boolean corner2;
			if(i == blocks.size())
				corner2 = BlockHelper.isWaterSource(blocks.get(0));
			else
				corner2 = BlockHelper.isWaterSource(blocks.get(i));
			if(corner0 && corner1 && corner2)
				return true;
		}
		return false;
		/*boolean corner0, corner1, corner2;
		//Begin testing corners
		corner0 = BlockHelper.isWaterSourceAt(world, x+1, y, z);
		corner1 = BlockHelper.isWaterSourceAt(world, x+1, y, z+1);
		corner2 = BlockHelper.isWaterSourceAt(world, x, y, z+1);
		//LogHelper.info(StringHelper.build(corner0, corner1, corner2));
		if(corner0 && corner1 && corner2)
			return true;

		corner0 = corner2;
		corner1 = BlockHelper.isWaterSourceAt(world, x-1, y, z+1);
		corner2 = BlockHelper.isWaterSourceAt(world, x-1, y, z);
		//LogHelper.info(StringHelper.build(corner0, corner1, corner2));
		if(corner0 && corner1 && corner2)
			return true;

		corner0 = corner2;
		corner1 = BlockHelper.isWaterSourceAt(world, x-1, y, z-1);
		corner2 = BlockHelper.isWaterSourceAt(world, x, y, z-1);
		//LogHelper.info(StringHelper.build(corner0, corner1, corner2));
		if(corner0 && corner1 && corner2)
			return true;

		corner0 = corner2;
		corner1 = BlockHelper.isWaterSourceAt(world, x+1, y, z-1);
		corner2 = BlockHelper.isWaterSourceAt(world, x+1, y, z);
		//LogHelper.info(StringHelper.build(corner0, corner1, corner2));
		return corner0 && corner1 && corner2;*/

	}

	private int getHeight(final World world, final int x, int y, final int z){
		int runs = 0;
		while(!this.isTop(world, x, y, z)){
			y++;
			if(++runs > 256) //Infinite loop prevention
				return 256;
		}
		return BlockHelper.getHeight(world, x, y, z, true);
	}

	private boolean isTop(final World world, final int x, final int y, final int z){
		return world.getBlock(x, y+1, z) != this;
	}

	private IIcon[] icons;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(final IIconRegister register){
		this.icons = new IIcon[]
				{register.registerIcon(ModData.MOD_ID+":seaweed1"),
				register.registerIcon(ModData.MOD_ID+":seaweed20"),
				register.registerIcon(ModData.MOD_ID+":seaweed21"),
				register.registerIcon(ModData.MOD_ID+":seaweed30"),
				register.registerIcon(ModData.MOD_ID+":seaweed31"),
				register.registerIcon(ModData.MOD_ID+":seaweed32")};
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final int side, final int meta)
	{
		int pos = 0;
		if(meta == 0)
			pos = 0;
		else if((meta & 1) == 1){
			if((meta & 4) == 4)
				pos = 1;
			else if((meta & 8) == 8)
				pos = 2;
		}else if((meta & 2) == 2)
			if((meta & 4) == 4)
				pos = 3;
			else if((meta & 8) == 8)
				pos = 4;
			else if (meta == 2)
				pos = 5;
		return this.icons[pos];
	}

	public static int getMetaFor(final int height, final int pos){
		if(height == 1)
			return 0;
		else if(height == 2){
			if(pos == 0)
				return 1 + 4;
			else if (pos == 1)
				return 1 + 8;
		}else if(height == 3)
			if(pos == 0)
				return 2 + 4;
			else if(pos == 1)
				return 2 + 8;
			else if(pos == 2)
				return 2;
		return 0;
	}
}

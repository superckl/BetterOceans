package me.superckl.betteroceans.common.block;

import java.util.List;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.reference.ModBlocks;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModTabs;
import me.superckl.betteroceans.common.utility.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSoftCoral extends BlockBO{

	public BlockSoftCoral() {
		super(Material.water);
		this.setBlockName("softcoral").setCreativeTab(ModTabs.tabBlocks).setHardness(0F);
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
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
	public int getRenderType(){
		return 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(final Item item, final CreativeTabs tab, final List list)
	{
		list.add(new ItemStack(item, 1, 0));
	}

	@Override
	public void onBlockDestroyedByPlayer(final World world, final int x, final int y, final int z, final int meta) {
		world.setBlock(x, y, z, BetterOceans.getInstance().getConfig().isFluidReplace() ? BlockHelper.getWaterReplacement(world, x, y, z):Blocks.air);
	}

	@Override
	public boolean canPlaceBlockAt(final World world, final int x, final int y, final int z){
		return this.canBlockStay(world, x, y, z);
	}

	@Override
	public boolean canBlockStay(final World world, final int x, final int y, final int z){
		final Block below = world.getBlock(x, y-1, z);
		//LogHelper.info(below.getUnlocalizedName());
		if(below != ModBlocks.hardCoral)
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

	}

	private IIcon[] icons;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(final IIconRegister register){
		this.icons = new IIcon[] {register.registerIcon(ModData.MOD_ID+":softcoral0")};
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final int side, final int meta)
	{
		return this.icons[meta];
	}

}

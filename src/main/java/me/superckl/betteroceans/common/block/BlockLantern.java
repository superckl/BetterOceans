package me.superckl.betteroceans.common.block;

import java.util.List;

import me.superckl.betteroceans.common.entity.tile.TileEntityLantern;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.reference.ModTabs;
import me.superckl.betteroceans.common.reference.RenderData;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.NumberHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLantern extends BlockContainerBO{

	public BlockLantern() {
		super(Material.rock);
		this.setBlockName("lantern").setStepSound(Block.soundTypeStone).setCreativeTab(ModTabs.tabBlocks);
		this.setHarvestLevel("pickaxe", 1);
		this.setBlockBounds(4.5F*RenderData.pixel, 0F, 4.5F*RenderData.pixel, 11.5F*RenderData.pixel, 14F*RenderData.pixel, 11.5F*RenderData.pixel);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(final Item item, final CreativeTabs tab, final List list)
	{
		for(int i = 0; i < 5; i ++)
			list.add(new ItemStack(item, 1, i));
	}



	@Override
	public void onBlockPlacedBy(final World world, final int x, final int y, final int z, final EntityLivingBase entity, final ItemStack stack) {
		//TODO
		LogHelper.info(NumberHelper.normalizeAngle(entity.rotationYaw));
		final int i = (int) Math.floor(NumberHelper.normalizeAngle(entity.rotationYaw)/360F*9)%8;
		LogHelper.info(i);
		final TileEntityLantern te = (TileEntityLantern) world.getTileEntity(x, y, z);
		//Using a switch statement here so it's obvious how it changes as it goes around. (turning counterclockwise)
		switch(i){
		case 7:
			te.setRotation(.4F);
			break;
		case 6:
			te.setRotation(1F);
			break;
		case 5:
			te.setRotation(-.4F);
			break;
		case 3:
			te.setRotation(.4F);
			break;
		case 2:
			te.setRotation(1F);
			break;
		case 1:
			te.setRotation(-.4F);
			break;
		}
	}

	@Override
	public void registerBlockIcons(final IIconRegister p_149651_1_) {
		this.blockIcon = Blocks.anvil.getIcon(0, 0);
	}

	@Override
	public int damageDropped(final int meta) {
		return meta;
	}

	@Override
	public int getLightValue() {
		return 14;
	}

	@Override
	public int getRenderType()
	{
		return -2;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}


	@Override
	public TileEntity createNewTileEntity(final World world, final int meta) {
		return new TileEntityLantern(new ItemStack(ModItems.lumPowder, 1, meta));
	}

}

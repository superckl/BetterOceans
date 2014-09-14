package me.superckl.betteroceans.common.block;

import java.util.List;

import me.superckl.betteroceans.common.entity.tile.TileEntityLantern;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.reference.ModTabs;
import me.superckl.betteroceans.common.reference.RenderData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLantern extends BlockContainerBO{

	public BlockLantern() {
		super(Material.rock);
		this.setBlockName("lantern").setStepSound(Block.soundTypeStone).setCreativeTab(ModTabs.tabBlocks);
		this.setHarvestLevel("pickaxe", 1);
		this.setBlockBounds(3F*RenderData.pixel, 0F, 3F*RenderData.pixel, 13F*RenderData.pixel, 13F*RenderData.pixel, 13F*RenderData.pixel);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(final Item item, final CreativeTabs tab, final List list)
	{
		for(int i = 0; i < 5; i ++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	public int damageDropped(final int meta) {
		return meta;
	}

	@Override
	public int getLightValue() {
		return 15;
	}

	private IIcon[] icons;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(final IIconRegister register){
		this.icons = new IIcon[5];
		for(int i = 0; i < 5; i ++)
			this.icons[i] = register.registerIcon(ModData.MOD_ID+":lantern"+i);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final int side, final int meta)
	{
		return this.icons[meta];
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

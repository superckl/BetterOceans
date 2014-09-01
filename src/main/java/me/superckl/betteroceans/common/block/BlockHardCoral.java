package me.superckl.betteroceans.common.block;

import java.util.List;

import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHardCoral extends BlockBO{

	public BlockHardCoral() {
		super(Material.coral);
		this.setBlockName("hardcoral").setCreativeTab(ModTabs.tabBlocks).setHardness(2F).setStepSound(Block.soundTypeStone).setHarvestLevel("pickaxe", 1);
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

	private IIcon[] icons;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(final IIconRegister register){
		this.icons = new IIcon[5];
		for(int i = 0; i < 5; i ++)
			this.icons[i] = register.registerIcon(ModData.MOD_ID+":hardcoral"+i);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final int side, final int meta)
	{
		return this.icons[meta];
	}

}

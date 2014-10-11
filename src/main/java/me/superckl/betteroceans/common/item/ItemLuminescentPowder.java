package me.superckl.betteroceans.common.item;

import java.util.List;

import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemLuminescentPowder extends ItemBO{

	public ItemLuminescentPowder() {
		super();
		this.setHasSubtypes(true);
		this.setCreativeTab(ModTabs.tabItems);
		this.setUnlocalizedName("lumpowder");
	}

	@Override
	protected boolean isNameDamageReliant() {
		return true;
	}

	@Override
	public int getMetadata(final int meta) {
		return meta;
	}

	@Override
	public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
		for(int i = 0; i < 5; i ++)
			list.add(new ItemStack(item, 1, i));
	}

	private IIcon[] icons;

	@Override
	public void registerIcons(final IIconRegister register) {
		this.icons = new IIcon[5];
		for(int i = 0; i < 5; i ++)
			this.icons[i] = register.registerIcon(ModData.MOD_ID+":lumpowder"+i);
	}

	@Override
	public IIcon getIconFromDamage(final int meta) {
		return this.icons[meta];
	}

}

package me.superckl.betteroceans.common.item;

import me.superckl.betteroceans.common.reference.ModData;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;

public class ItemSeaweed extends ItemBO{

	public ItemSeaweed() {
		super();
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName("seaweed");
	}

	@Override
	public void registerIcons(final IIconRegister register){
		this.itemIcon = register.registerIcon(ModData.MOD_ID+":seaweed");
	}

}

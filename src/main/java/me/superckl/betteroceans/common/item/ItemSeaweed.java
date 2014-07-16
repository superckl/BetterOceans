package me.superckl.betteroceans.common.item;

import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModTabs;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemSeaweed extends ItemBO{

	public ItemSeaweed() {
		super();
		this.setCreativeTab(ModTabs.tabItems);
		this.setUnlocalizedName("seaweed");
	}

	@Override
	public void registerIcons(final IIconRegister register){
		this.itemIcon = register.registerIcon(ModData.MOD_ID+":seaweed");
	}

}

package me.superckl.betteroceans.common.item;

import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModTabs;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemCookedSeaweed extends ItemFoodBO{

	public ItemCookedSeaweed() {
		super(1, 0.5F, false);
		this.setUnlocalizedName("cookedseaweed").setCreativeTab(ModTabs.tabItems);
	}

	@Override
	public void registerIcons(final IIconRegister register){
		this.itemIcon = register.registerIcon(ModData.MOD_ID+":cookedseaweed");
	}

}

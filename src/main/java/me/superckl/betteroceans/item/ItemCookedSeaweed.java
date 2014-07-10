package me.superckl.betteroceans.item;

import me.superckl.betteroceans.reference.ModData;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;

public class ItemCookedSeaweed extends ItemFoodBO{

	public ItemCookedSeaweed() {
		super(1, 0.5F, false);
		this.setUnlocalizedName("cookedseaweed");
	}

	@Override
	public void registerIcons(IIconRegister register){
		this.itemIcon = register.registerIcon(ModData.MOD_ID+":cookedseaweed");
	}
	
}

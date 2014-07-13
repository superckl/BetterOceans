package me.superckl.betteroceans.common.item;

import me.superckl.betteroceans.common.reference.ModData;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemCookedSeaweed extends ItemFoodBO{

	public ItemCookedSeaweed() {
		super(1, 0.5F, false);
		this.setUnlocalizedName("cookedseaweed");
	}

	@Override
	public void registerIcons(final IIconRegister register){
		this.itemIcon = register.registerIcon(ModData.MOD_ID+":cookedseaweed");
	}

}

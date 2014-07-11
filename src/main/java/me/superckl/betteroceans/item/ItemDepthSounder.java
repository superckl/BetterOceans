package me.superckl.betteroceans.item;

import me.superckl.betteroceans.reference.ModBlocks;
import me.superckl.betteroceans.reference.ModData;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDepthSounder extends ItemBO{

	public ItemDepthSounder() {
		super();
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName("depthsounder");
	}

	@Override
	public void registerIcons(final IIconRegister register){
		this.itemIcon = register.registerIcon(ModData.MOD_ID+":depthsounder");
	}
	
}

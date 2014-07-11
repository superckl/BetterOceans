package me.superckl.betteroceans.item;

import me.superckl.betteroceans.client.tick.RenderTickHandler;
import me.superckl.betteroceans.reference.ModData;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDepthSounder extends ItemBO{

	public ItemDepthSounder() {
		super();
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName("depthsounder");
	}

	private IIcon[] icons;

	@Override
	public void registerIcons(final IIconRegister register){
		this.icons = new IIcon[7];
		for(int i = 1; i < 8; i++)
			this.icons[i-1] = register.registerIcon(ModData.MOD_ID+":depthSounder"+i);
		//this.itemIcon = register.registerIcon(ModData.MOD_ID+":depthsounder");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(final int damage){
		return this.icons[(int) Math.min(Math.round(RenderTickHandler.lastDepth/8), 6L)];
	}

}

package me.superckl.betteroceans.common.item;

import me.superckl.betteroceans.client.handler.RenderTickHandler;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDepthSounder extends ItemBO{

	public ItemDepthSounder() {
		super();
		this.setCreativeTab(ModTabs.tabItems);
		this.setUnlocalizedName("depthsounder");
	}

	private IIcon[] icons;

	@Override
	public void registerIcons(final IIconRegister register){
		this.icons = new IIcon[7];
		for(int i = 1; i < 8; i++)
			this.icons[i-1] = register.registerIcon(ModData.MOD_ID+":depthSounder"+i);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(final int damage){
		return this.icons[(int) Math.min(Math.round(RenderTickHandler.lastDepth/8), 5L)];
	}

}

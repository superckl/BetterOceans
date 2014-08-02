package me.superckl.betteroceans.common.item;

import java.util.List;

import me.superckl.betteroceans.client.handler.RenderTickHandler;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBoatPart extends ItemBO{

	/*
	 * Damage Values (bit positions):
	 * 
	 * Positions:
	 * 1 - Bottom
	 * 2 - Side
	 * 4 - End
	 * 
	 * Materials:
	 * 8 - Wood
	 */
	
	public ItemBoatPart() {
		super();
		this.setHasSubtypes(true);
		this.setCreativeTab(ModTabs.tabItems);
		this.setUnlocalizedName("boatpart");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(item, 1, 1 & 8));
        list.add(new ItemStack(item, 1, 2 & 8));
        list.add(new ItemStack(item, 1, 4 & 8));
    }

	private IIcon[] icons;

	@Override
	public void registerIcons(final IIconRegister register){
		this.icons = new IIcon[] {register.registerIcon(ModData.MOD_ID+":part_bottom_wood"),
				register.registerIcon(ModData.MOD_ID+":part_side_wood"), register.registerIcon(ModData.MOD_ID+":part_end_wood")};
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(final int damage){
		return this.icons[this.translateDamageToIcon(damage)];
	}
	
	private int translateDamageToIcon(int damage){
		if((damage & 1) == 1){
			if((damage & 8) == 8)
				return 0;
		}else if((damage & 2) == 2){
			if((damage & 8) == 8){
				return 1;
			}
		}else{
			return 2;
		}
		return 0;
	}
}

package me.superckl.betteroceans.common.reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ModTabs{

	public static final CreativeTabs tabBlocks = new CreativeTabs(ModData.MOD_ID+":blocks") {

		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ModBlocks.boatbench);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public String getTranslatedTabLabel()
		{
			return "Better Oceans Blocks";
		}
	};

	public static final CreativeTabs tabItems = new CreativeTabs(ModData.MOD_ID+":items") {

		@Override
		public Item getTabIconItem() {
			return ModItems.itemSeaweed;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public String getTranslatedTabLabel()
		{
			return "Better Oceans Items";
		}
	};

}

package me.superckl.betteroceans.client.gui;

import java.util.ArrayList;
import java.util.List;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.utility.StringHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.GuiConfigEntries.CategoryEntry;
import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class GuiConfigBetterOceans extends BOGuiConfig{

	public GuiConfigBetterOceans(final GuiScreen parentScreen) {
		super(parentScreen, GuiConfigBetterOceans.getConfigElements(), ModData.MOD_ID, false,
				false, LanguageRegistry.instance().getStringLocalization(StringHelper.formatGUIUnlocalizedName("config")), "Configure all the things!");
	}

	private static List<IConfigElement> getConfigElements(){
		final List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyConfigElement.DummyCategoryElement("General", "betteroceans.configgui.ctgy.general", GeneralCategory.class));
		list.add(new DummyConfigElement.DummyCategoryElement("World Generation", "betteroceans.configgui.ctgy.worldgen", WorldGenCategory.class));
		if(Loader.isModLoaded("BiomesOPlenty"))
			list.add(new DummyConfigElement.DummyCategoryElement("Biomes O' Plenty", "betteroceans.configgui.ctgy.bop", BOPCategory.class));
		return list;
	}


	public static class WorldGenCategory extends CategoryEntry{

		public WorldGenCategory(final GuiConfig owningScreen,
				final GuiConfigEntries owningEntryList, final IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new BOGuiConfig(this.owningScreen, new ConfigElement(BetterOceans.getInstance().getConfig().getConfigFile().getCategory("world gen")).getChildElements()
					, ModData.MOD_ID, false, false, LanguageRegistry.instance().getStringLocalization(StringHelper.formatGUIUnlocalizedName("config_worldgen")));
		}

	}

	public static class GeneralCategory extends CategoryEntry{

		public GeneralCategory(final GuiConfig owningScreen,
				final GuiConfigEntries owningEntryList, final IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new BOGuiConfig(this.owningScreen, new ConfigElement(BetterOceans.getInstance().getConfig().getConfigFile().getCategory("general")).getChildElements()
					, ModData.MOD_ID, false, false, LanguageRegistry.instance().getStringLocalization(StringHelper.formatGUIUnlocalizedName("config_general")));
		}

	}

	public static class BOPCategory extends CategoryEntry{

		public BOPCategory(final GuiConfig owningScreen,
				final GuiConfigEntries owningEntryList, final IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(){
			return new BOGuiConfig(this.owningScreen, new ConfigElement(BetterOceans.getInstance().getConfig().getConfigFile().getCategory("biomes o plenty")).getChildElements()
					, ModData.MOD_ID, false, false, LanguageRegistry.instance().getStringLocalization(StringHelper.formatGUIUnlocalizedName("config_bop")));
		}

	}

}

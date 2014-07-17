package me.superckl.betteroceans.client.gui;

import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.utility.StringHelper;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class GuiConfigBetterOceans extends GuiConfig{

	public GuiConfigBetterOceans(final GuiScreen parentScreen) {
		super(parentScreen, ModData.CONFIG_ELEMENTS, ModData.MOD_ID, false,
				false, LanguageRegistry.instance().getStringLocalization(StringHelper.formatGUIUnlocalizedName("config")));
	}

}

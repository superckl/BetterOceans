package me.superckl.betteroceans.client.gui;

import me.superckl.betteroceans.common.reference.ModData;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.config.GuiConfig;

public class GuiConfigBetterOceans extends GuiConfig{

	public GuiConfigBetterOceans(final GuiScreen parentScreen) {
		super(parentScreen, ModData.CONFIG_ELEMENTS, ModData.MOD_ID, false,
				false, "Better Oceans Configuration");
	}

}

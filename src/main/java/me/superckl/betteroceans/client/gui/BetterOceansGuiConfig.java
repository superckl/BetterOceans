package me.superckl.betteroceans.client.gui;

import me.superckl.betteroceans.reference.ModData;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.config.GuiConfig;

public class BetterOceansGuiConfig extends GuiConfig{

	public BetterOceansGuiConfig(final GuiScreen parentScreen) {
		super(parentScreen, ModData.CONFIG_ELEMENTS, ModData.MOD_ID, false,
				false, "Better Oceans Configuration");
	}

}

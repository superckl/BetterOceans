package me.superckl.betteroceans.client.gui;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

public class BOGuiConfig extends GuiConfig{


	public BOGuiConfig(final GuiScreen parentScreen,
			final List<IConfigElement> configElements, final String modID,
			final boolean allRequireWorldRestart, final boolean allRequireMcRestart,
			final String title) {
		super(parentScreen, configElements, modID, allRequireWorldRestart,
				allRequireMcRestart, title);
	}

	public BOGuiConfig(final GuiScreen parentScreen,
			final List<IConfigElement> configElements, final String modId, final boolean worldRestart,
			final boolean mcRestart, final String title, final String title2) {
		super(parentScreen, configElements, modId, worldRestart, mcRestart, title, title2);
	}

}

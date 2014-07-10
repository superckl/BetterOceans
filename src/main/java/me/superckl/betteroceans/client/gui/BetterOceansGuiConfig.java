package me.superckl.betteroceans.client.gui;

import java.util.List;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.reference.ModData;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

public class BetterOceansGuiConfig extends GuiConfig{
	
	public BetterOceansGuiConfig(GuiScreen parentScreen) {
		super(parentScreen, ModData.CONFIG_ELEMENTS, ModData.MOD_ID, false,
				false, "Better Oceans Configuration");
	}

}

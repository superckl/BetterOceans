package me.superckl.betteroceans.reference;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.config.IConfigElement;

public class ModData {

	public static final String MOD_ID = "BetterOceans";
	public static final String MOD_NAME = "Better Oceans";
	public static final String VERSION = "0.1-Alpha";
	public static final String CLIENT_PROXY = "me.superckl.betteroceans.proxy.ClientProxy";
	public static final String SERVER_PROXY = "me.superckl.betteroceans.proxy.ServerProxy";
	public static final String GUI_FACTORY = "me.superckl.betteroceans.client.gui.BetterOceansGuiFactory";
	public static final List<IConfigElement> CONFIG_ELEMENTS = new ArrayList<IConfigElement>();
	public static final String OCEAN_BIOME_NAME = "Better Ocean";
	public static final String DEEP_OCEAN_BIOME_NAME = "Better Deep Ocean";
}

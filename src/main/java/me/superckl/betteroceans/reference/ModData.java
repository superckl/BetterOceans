package me.superckl.betteroceans.reference;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.config.IConfigElement;

public class ModData {

	public static final String MOD_ID = "BetterOceans";
	public static final String MOD_NAME = "Better Oceans";
	public static final String VERSION = "1.0";
	public static final String CLIENT_PROXY = "me.superckl.betteroceans.proxy.ClientProxy";
	public static final String SERVER_PROXY = "me.superckl.betteroceans.proxy.ServerProxy";
	public static final String GUI_FACTORY = "me.superckl.betteroceans.client.gui.BetterOceansGuiFactory";
	public static final List<IConfigElement> CONFIG_ELEMENTS = new ArrayList<IConfigElement>();

}

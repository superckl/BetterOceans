package me.superckl.betteroceans.common.reference;

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public abstract class NetworkData {

	public static final String PART_SELECT_CHANNEL_NAME = ModData.MOD_ID+":partselect";
	public static final String UPDATE_PARTS_CHANNEL_NAME = ModData.MOD_ID+":updateparts";
	public static final String CRAFT_PRESS_CHANNEL_NAME = ModData.MOD_ID+":presscraft";

	public static final SimpleNetworkWrapper PART_SELECT_CHANNEL = null;
	public static final SimpleNetworkWrapper UPDATE_PARTS_CHANNEL = null;
	public static final SimpleNetworkWrapper CRAFT_PRESS_CHANNEL = null;

}

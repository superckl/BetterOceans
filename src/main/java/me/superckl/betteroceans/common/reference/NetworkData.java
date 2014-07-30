package me.superckl.betteroceans.common.reference;

import me.superckl.betteroceans.common.entity.EntityBOBoat;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class NetworkData {

	//TODO change to parts

	public static final String BOAT_SELECT_CHANNEL_NAME = ModData.MOD_ID+":boatselect";
	public static final SimpleNetworkWrapper BOAT_SELECT_CHANNEL = null;


	public static final int BOAT_ID = 0;


	public static Class<? extends EntityBOBoat> getEntity(final int id){
		if(id == NetworkData.BOAT_ID)
			return EntityBOBoat.class;
		return null;
	}
}

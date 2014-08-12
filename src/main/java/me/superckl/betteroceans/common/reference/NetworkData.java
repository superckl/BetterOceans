package me.superckl.betteroceans.common.reference;

import me.superckl.betteroceans.common.entity.EntityBOBoat;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public abstract class NetworkData {

	//TODO change to parts

	public static final String PART_SELECT_CHANNEL_NAME = ModData.MOD_ID+":partselect";
	public static final String UPDATE_PARTS_CHANNEL_NAME = ModData.MOD_ID+"updateparts";
	public static final SimpleNetworkWrapper PART_SELECT_CHANNEL = null;
	public static final SimpleNetworkWrapper UPDATE_PARTS_CHANNEL = null;


	public static final int BOAT_ID = 0;


	public static Class<? extends EntityBOBoat> getEntity(final int id){
		if(id == NetworkData.BOAT_ID)
			return EntityBOBoat.class;
		return null;
	}
}

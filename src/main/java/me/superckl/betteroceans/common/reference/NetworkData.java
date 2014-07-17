package me.superckl.betteroceans.common.reference;

import me.superckl.betteroceans.common.entity.EntityWoodenBoat;
import me.superckl.betteroceans.common.entity.IEntityBoat;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class NetworkData {

	public static final String BOAT_SELECT_CHANNEL_NAME = ModData.MOD_ID+":boatselect";
	public static final SimpleNetworkWrapper BOAT_SELECT_CHANNEL = null;


	public static final int WOODEN_BOAT_ID = 0;


	public static Class<? extends IEntityBoat> getEntity(final int id){
		if(id == NetworkData.WOODEN_BOAT_ID)
			return EntityWoodenBoat.class;
		return null;
	}
}

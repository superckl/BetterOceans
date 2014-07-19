package me.superckl.betteroceans.common.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ServerTickHandler {

	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onServerTick(final TickEvent.ServerTickEvent e){
		//TODO calculate stamina drain
	}

}

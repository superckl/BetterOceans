package me.superckl.betteroceans.common.handler;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ServerTickHandler {

	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent e){
		WorldServer[] worlds = MinecraftServer.getServer().worldServers;
	}
	
}

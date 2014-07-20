package me.superckl.betteroceans.common.handler;

import java.util.List;

import me.superckl.betteroceans.common.utility.BlockHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ServerTickHandler {

	private int tickDelay = 20;

	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onServerTick(final TickEvent.ServerTickEvent e){
		if(this.tickDelay > 0){
			this.tickDelay--;
			return;
		}else
			this.tickDelay = 20;
		final WorldServer[] worlds = MinecraftServer.getServer().worldServers;
		for(final WorldServer world:worlds){
			final List<EntityPlayer> players = world.playerEntities;
			for(final EntityPlayer player:players){
				if(player instanceof EntityPlayerMP == false)
					continue;
				if(BlockHelper.isSwimming(player)){
					//We need to drain stamina
				}else{
					//Recharge stamina
				}
			}
		}
	}

}

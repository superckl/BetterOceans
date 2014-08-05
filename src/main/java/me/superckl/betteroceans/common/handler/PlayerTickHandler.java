package me.superckl.betteroceans.common.handler;

import me.superckl.betteroceans.common.entity.StaminaExtendedProperties;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.Side;

public class PlayerTickHandler {

	private boolean wasExhausted;

	private int tickDelay = 8;

	@SubscribeEvent
	public void onPlayerTick(final PlayerTickEvent e){
		if(e.side == Side.SERVER && e.phase == Phase.END){
			if(this.tickDelay > 0){
				this.tickDelay--;
				return;
			}else
				this.tickDelay = 8;
			((StaminaExtendedProperties)e.player.getExtendedProperties("swimStamina")).playerTick();
		}else if(e.phase == Phase.START && e.side == Side.CLIENT)
			if(((StaminaExtendedProperties)e.player.getExtendedProperties("swimStamina")).isExhausted()){
				e.player.motionX*=.25;
				e.player.motionZ*=.25;
				e.player.motionY-=0.03999999910593033D; //Jump boost factor in water
				//e.player.capabilities.setFlySpeed(0F);
				//e.player.capabilities.setPlayerWalkSpeed(0F);
				this.wasExhausted = true;
			}else if(this.wasExhausted)
				this.wasExhausted = false;
	}

}

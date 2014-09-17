package me.superckl.betteroceans.common.handler;

import me.superckl.betteroceans.common.entity.prop.StaminaExtendedProperties;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.utility.BlockHelper;
import net.minecraft.item.ItemStack;
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
		}
		if(e.phase == Phase.START && !e.player.capabilities.isFlying/* && e.side == Side.CLIENT*/){
			final ItemStack armor = e.player.getCurrentArmor(2);
			final boolean lifeJacket = armor != null && armor.getItem() == ModItems.lifeJacket;
			if(lifeJacket && e.player.isInWater() && BlockHelper.getFluidDepth(e.player.worldObj, (int) e.player.posX, (int) e.player.posY, (int) e.player.posZ) > 1)
				e.player.motionY += 0.05D;
			if(((StaminaExtendedProperties)e.player.getExtendedProperties("swimStamina")).isExhausted()){
				e.player.motionX *= .25;
				e.player.motionZ *= .25;
				if(!lifeJacket)
					e.player.motionY -= 0.03999999910593033D; //Jump boost factor in water
				this.wasExhausted = true;
			}else if(this.wasExhausted)
				this.wasExhausted = false;
		}
	}

}

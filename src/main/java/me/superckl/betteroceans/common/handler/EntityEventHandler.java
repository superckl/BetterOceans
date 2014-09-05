package me.superckl.betteroceans.common.handler;

import java.util.HashMap;
import java.util.Map;

import me.superckl.betteroceans.common.entity.prop.StaminaExtendedProperties;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityEventHandler {

	private final Map<EntityLivingBase, Float> fallingEntities = new HashMap<EntityLivingBase, Float>();

	@SubscribeEvent
	public void onEntityConstruction(final EntityConstructing e){
		if(e.entity instanceof EntityPlayer)
			e.entity.registerExtendedProperties("swimStamina", new StaminaExtendedProperties());
	}

	@SubscribeEvent
	public void onLivingFall(final LivingUpdateEvent e){
		if(e.entityLiving.worldObj.isRemote)
			return;
		if(!e.entityLiving.isInWater() && e.entityLiving.fallDistance >= 10F)
			this.fallingEntities.put(e.entityLiving, e.entityLiving.fallDistance);
		else if(this.fallingEntities.containsKey(e.entityLiving))
			if(e.entityLiving.isInWater()){
				final float distance = this.fallingEntities.remove(e.entityLiving).floatValue();
				e.entityLiving.attackEntityFrom(DamageSource.fall, (float) Math.floor(distance/8F));
			}else if(e.entityLiving.fallDistance < 10F)
				this.fallingEntities.remove(e.entityLiving); //Cleanup to prevent memory leaks
	}

}

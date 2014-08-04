package me.superckl.betteroceans.common.entity;

import lombok.Getter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class StaminaExtendedProperties implements IExtendedEntityProperties{

	@Getter
	private EntityPlayer player;

	@Override
	public void saveNBTData(final NBTTagCompound compound) {
		compound.setInteger("swimStamina", this.getStamina());
	}

	@Override
	public void loadNBTData(final NBTTagCompound compound) {
		this.setStamina(compound.getInteger("swimStamina"));
	}

	@Override
	public void init(final Entity entity, final World world) {
		if(entity instanceof EntityPlayer == false)
			return;
		this.player = (EntityPlayer) entity;
		this.player.getDataWatcher().addObject(24, new Integer(100));
	}

	public void serverTick(){

	}

	public void setStamina(final int stamina){
		this.player.getDataWatcher().updateObject(24, new Integer(stamina));
	}

	public int getStamina(){
		return this.player.getDataWatcher().getWatchableObjectInt(24);
	}

}

package me.superckl.betteroceans.common.entity;

import lombok.Getter;
import me.superckl.betteroceans.common.utility.LogHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class StaminaExtendedProperties implements IExtendedEntityProperties{

	@Getter
	private EntityPlayer player;

	private int regenDelay = 4;

	@Override
	public void saveNBTData(final NBTTagCompound compound) {
		LogHelper.info("saving "+this.getStamina());
		compound.setInteger("swimStamina", this.getStamina());
	}

	@Override
	public void loadNBTData(final NBTTagCompound compound) {
		LogHelper.info(compound.hasKey("swimStamina"));
		LogHelper.info("loaded "+compound.getInteger("swimStamina"));
		this.setStamina(compound.getInteger("swimStamina"));
	}

	@Override
	public void init(final Entity entity, final World world) {
		if(entity instanceof EntityPlayer == false)
			return;
		this.player = (EntityPlayer) entity;
		this.player.getDataWatcher().addObject(27, new Integer(100));
		this.player.getDataWatcher().addObject(28, new Byte((byte) 0));
	}

	public void playerTick(){
		if(this.player.isInWater() && this.player.motionY != -0.02D)
			this.swimTick();
		else
			this.nonSwimTick();
	}

	/**
	 * @return Whether or not the play can continue swimming
	 */
	public boolean swimTick(){
		this.regenDelay = 4;
		if(this.isExhausted())
			return false;
		int stamina = this.getStamina()-2;
		stamina = Math.max(stamina, 0);
		this.setStamina(stamina);
		if(stamina == 0){
			this.setExhausted(true);
			this.regenDelay = 6;
			return false;
		}
		return true;
	}

	public void nonSwimTick(){
		if(this.regenDelay > 0){
			this.regenDelay--;
			return;
		}else if(this.isExhausted())
			this.setExhausted(false);
		int stamina = this.getStamina();
		if(stamina >= 100)
			return;
		stamina = Math.min(100, stamina+1);
		this.setStamina(stamina);
	}

	public void setStamina(final int stamina){
		this.player.getDataWatcher().updateObject(27, new Integer(stamina));
	}

	public int getStamina(){
		return this.player.getDataWatcher().getWatchableObjectInt(27);
	}

	public void setExhausted(final boolean exhausted){
		this.player.getDataWatcher().updateObject(28, new Byte((byte) (exhausted ? 1:0)));
	}

	public boolean isExhausted(){
		return this.player.getDataWatcher().getWatchableObjectByte(28) == 1;
	}

}

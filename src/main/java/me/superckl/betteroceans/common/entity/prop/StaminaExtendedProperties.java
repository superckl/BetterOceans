package me.superckl.betteroceans.common.entity.prop;

import lombok.Getter;
import me.superckl.betteroceans.common.reference.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class StaminaExtendedProperties implements IExtendedEntityProperties{

	@Getter
	private EntityPlayer player;

	private int regenDelay = 4;

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
		if(!(entity instanceof EntityPlayer))
			return;
		this.player = (EntityPlayer) entity;
		this.player.getDataWatcher().addObject(27, new Integer(100));
		this.player.getDataWatcher().addObject(28, new Byte((byte) 0));
	}

	public void playerTick(){
		//TODO figure out why no drain, it should be that way, but it's not intentional :/
		final ItemStack armor = this.player.getCurrentArmor(2);
		final boolean lifeJacket = armor != null && armor.getItem() == ModItems.lifeJacket;
		final boolean shouldSwim = this.player.isInWater() && (this.player.motionY != -0.02D || lifeJacket);
		if(!this.player.capabilities.isCreativeMode && this.player.ridingEntity == null && shouldSwim)
			this.swimTick(lifeJacket);
		else
			this.nonSwimTick();
	}

	/**
	 * @return Whether or not the player can continue swimming
	 */
	public boolean swimTick(final boolean lifeJacket){
		this.regenDelay = 4;
		if(this.isExhausted() || lifeJacket)
			return lifeJacket;
		int stamina = this.getStamina()- (lifeJacket ? 1:2);
		stamina = Math.max(stamina, 0);
		this.setStamina(stamina);
		if(stamina == 0){
			this.setExhausted(true);
			this.regenDelay = 4;
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

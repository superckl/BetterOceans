package me.superckl.betteroceans.common.entity;

import lombok.Getter;
import me.superckl.betteroceans.common.utility.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityFishBase extends EntityWaterMob{

	@Getter
	private final boolean canLiveInSaltWater;

	public EntityFishBase(final World world, final boolean canLiveInSaltWater) {
		super(world);
		this.canLiveInSaltWater = canLiveInSaltWater;
		//this.tasks.addTask(2, new EntityAIHeightRestrictedWander(this, 0.8D, 25));
		this.tasks.addTask(1, new EntityAISwimming(this));
		//this.tasks.addTask(3, new EntityAITempt(this, 0.85D, ModItems.itemSeaweed, false));
		//this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityPlayer.class, 5, 0.81D, 1.2D)); Will be used in subclasses
	}

	@Override
	public boolean getCanSpawnHere() {
		final Block block = this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ);
		return this.canLiveInSaltWater ? BlockHelper.isWaterSource(block):block == Blocks.water;
	}

	@Override
	public boolean canBeSteered() {
		return false;
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

}

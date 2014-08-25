package me.superckl.betteroceans.common.entity.ai;

import java.util.Random;

import me.superckl.betteroceans.common.utility.BlockHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

/**
 * Most of this class is from JoshieJack's Mariculture mod. Credit to him.
 */
public class EntityAISwimRandomly extends EntityAIBase {
	Random rand = new Random();
	private final EntityLiving theEntity;
	private int courseChangeCooldown;
	private double waypointX;
	private double waypointY;
	private double waypointZ;

	public EntityAISwimRandomly(final EntityLiving entity) {
		this.theEntity = entity;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		return this.theEntity.isInWater() || this.theEntity.handleLavaMovement();
	}

	@Override
	public void updateTask() {
		final double d0 = this.waypointX - this.theEntity.posX;
		final double d1 = this.waypointY - this.theEntity.posY;
		final double d2 = this.waypointZ - this.theEntity.posZ;
		double d3 = d0 * d0 + d1 * d1 + d2 * d2;

		if (d3 < 1.0D || d3 > 3600.0D) {
			this.waypointX = this.theEntity.posX + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
			this.waypointY = this.theEntity.posY + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
			this.waypointZ = this.theEntity.posZ + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
		}

		if (this.courseChangeCooldown-- <= 0) {
			this.courseChangeCooldown += this.rand.nextInt(5) + 2;
			d3 = MathHelper.sqrt_double(d3);

			if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3)) {
				this.theEntity.motionX += d0 / d3 * 0.08D;
				this.theEntity.motionY += d1 / d3 * 0.08D;
				this.theEntity.motionZ += d2 / d3 * 0.08D;
			} else {
				this.waypointX = this.theEntity.posX;
				this.waypointY = this.theEntity.posY;
				this.waypointZ = this.theEntity.posZ;
			}
		}

		this.theEntity.renderYawOffset = this.theEntity.rotationYaw = -((float)Math.atan2(this.theEntity.motionX, this.theEntity.motionZ)) * 180.0F / (float)Math.PI;
	}

	private boolean isCourseTraversable(final double x, final double y, final double z, final double par7) {
		if(!BlockHelper.isWaterSourceAt(this.theEntity.worldObj, (int)x, (int)y, (int)z))
			return false;

		final double d4 = (this.waypointX - this.theEntity.posX) / par7;
		final double d5 = (this.waypointY - this.theEntity.posY) / par7;
		final double d6 = (this.waypointZ - this.theEntity.posZ) / par7;
		final AxisAlignedBB axisalignedbb = this.theEntity.boundingBox.copy();

		for (int i = 1; i < par7; ++i) {
			axisalignedbb.offset(d4, d5, d6);

			if (!this.theEntity.worldObj.getCollidingBoundingBoxes(this.theEntity, axisalignedbb).isEmpty())
				return false;
		}

		return true;
	}
}

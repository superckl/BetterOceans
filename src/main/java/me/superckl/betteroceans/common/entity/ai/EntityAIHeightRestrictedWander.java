package me.superckl.betteroceans.common.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class EntityAIHeightRestrictedWander extends EntityAIBase{

	private final EntityCreature entity;
	private double xPosition;
	private double yPosition;
	private double zPosition;
	private final double speed;
	private final double maxY;

	public EntityAIHeightRestrictedWander(final EntityCreature entity, final double speed, final double maxY)
	{
		this.entity = entity;
		this.speed = speed;
		this.maxY = maxY;
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		if (this.entity.getAge() >= 100)
			return false;
		else if (this.entity.getRNG().nextInt(120) != 0)
			return false;
		else
		{
			final Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);

			if (vec3 == null)
				return false;
			else
			{
				this.xPosition = vec3.xCoord;
				this.yPosition = Math.min(vec3.yCoord, this.maxY);
				this.zPosition = vec3.zCoord;
				return true;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting()
	{
		return !this.entity.getNavigator().noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
	}

}

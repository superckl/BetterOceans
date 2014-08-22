package me.superckl.betteroceans.common.entity;

import me.superckl.betteroceans.common.reference.ModItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class EntityFishBasic extends EntityFishBase{

	public static final int WANDER_DIST = 30;
	private boolean isMoving;
	private boolean prevIsMoving;
	private boolean isGoingLeft;

	public EntityFishBasic(final World world) {
		super(world, true);
		this.tasks.addTask(2, new EntityAIWander(this, 0.8D));
		this.tasks.addTask(3, new EntityAITempt(this, 0.85D, ModItems.itemSeaweed, false));
		this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityPlayer.class, 5, 0.81D, 1.2D));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.getDataWatcher().addObject(17, new Float(0));
		this.setHomeArea((int) this.posX, (int) this.posY, (int) this.posZ, EntityFishBasic.WANDER_DIST);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5.0D);
	}

	@Override
	public void onUpdate(){
		super.onUpdate();
		if(this.motionX != 0 || this.motionY != 0 || this.motionZ != 0)
			this.isMoving = true;
		else
			this.isMoving = false;
		this.updateSwimState();
	}

	private void updateSwimState(){
		if(!this.isMoving && this.getSwimState() != 0F){
			float state = this.getSwimState();
			this.setSwimState(state -= 1F*Math.signum(state));
			if(Math.abs(state) < 1F)
				this.setSwimState(0F);
			this.prevIsMoving = this.isMoving;
			return;
		}else if(!this.isMoving)
			return;
		boolean goLeft;
		if(!this.prevIsMoving)
			goLeft = this.rand.nextBoolean();
		else
			goLeft = this.isGoingLeft;
		this.prevIsMoving = this.isMoving;
		float state = this.getSwimState();
		state += goLeft ? -1F:1F;
		if(Math.abs(state) >= 30F)
			goLeft = !goLeft;
		this.setSwimState(state);
		this.isGoingLeft = goLeft;
	}

	public float getSwimState(){
		return this.dataWatcher.getWatchableObjectFloat(17);
	}

	public void setSwimState(final float state){
		this.dataWatcher.updateObject(17, new Float(state));
	}

	@Override
	public void writeToNBT(final NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setFloat("swimState", this.getSwimState());
		final ChunkCoordinates cc = this.getHomePosition();
		compound.setIntArray("spawn", new int[] {cc.posX, cc.posY, cc.posZ});
	}

	@Override
	public void readFromNBT(final NBTTagCompound compound){
		super.readFromNBT(compound);
		final int[] coords = compound.getIntArray("spawn");
		this.setHomeArea(coords[0], coords[1], coords[2], EntityFishBasic.WANDER_DIST);
	}

}

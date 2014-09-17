package me.superckl.betteroceans.common.entity;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import me.superckl.betteroceans.common.event.ModularBoatEvent;
import me.superckl.betteroceans.common.nets.IItemNet;
import me.superckl.betteroceans.common.nets.INet;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.parts.BoatPart.Type;
import me.superckl.betteroceans.common.reference.NetworkData;
import me.superckl.betteroceans.common.utility.BoatHelper;
import me.superckl.betteroceans.common.utility.ConstructorWrapper;
import me.superckl.betteroceans.network.MessagePartUpdate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Note: Most of the motion code is from the EntityBoat class, credit to Mojang for that.
 */
public class EntityBOBoat extends EntityModularBoat implements IEntityAdditionalSpawnData{

	@Getter
	private INet attachedNet;
	@Getter
	private final List<BoatPart> boatParts = new ArrayList<BoatPart>();
	@Setter
	@Getter
	private boolean renderWithRotation;
	public int renderYawOffset; //We have to do this manually apparently...

	private boolean isBoatEmpty;
	private double speedMultiplier;
	private int boatPosRotationIncrements;
	private double boatX;
	private double boatY;
	private double boatZ;
	private double boatYaw;
	private double boatPitch;
	public float length;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;

	public EntityBOBoat(final World world, final double x, final double y, final double z) {
		this(world);
		this.setPosition(x, y + this.yOffset, z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	public EntityBOBoat(final World world){
		super(world);
		this.isBoatEmpty = true;
		this.speedMultiplier = 0.02D;
		this.preventEntitySpawning = true;
		this.setSize(1.4F, .6F);
		this.yOffset = this.height / 1.86F;
	}

	/*public void recomputeSize(){
		this.setSize(BoatHelper.computeWidth(this), BoatHelper.computeLength(this), BoatHelper.computeHeight(this));
	}*/

	/*@Override
	protected void setSize(float p_70105_1_, float p_70105_2_) {
		this.recomputeSize();
	}

	public void setSize(float width, float length, float height){
		super.setSize(width, height);
		this.length = length;
		this.boundingBox.maxZ = this.boundingBox.minZ + (double)length;
	}*/

	/*	@Override
    public void setPosition(double p_70107_1_, double p_70107_3_, double p_70107_5_)
    {
        this.posX = p_70107_1_;
        this.posY = p_70107_3_;
        this.posZ = p_70107_5_;
        float f = this.length / 2.0F;
        float f2 = this.width / 2.0F;
        float f1 = this.height;
        this.boundingBox.setBounds(p_70107_1_ - (double)f, p_70107_3_ - (double)this.yOffset + (double)this.ySize, p_70107_5_ - (double)f2, p_70107_1_ + (double)f, p_70107_3_ - (double)this.yOffset + (double)this.ySize + (double)f1, p_70107_5_ + (double)f2);
    }*/

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObject(17, new Integer(0));
		this.dataWatcher.addObject(18, new Integer(1));
		this.dataWatcher.addObject(19, new Float(0.0F));
		this.dataWatcher.addObject(20, new Float(0F));
	}



	@Override
	public AxisAlignedBB getCollisionBox(final Entity entity)
	{
		return entity.boundingBox;
	}

	@Override
	public AxisAlignedBB getBoundingBox()
	{
		return this.boundingBox;
	}

	@Override
	public boolean canBePushed()
	{
		return true;
	}

	@Override
	public double getMountedYOffset()
	{
		return this.height * 0.0D - 0.30000001192092896D;
	}

	@Override
	public boolean attackEntityFrom(final DamageSource source, final float damage)
	{
		if (this.isEntityInvulnerable())
			return false;
		else if (!this.worldObj.isRemote && !this.isDead)
		{
			this.setForwardDirection(-this.getForwardDirection());
			this.setTimeSinceHit(10);
			this.setDamageTaken(this.getDamageTaken() + damage * 10.0F);
			this.setBeenAttacked();
			final boolean flag = source.getEntity() instanceof EntityPlayer && ((EntityPlayer)source.getEntity()).capabilities.isCreativeMode;

			if (flag || this.getDamageTaken() > 40.0F*Math.max(BoatHelper.compoundIntegrityFactors(this), 0.8F))
			{
				if (this.riddenByEntity != null)
					this.riddenByEntity.mountEntity(this);

				if (!flag){
					this.dropAsParts();
					this.boatParts.clear();
				}

				this.setDead();
			}

			return true;
		} else
			return true;
	}

	@Override
	public boolean interactFirst(final EntityPlayer player){
		if(player.isSneaking()){
			if(this.isComplete()){
				//TODO this should be done in the item
				if(this.hasNetAttatched()){
					player.inventory.addItemStackToInventory(this.attachedNet.toItemStack());
					this.attachNet(null);
				}

				if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemNet){
					final IItemNet itemNet = (IItemNet) player.getHeldItem().getItem();
					if(!itemNet.canAttachTo(this))
						return false;
					this.attachNet(itemNet.toNet(player.getHeldItem().getItemDamage()));
					player.getHeldItem().stackSize--;
					return true;
				}
			}
		}else if(this.isComplete())
			if(this.riddenByEntity == null){
				if (!this.worldObj.isRemote)
					player.mountEntity(this);
				return true;
			}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void performHurtAnimation()
	{
		this.setForwardDirection(-this.getForwardDirection());
		this.setTimeSinceHit(10);
		this.setDamageTaken(this.getDamageTaken() * 11.0F);
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	@Override
	public void updateRiderPosition()
	{
		if (this.riddenByEntity != null)
		{
			final double d0 = Math.cos(this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			final double d1 = Math.sin(this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			this.riddenByEntity.setPosition(this.posX + d0, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + d1);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(final double x, final double y, final double z, final float yaw, final float pitch, final int rotationInc)
	{
		if (this.isBoatEmpty)
			this.boatPosRotationIncrements = rotationInc + 5;
		else
		{
			final double d3 = x - this.posX;
			final double d4 = y - this.posY;
			final double d5 = z - this.posZ;
			final double d6 = d3 * d3 + d4 * d4 + d5 * d5;

			if (d6 <= 1.0D)
				return;

			this.boatPosRotationIncrements = 3;
		}

		this.boatX = x;
		this.boatY = y;
		this.boatZ = z;
		this.boatYaw = yaw;
		this.boatPitch = pitch;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(final double x, final double y, final double z)
	{
		this.velocityX = this.motionX = x;
		this.velocityY = this.motionY = y;
		this.velocityZ = this.motionZ = z;
	}

	private boolean trySink(){
		return this.isComplete() && this.riddenByEntity != null && this.rand.nextDouble() < 0.00012D*BoatHelper.compoundSinkModifiers(this);
	}

	@Override
	public void onUpdate()
	{
		if(this.hasNetAttatched())
			this.attachedNet.preAttatchedUpdate();
		super.onUpdate();

		if(this.isSinking() || this.trySink()){
			final float depth = this.getSinkDepth();
			this.setSinkDepth(Math.abs(depth) > this.height+0.3F ? this.getSinkDepth()+.05F:this.getSinkDepth()+0.0005F);
			this.yOffset -= Math.abs(depth) > this.height+0.3F ? .05F:0.0005F;
			if(this.yOffset < -7F){
				this.setDead();
				if(this.riddenByEntity != null)
					this.riddenByEntity.mountEntity(null);
			}
		}
		if (this.getTimeSinceHit() > 0)
			this.setTimeSinceHit(this.getTimeSinceHit() - 1);

		if (this.getDamageTaken() > 0.0F)
			this.setDamageTaken(this.getDamageTaken() - 1.0F);

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		final byte b0 = 5;
		double d0 = 0.0D;

		for (int i = 0; i < b0; ++i)
		{
			final double d1 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (i + 0) / b0 - 0.125D;
			final double d3 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (i + 1) / b0 - 0.125D;
			final AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(this.boundingBox.minX, d1, this.boundingBox.minZ, this.boundingBox.maxX, d3, this.boundingBox.maxZ);

			if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water))
				d0 += 1.0D / b0;
		}

		final double d10 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		double d2;
		double d4;
		int j;

		if (d10 > 0.26249999999999996D)
		{
			d2 = Math.cos(this.rotationYaw * Math.PI / 180.0D);
			d4 = Math.sin(this.rotationYaw * Math.PI / 180.0D);

			for (j = 0; j < 1.0D + d10 * 60.0D; ++j)
			{
				final double d5 = this.rand.nextFloat() * 2.0F - 1.0F;
				final double d6 = (this.rand.nextInt(2) * 2 - 1) * 0.7D;
				double d8;
				double d9;

				if (this.rand.nextBoolean())
				{
					d8 = this.posX - d2 * d5 * 0.8D + d4 * d6;
					d9 = this.posZ - d4 * d5 * 0.8D - d2 * d6;
					this.worldObj.spawnParticle("splash", d8, this.posY - 0.125D, d9, this.motionX, this.motionY, this.motionZ);
				}
				else
				{
					d8 = this.posX + d2 + d4 * d5 * 0.7D;
					d9 = this.posZ + d4 - d2 * d5 * 0.7D;
					this.worldObj.spawnParticle("splash", d8, this.posY - 0.125D, d9, this.motionX, this.motionY, this.motionZ);
				}
			}
		}

		double d11;
		double d12;

		if (this.worldObj.isRemote && this.isBoatEmpty)
		{
			if (this.boatPosRotationIncrements > 0)
			{
				d2 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
				d4 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
				d11 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
				d12 = MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw);
				this.rotationYaw = (float)(this.rotationYaw + d12 / this.boatPosRotationIncrements);
				this.rotationPitch = (float)(this.rotationPitch + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
				--this.boatPosRotationIncrements;
				this.setPosition(d2, d4, d11);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			}
			else
			{
				d2 = this.posX + this.motionX;
				d4 = this.posY + this.motionY;
				d11 = this.posZ + this.motionZ;
				this.setPosition(d2, d4, d11);

				if (this.onGround)
				{
					this.motionX *= 0.0D;
					this.motionY *= 0.0D;
					this.motionZ *= 0.0D;
				}
				//final double modifier = BoatHelper.compoundSpeedModifiers(this);
				this.motionX *= /*modifier**/Math.max(0F, 1F-this.getSinkDepth());
				this.motionY *= this.isSinking() ? 0D:/*modifier**/Math.max(0F, 1F-this.getSinkDepth());
				this.motionZ *= /*modifier**/Math.max(0F, 1F-this.getSinkDepth());
			}
		}
		else
		{
			if(!this.isSinking())
				if (d0 < 1.0D)
				{
					d2 = d0 * 2.0D - 1.0D;
					this.motionY += 0.03999999910593033D * d2;
				}
				else
				{
					if (this.motionY < 0.0D)
						this.motionY /= 2.0D;

					this.motionY += 0.007000000216066837D;
				}
			if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase)
			{
				final EntityLivingBase entitylivingbase = (EntityLivingBase)this.riddenByEntity;
				final float f = this.riddenByEntity.rotationYaw + -entitylivingbase.moveStrafing * 90.0F;
				double turnM = BoatHelper.compoundTurnModifiers(this);
				turnM = -1; //TODO fix boat turning. We're going to use vanilla mechanics for now
				if(turnM < 0){
					this.motionX += -Math.sin(f * (float)Math.PI / 180.0F) * this.speedMultiplier * entitylivingbase.moveForward * 0.05000000074505806D;
					this.motionZ += Math.cos(f * (float)Math.PI / 180.0F) * this.speedMultiplier * entitylivingbase.moveForward * 0.05000000074505806D;
				}else{
					//this was a bitch to figure out, currently broken
					final double radianF = Math.toRadians(f);
					final double radianB = Math.toRadians(this.rotationYaw+90F);
					double xDir = Math.sin(radianF);
					final double boatxDir = Math.sin(radianB);
					xDir-=Math.min((xDir-boatxDir)*(.6D/turnM), xDir-boatxDir);
					this.motionX += -xDir * this.speedMultiplier * entitylivingbase.moveForward * 0.04000000074505806D;


					double zDir = Math.cos(radianF);
					final double boatzDir = Math.cos(radianB);
					zDir-=Math.min((zDir-boatzDir)*(.6D/turnM), zDir-boatzDir);
					this.motionZ += zDir * this.speedMultiplier * entitylivingbase.moveForward * 0.04000000074505806D;
				}
			}

			d2 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			final double maxSpeed = .35D*BoatHelper.compoundSpeedModifiers(this);
			if (d2 > maxSpeed)
			{
				d4 = maxSpeed / d2;
				this.motionX *= d4;
				this.motionZ *= d4;
				d2 = maxSpeed;
			}

			if (d2 > d10 && this.speedMultiplier < 0.35D)
			{
				this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;

				if (this.speedMultiplier > 0.35D)
					this.speedMultiplier = 0.35D;
			}
			else
			{
				this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;

				if (this.speedMultiplier < 0.07D)
					this.speedMultiplier = 0.07D;
			}

			int l;

			for (l = 0; l < 4; ++l)
			{
				final int i1 = MathHelper.floor_double(this.posX + (l % 2 - 0.5D) * 0.8D);
				j = MathHelper.floor_double(this.posZ + (l / 2 - 0.5D) * 0.8D);

				for (int j1 = 0; j1 < 2; ++j1)
				{
					final int k = MathHelper.floor_double(this.posY) + j1;
					final Block block = this.worldObj.getBlock(i1, k, j);

					if (block == Blocks.snow_layer)
					{
						this.worldObj.setBlockToAir(i1, k, j);
						this.isCollidedHorizontally = false;
					}
					else if (block == Blocks.waterlily)
					{
						this.worldObj.func_147480_a(i1, k, j, true);
						this.isCollidedHorizontally = false;
					}
				}
			}

			if (this.onGround)
			{
				this.motionX *= 0.0D;
				this.motionY *= 0.0D;
				this.motionZ *= 0.0D;
			}

			//final double modifier = BoatHelper.compoundSpeedModifiers(this);
			this.motionX *= /*modifier**/Math.max(0F, 1F-this.getSinkDepth());
			this.motionY *= this.isSinking() ? 0D:/*modifier**/Math.max(0F, 1F-this.getSinkDepth());
			this.motionZ *= /*modifier**/Math.max(0F, 1F-this.getSinkDepth());

			this.moveEntity(this.motionX, this.motionY, this.motionZ);

			if (this.isCollidedHorizontally && d10 > 0.2D*BoatHelper.compoundIntegrityFactors(this))
			{
				if (!this.worldObj.isRemote && !this.isDead)
				{
					this.setDead();
					//drop the parts, maybe
					this.dropAsParts();
					this.boatParts.clear();
				}
			}
			else
			{
				this.motionX *= 0.9900000095367432D;
				this.motionY *= 0.949999988079071D;
				this.motionZ *= 0.9900000095367432D;
			}

			this.rotationPitch = 0.0F;
			d4 = this.rotationYaw;
			d11 = this.prevPosX - this.posX;
			d12 = this.prevPosZ - this.posZ;

			if (d11 * d11 + d12 * d12 > 0.001D)
				d4 = (float)(Math.atan2(d12, d11) * 180.0D / Math.PI);

			double d7 = MathHelper.wrapAngleTo180_double(d4 - this.rotationYaw);

			if (d7 > 20.0D)
				d7 = 20.0D;

			if (d7 < -20.0D)
				d7 = -20.0D;

			this.rotationYaw = (float)(this.rotationYaw + d7);
			this.setRotation(this.rotationYaw, this.rotationPitch);

			if (!this.worldObj.isRemote)
			{
				final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

				if (list != null && !list.isEmpty())
					for (final Object aList : list) {
						final Entity entity = (Entity) aList;

						if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityBoat)
							entity.applyEntityCollision(this);
					}

				if (this.riddenByEntity != null && this.riddenByEntity.isDead)
					this.riddenByEntity = null;
			}
		}
		if(this.hasNetAttatched())
			this.attachedNet.postAttahcedUpdate();
	}

	private void dropAsParts(){
		for(final BoatPart part:this.boatParts)
			if(part.shouldDrop(this.rand)){
				final ItemStack stack = part.getCraftingResult();
				stack.stackSize = 1;
				this.entityDropItem(stack, 0F);
			}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	@Override
	protected void updateFallState(final double distanceFallen, final boolean onGround)
	{
		final int i = MathHelper.floor_double(this.posX);
		final int j = MathHelper.floor_double(this.posY);
		final int k = MathHelper.floor_double(this.posZ);

		if (onGround)
		{
			if (this.fallDistance > 3.0F)
			{
				this.fall(this.fallDistance);

				if (!this.worldObj.isRemote && !this.isDead)
				{
					this.setDead();
					int l;

					for (l = 0; l < 3; ++l)
						this.func_145778_a(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);

					for (l = 0; l < 2; ++l)
						this.func_145778_a(Items.stick, 1, 0.0F);
				}

				this.fallDistance = 0.0F;
			}
		}
		else if (this.worldObj.getBlock(i, j - 1, k).getMaterial() != Material.water && distanceFallen < 0.0D)
			this.fallDistance = (float)(this.fallDistance - distanceFallen);
	}

	public void setDamageTaken(final float damage)
	{
		this.dataWatcher.updateObject(19, Float.valueOf(damage));
	}

	public float getDamageTaken()
	{
		return this.dataWatcher.getWatchableObjectFloat(19);
	}

	public void setTimeSinceHit(final int time)
	{
		this.dataWatcher.updateObject(17, Integer.valueOf(time));
	}

	public int getTimeSinceHit()
	{
		return this.dataWatcher.getWatchableObjectInt(17);
	}

	public void setForwardDirection(final int direction)
	{
		this.dataWatcher.updateObject(18, Integer.valueOf(direction));
	}

	public int getForwardDirection()
	{
		return this.dataWatcher.getWatchableObjectInt(18);
	}

	public boolean isSinking(){
		return this.dataWatcher.getWatchableObjectFloat(20) > 0F;
	}

	public void setSinkDepth(final float depth){
		this.dataWatcher.updateObject(20, new Float(depth));
	}

	public float getSinkDepth(){
		return this.dataWatcher.getWatchableObjectFloat(20);
	}

	@SideOnly(Side.CLIENT)
	public void setIsBoatEmpty(final boolean empty)
	{
		this.isBoatEmpty = empty;
	}

	@Override
	public void attachNet(final INet net) {
		this.attachedNet = net;
	}

	@Override
	public boolean hasNetAttatched() {
		return this.attachedNet != null;
	}

	@Override
	public boolean isComplete() {
		return BoatHelper.hasParts(this, Type.BOTTOM, Type.SIDE, Type.SIDE, Type.END, Type.END);
	}

	public BoatPart translateItemDamageToPart(final int damage){
		final Type type = BoatPart.deserialize(damage).getType();
		BoatPart part;
		if(type == Type.END || type == Type.SIDE){
			boolean flag = false;
			if(type == Type.SIDE){
				final boolean hasLeft = BoatHelper.hasSide(this, true);
				final boolean hasRight = BoatHelper.hasSide(this, false);
				if(!hasLeft)
					flag = true;
				else if(hasRight)
					return null; //Something went wrong here.
			}else{
				final boolean hasFront = BoatHelper.hasEnd(this, true);
				final boolean hasBack = BoatHelper.hasEnd(this, false);
				if(!hasFront)
					flag = true;
				else if(hasBack)
					return null; //Hrmm... Who dun it?
			}
			ConstructorWrapper<? extends BoatPart> cons = BoatPart.getParts().get(damage);
			cons = cons.clone();
			if(cons.getArguments() == null || cons.getArguments().length != 1)
				return null; //What??? How did that happen...
			cons.setArguments(flag);
			part = cons.newInstance();
		}else
			part = BoatPart.deserialize(damage);
		return part;
	}

	@Override
	public boolean addPart(final BoatPart part) {
		return this.addPart(part, true);
	}

	/**
	 * @param syncClient This should never be true on client worlds. The method, however, checks for remote worlds.
	 */
	public boolean addPart(final BoatPart part, final boolean syncClient){
		return this.addPart(part, syncClient, false);
	}

	/**
	 * @param syncClient This should never be true on client worlds. The method, however, checks for remote worlds before syncing.
	 */
	public boolean addPart(final BoatPart part, final boolean syncClient, final boolean skipCheck){
		if(part == null)
			return false;
		if (!skipCheck) {
			final Type[] types = new Type[part.getMaxNumberOnBoat()];
			Arrays.fill(types, part.getType());
			if (BoatHelper.hasParts(this, types))
				return false;
			if(part.useOverallComplexity()){
				if(part.getRequiredComplexity() > this.getOverallComplexity())
					return false;
			}else if(!BoatHelper.areRequirementsSatisfied(part, this))
				return false;
		}
		if(MinecraftForge.EVENT_BUS.post(new ModularBoatEvent.PartAdd(this, part))){
			this.boatParts.add(part);
			if(syncClient && !this.worldObj.isRemote)
				this.syncParts();
			return true;
		}
		return false;
	}

	@Override
	protected void readEntityFromNBT(final NBTTagCompound compound) {
		this.setSinkDepth(compound.getFloat("sinkDepth"));
		for(final int id:compound.getIntArray("parts"))
			this.boatParts.add(BoatPart.deserialize(id));
	}

	@Override
	protected void writeEntityToNBT(final NBTTagCompound compound) {
		compound.setFloat("sinkDepth", this.getSinkDepth());
		final int[] array = new int[this.boatParts.size()];
		for(int i = 0; i < this.boatParts.size(); i++)
			array[i] = this.boatParts.get(i).getPartID();
		compound.setIntArray("parts", array);
	}

	private void syncParts(){
		NetworkData.UPDATE_PARTS_CHANNEL.sendToDimension(new MessagePartUpdate(this), this.worldObj.provider.dimensionId);
	}

	@Override
	public void writeSpawnData(final ByteBuf buf) {
		buf.writeInt(this.boatParts.size());
		for(final BoatPart part:this.getBoatParts())
			BoatHelper.writePartToBuffer(part, buf);
	}

	@Override
	public void readSpawnData(final ByteBuf buf) {
		int parts = buf.readInt();
		while(parts > 0 && buf.readableBytes() > 0){
			this.boatParts.add(BoatHelper.readPartFromBuffer(buf));
			parts--;
		}

	}

}

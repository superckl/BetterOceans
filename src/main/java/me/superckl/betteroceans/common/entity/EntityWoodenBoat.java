package me.superckl.betteroceans.common.entity;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import me.superckl.betteroceans.common.nets.IItemNet;
import me.superckl.betteroceans.common.nets.INet;
import me.superckl.betteroceans.common.reference.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityWoodenBoat extends EntityBoat implements IEntityBoat, Rotatable{

	@Getter
	private INet attatchedNet;
	@Setter
	@Getter
	private boolean renderWithRotation;
	public int renderYawOffset; //We have to do this manually apparently...

	public Entity passenger;

	public EntityWoodenBoat(final World world, final double par2, final double par4,
			final double par6) {
		super(world, par2, par4, par6);
	}

	public EntityWoodenBoat(final World world){
		super(world);
	}

	@Override
	public boolean interactFirst(final EntityPlayer player){
		if(player.isSneaking()){
			if(this.hasNetAttatched()){
				player.inventory.addItemStackToInventory(this.attatchedNet.toItemStack());
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

		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != player)
		{
			if(this.passenger != null  && this.passenger instanceof EntityPlayer && this.passenger != player)
				return true;
			else{
				if (!this.worldObj.isRemote)
				{
					player.ridingEntity = this;
					this.passenger = player;
				}
				return true;
			}
		}
		else
		{
			if (!this.worldObj.isRemote)
				player.mountEntity(this);

			return true;
		}
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
	public void onUpdate(){
		if(this.hasNetAttatched())
			this.attatchedNet.preAttatchedUpdate();
		super.onUpdate();
		if(this.hasNetAttatched())
			this.attatchedNet.postAttahcedUpdate();
	}

	@Override
	public void attachNet(final INet net) {
		this.attatchedNet = net;
	}

	@Override
	public boolean hasNetAttatched() {
		return this.attatchedNet != null;
	}

	@Override
	public List<ItemStack> getCraftingIngredients() {
		return Arrays.asList(new ItemStack(Blocks.planks, 8));
	}

	@Override
	public Item getItem(){
		return ModItems.woodenBoat;
	}

	@Override
	public Entity asEntity() {
		return this;
	}

}

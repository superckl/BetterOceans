package me.superckl.betteroceans.entity;

import lombok.Getter;
import me.superckl.betteroceans.net.IItemNet;
import me.superckl.betteroceans.net.INet;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityWoodenBoat extends EntityBoat implements IEntityBoat{

	@Getter
	private INet attatchedNet;

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

		return super.interactFirst(player);
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

}

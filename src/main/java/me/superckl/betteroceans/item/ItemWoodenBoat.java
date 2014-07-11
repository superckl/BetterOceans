package me.superckl.betteroceans.item;

import java.util.List;

import me.superckl.betteroceans.entity.EntityWoodenBoat;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemWoodenBoat extends ItemBO{

	public ItemWoodenBoat() {
		super();
		this.setCreativeTab(CreativeTabs.tabTransport);
		this.setUnlocalizedName("woodenboat");
		this.setMaxStackSize(1);
	}

	@Override
	public void registerIcons(final IIconRegister register){
		this.itemIcon = register.registerIcon("boat");
	}

	//some magic from the ItemBoat class...
	@Override
	public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer player)
	{
		final float f = 1.0F;
		final float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
		final float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
		final double d0 = player.prevPosX + (player.posX - player.prevPosX) * f;
		final double d1 = player.prevPosY + (player.posY - player.prevPosY) * f + 1.62D - player.yOffset;
		final double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
		final Vec3 vec3 = world.getWorldVec3Pool().getVecFromPool(d0, d1, d2);
		final float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
		final float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
		final float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		final float f6 = MathHelper.sin(-f1 * 0.017453292F);
		final float f7 = f4 * f5;
		final float f8 = f3 * f5;
		final double d3 = 5.0D;
		final Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
		final MovingObjectPosition movingobjectposition = world.rayTraceBlocks(vec3, vec31, true);

		if (movingobjectposition == null)
			return itemStack;
		else
		{
			final Vec3 vec32 = player.getLook(f);
			boolean flag = false;
			final float f9 = 1.0F;
			final List list = world.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.addCoord(vec32.xCoord * d3, vec32.yCoord * d3, vec32.zCoord * d3).expand(f9, f9, f9));
			int i;

			for (i = 0; i < list.size(); ++i)
			{
				final Entity entity = (Entity)list.get(i);

				if (entity.canBeCollidedWith())
				{
					final float f10 = entity.getCollisionBorderSize();
					final AxisAlignedBB axisalignedbb = entity.boundingBox.expand(f10, f10, f10);

					if (axisalignedbb.isVecInside(vec3))
						flag = true;
				}
			}

			if (flag)
				return itemStack;
			else
			{
				if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
				{
					i = movingobjectposition.blockX;
					int j = movingobjectposition.blockY;
					final int k = movingobjectposition.blockZ;

					if (world.getBlock(i, j, k) == Blocks.snow_layer)
						--j;

					final EntityBoat entityboat = new EntityWoodenBoat(world, i + 0.5F, j + 1.0F, k + 0.5F);
					entityboat.rotationYaw = ((MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) - 1) * 90;

					if (!world.getCollidingBoundingBoxes(entityboat, entityboat.boundingBox.expand(-0.1D, -0.1D, -0.1D)).isEmpty())
						return itemStack;

					if (!world.isRemote)
						world.spawnEntityInWorld(entityboat);

					if (!player.capabilities.isCreativeMode)
						--itemStack.stackSize;
				}

				return itemStack;
			}
		}
	}

}

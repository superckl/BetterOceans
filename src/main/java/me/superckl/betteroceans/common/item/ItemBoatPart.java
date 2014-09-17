package me.superckl.betteroceans.common.item;

import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.parts.BoatPart.Type;
import me.superckl.betteroceans.common.parts.PartBottom;
import me.superckl.betteroceans.common.reference.BoatParts;
import me.superckl.betteroceans.common.reference.ModTabs;
import me.superckl.betteroceans.common.utility.StringHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBoatPart extends ItemBO{

	/*
	 * Damage Values (bit positions):
	 *
	 * Positions:
	 * 1 - Bottom
	 * 2 - Side
	 * 4 - End
	 *
	 * Materials:
	 * 32 - Wood
	 * 64 - iron
	 */

	public ItemBoatPart() {
		super();
		this.setHasSubtypes(true);
		this.setCreativeTab(ModTabs.tabItems);
		this.maxStackSize = 2;
		this.setUnlocalizedName("boatpart");
	}

	@SubscribeEvent
	public void onEntityInteract(final EntityInteractEvent e){
		if(e.target.worldObj.isRemote || !(e.target instanceof EntityBOBoat))
			return;
		if(e.entityPlayer.getHeldItem() == null || e.entityPlayer.getHeldItem().getItem() != this)
			return;
		final BoatPart part = ((EntityBOBoat)e.target).translateItemDamageToPart(e.entityPlayer.getHeldItem().getItemDamage());
		if(part != null && ((EntityBOBoat)e.target).addPart(part)){
			if(!e.entityPlayer.capabilities.isCreativeMode)
				e.entityPlayer.getHeldItem().stackSize--;
			e.setCanceled(true);
		}
	}

	@Override
	public void addInformation(final ItemStack stack, final EntityPlayer player, final List list, final boolean par4) {
		final BoatPart part = BoatPart.deserialize(stack.getItemDamage());
		if(part.getType() == Type.BOTTOM)
			list.add("Place in water to start building a boat.");
		if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			list.add(StringHelper.build("Hold ", EnumChatFormatting.YELLOW, EnumChatFormatting.ITALIC, "Shift ",EnumChatFormatting.RESET, EnumChatFormatting.GRAY, "for stats."));
		else{
			list.add(StringHelper.build("Speed: ", part.getSpeedModifier()));
			list.add(StringHelper.build("Integrity: ", part.getIntegrityFactor()));
			list.add(StringHelper.build("Complexity: ", part.getComplexity()));
			list.add(StringHelper.build("Required Complexity: ", part.getRequiredComplexity()));
			if(BetterOceans.getInstance().getConfig().isDebugTooltips()){
				list.add(StringHelper.build("Part ID: ", part.getPartID()));
				list.add(StringHelper.build("Class: ", part.getClass().getSimpleName()));
			}
		}
	}

	/**
	 * Most of this is taken from the ItemBoat class, credit to Mojang
	 */
	@Override
	public ItemStack onItemRightClick(final ItemStack stack, final World world, final EntityPlayer player)
	{
		if(BoatPart.deserialize(stack.getItemDamage()) instanceof PartBottom == false)
			return stack;
		final float f = 1.0F;
		final float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
		final float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
		final double d0 = player.prevPosX + (player.posX - player.prevPosX) * f;
		final double d1 = player.prevPosY + (player.posY - player.prevPosY) * f + 1.62D - player.yOffset;
		final double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
		final Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
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
			return stack;
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
				return stack;
			else
			{
				if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
				{
					i = movingobjectposition.blockX;
					int j = movingobjectposition.blockY;
					final int k = movingobjectposition.blockZ;

					if (world.getBlock(i, j, k) == Blocks.snow_layer)
						--j;

					final EntityBOBoat entityboat = new EntityBOBoat(world, i + 0.5F, j + 1.0F, k + 0.5F);
					entityboat.rotationYaw = ((MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) - 1) * 90;
					entityboat.addPart(BoatPart.deserialize(stack.getItemDamage()), false, true);

					if (!world.getCollidingBoundingBoxes(entityboat, entityboat.boundingBox.expand(-0.1D, -0.1D, -0.1D)).isEmpty())
						return stack;

					if (!world.isRemote)
						world.spawnEntityInWorld(entityboat);

					if (!player.capabilities.isCreativeMode)
						--stack.stackSize;
				}

				return stack;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item item, final CreativeTabs tab, final List list)
	{
		final TreeMap<Integer, BoatPart> sorter = new TreeMap<Integer, BoatPart>();
		for(final BoatPart part:BoatParts.allParts)
			sorter.put(part.getPartID(), part);
		for(final Integer i:sorter.keySet())
			list.add(new ItemStack(item, 1, i));
	}

	private IIcon[] icons;

	@Override
	public void registerIcons(final IIconRegister register){
		final TreeMap<Integer, BoatPart> sorter = new TreeMap<Integer, BoatPart>();
		for(final BoatPart part:BoatParts.allParts)
			sorter.put(part.getPartID(), part);
		this.icons = new IIcon[sorter.lastKey().intValue()+1];
		for(final Entry<Integer, BoatPart> entry:sorter.entrySet())
			this.icons[entry.getKey()] = register.registerIcon(entry.getValue().getItemTexture());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(final int damage){
		return this.icons[damage];
	}

	@Override
	protected boolean isNameDamageReliant() {
		return true;
	}

}

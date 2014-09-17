package me.superckl.betteroceans.common.item;

import me.superckl.betteroceans.client.model.armor.ModelLifeJacket;
import me.superckl.betteroceans.common.reference.ModTabs;
import me.superckl.betteroceans.common.reference.RenderData;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLifeJacket extends ItemArmorBO{

	//private final AttributeModifier speedMod = new AttributeModifier(UUID.fromString("02b56654-ad90-46ea-86e5-61bc2434f2d9"), "lifeJacketSpeed", -0.4D, 2);

	public ItemLifeJacket() {
		super(ArmorMaterial.CLOTH, 0, 1);
		this.setUnlocalizedName("lifejacket").setCreativeTab(ModTabs.tabItems).setMaxDamage(0).setNoRepair();
	}

	@Override
	public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot,
			final String type) {
		return RenderData.LIFE_JACKET;
	}

	@Override
	public ModelBiped getArmorModel(final EntityLivingBase entityLiving,
			final ItemStack itemStack, final int armorSlot) {
		return new ModelLifeJacket();
	}

	@Override
	public void onArmorTick(final World world, final EntityPlayer player,
			final ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		if(player.isInWater() && player.motionY != -0.02D){
			player.motionX *= .65D;
			player.motionZ *= .65D;
		}
		/*final IAttributeInstance speed = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
		if(player.isInWater() && player.motionY != -0.02D && speed.getModifier(this.speedMod.getID()) == null)
			speed.applyModifier(this.speedMod);
		else if(!player.isInWater())
			speed.removeModifier(this.speedMod);*/
	}



}

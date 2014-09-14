package me.superckl.betteroceans.common.item;

import me.superckl.betteroceans.common.reference.ModTabs;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemLifeJacket extends ItemArmorBO{

	public ItemLifeJacket() {
		super(ArmorMaterial.CLOTH, 0, 1);
		this.setUnlocalizedName("lifejacket").setCreativeTab(ModTabs.tabItems).setMaxStackSize(1).setMaxDamage(1).setNoRepair();
	}

	@Override
	public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot,
			final String type) {
		//TODO
		return super.getArmorTexture(stack, entity, slot, type);
	}

	@Override
	public ModelBiped getArmorModel(final EntityLivingBase entityLiving,
			final ItemStack itemStack, final int armorSlot) {
		//TODO
		return super.getArmorModel(entityLiving, itemStack, armorSlot);
	}



}

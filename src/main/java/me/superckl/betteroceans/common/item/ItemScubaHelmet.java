package me.superckl.betteroceans.common.item;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.reference.ModTabs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemScubaHelmet extends ItemArmorBO{

	public ItemScubaHelmet() {
		super(ArmorMaterial.CLOTH, 0, 0);
		this.setUnlocalizedName("scubahelmet").setCreativeTab(ModTabs.tabItems).setNoRepair();
	}

	@Override
	public void onArmorTick(final World world, final EntityPlayer player,
			final ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		final ItemStack armor = player.getCurrentArmor(2);
		if(armor == null || armor.getItem() != ModItems.scubaTank || !armor.hasTagCompound() || !armor.getTagCompound().hasKey("air"))
			return;
		final float air = armor.getTagCompound().getFloat("air");
		if(air > 0 && player.isInsideOfMaterial(Material.water)){
			player.setAir(300);
			armor.getTagCompound().setFloat("air", Math.max(0, air-BetterOceans.getInstance().getConfig().getAirUseRate()));
		}
	}

}

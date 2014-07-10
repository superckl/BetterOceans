package me.superckl.betteroceans.item;

import me.superckl.betteroceans.reference.ModData;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSeaweedOil extends ItemBO{

	public ItemSeaweedOil()
	{
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName("seaweedoil");
	}

	@Override
	public void registerIcons(final IIconRegister register){
		this.itemIcon = register.registerIcon(ModData.MOD_ID+":seaweedoil");
	}

	@Override
	public ItemStack onEaten(final ItemStack itemStack, final World world, final EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode)
			--itemStack.stackSize;

		if (!world.isRemote)
		{
			for(final Object obj:player.getActivePotionEffects())
				if(obj instanceof PotionEffect){
					final PotionEffect effect = (PotionEffect) obj;
					effect.addCurativeItem(new ItemStack(this, 1));
				}
			player.curePotionEffects(itemStack);
		}

		return itemStack.stackSize <= 0 ? new ItemStack(Items.bucket) : itemStack;
	}

	@Override
	public int getMaxItemUseDuration(final ItemStack itemStack)
	{
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(final ItemStack itemStack)
	{
		return EnumAction.drink;
	}

	@Override
	public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer player)
	{
		player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
		return itemStack;
	}

}

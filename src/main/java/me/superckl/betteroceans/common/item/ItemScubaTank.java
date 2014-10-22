package me.superckl.betteroceans.common.item;

import java.util.List;

import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemScubaTank extends ItemArmorBO{

	public ItemScubaTank() {
		super(ArmorMaterial.IRON, 0, 1);
		this.setUnlocalizedName("scubatank").setCreativeTab(ModTabs.tabItems).setHasSubtypes(true);
	}

	@Override
	public void addInformation(final ItemStack stack, final EntityPlayer player, final List list, final boolean par4) {
		if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("air"))
			return;
		final float air = stack.getTagCompound().getFloat("air");
		if(air > 0)
			list.add("Contains "+(int) Math.ceil(air)+"/100 units of air.");
	}

	@Override
	public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
		list.add(new ItemStack(item));
		final ItemStack stack = new ItemStack(item);
		final NBTTagCompound comp = new NBTTagCompound();
		comp.setFloat("air", 100F);
		stack.setTagCompound(comp);
		list.add(stack);
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack){
		final String name = stack.hasTagCompound() && stack.getTagCompound().hasKey("air") && stack.getTagCompound().getFloat("air") > 0F ? "filledscubatank":"scubatank";
		return String.format("item.%s%s%s", ModData.MOD_ID.toLowerCase(), ":", name);
	}

}

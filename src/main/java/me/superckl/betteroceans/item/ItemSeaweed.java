package me.superckl.betteroceans.item;

import me.superckl.betteroceans.reference.ModBlocks;
import me.superckl.betteroceans.reference.ModData;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSeaweed extends ItemBO{

	public ItemSeaweed() {
		super();
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName("seaweed");
	}

	@Override
	public void registerIcons(final IIconRegister register){
		this.itemIcon = register.registerIcon(ModData.MOD_ID+":seaweed");
	}

	@Override
	public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final int x, final int y, final int z, final int par7, final float par8, final float par9, final float par10)
	{
		if(world.isRemote)
			return false;
		if(ModBlocks.seaweed.canPlaceBlockAt(world, x, y+1, z) && itemStack.stackSize >= 1){
			world.setBlock(x, y+1, z, ModBlocks.seaweed, world.getBlock(x, y, z) == ModBlocks.seaweed ? 1:0, 1 & 2);
			itemStack.stackSize--;
			return true;
		}
		return false;
	}

}

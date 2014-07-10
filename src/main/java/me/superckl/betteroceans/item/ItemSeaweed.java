package me.superckl.betteroceans.item;

import me.superckl.betteroceans.reference.ModBlocks;
import me.superckl.betteroceans.reference.ModData;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSeaweed extends ItemBO{

	public ItemSeaweed() {
		super();
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName("seaweed");
	}
	
	@Override
	public void registerIcons(IIconRegister register){
		this.itemIcon = register.registerIcon(ModData.MOD_ID+":seaweed");
	}
	
	@Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
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

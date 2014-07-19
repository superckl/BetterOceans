package me.superckl.betteroceans.common.block;

import java.util.Random;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatWorkbench;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBasicBoatWorkbench extends BlockContainerBO{

	public BlockBasicBoatWorkbench() {
		super(Material.wood);
		this.setBlockName("basicboatbench").setHardness(2.0F).setResistance(5.0F)
		.setStepSound(Block.soundTypeWood).setCreativeTab(ModTabs.tabBlocks);
	}

	@Override
	public void breakBlock(final World world, final int x, final int y, final int z, final Block p_149749_5_, final int p_149749_6_){
		this.dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
	}

	@Override
	public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_)
	{
		final TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking())
			return false;
		player.openGui(BetterOceans.getInstance(), ModData.GUIIDs.BASIC_BOAT_BENCH, world, x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(final World var1, final int var2) {
		return new TileEntityBoatWorkbench();
	}

	private void dropItems(final World world, final int x, final int y, final int z){
		final Random rand = new Random();

		final TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (!(tileEntity instanceof IInventory))
			return;
		final IInventory inventory = (IInventory) tileEntity;

		for (int i = 0; i < inventory.getSizeInventory()-1; i++) {
			final ItemStack item = inventory.getStackInSlot(i);

			if (item != null && item.stackSize > 0) {
				final float rx = rand.nextFloat() * 0.8F + 0.1F;
				final float ry = rand.nextFloat() * 0.8F + 0.1F;
				final float rz = rand.nextFloat() * 0.8F + 0.1F;

				final EntityItem entityItem = new EntityItem(world,
						x + rx, y + ry, z + rz,
						new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));

				if (item.hasTagCompound())
					entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());

				final float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				item.stackSize = 0;
			}
		}
	}

	private IIcon[] icons;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(final IIconRegister register){
		this.icons = new IIcon[]
				{register.registerIcon(ModData.MOD_ID+":basicbenchtop"),
				register.registerIcon(ModData.MOD_ID+":basicbenchside"),
				register.registerIcon(ModData.MOD_ID+":basicbenchbottom")};
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final int side, final int meta)
	{
		if(side == 0)
			return this.icons[2];
		else if(side == 1)
			return this.icons[0];
		return this.icons[1];
	}

}

package me.superckl.betteroceans.common.block;

import java.util.List;
import java.util.Random;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBoatbench extends BlockContainerBO{

	public BlockBoatbench() {
		super(Material.rock);
		this.setBlockName("boatbench").setStepSound(Block.soundTypeStone).setCreativeTab(ModTabs.tabBlocks);
		this.setHarvestLevel("axe", 0, 0);
		this.setHarvestLevel("pickaxe", 1, 1);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(final Item item, final CreativeTabs tab, final List list)
	{
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}

	private final float[] hardness = new float[] {2.5F, 3.5F};

	@Override
	public float getBlockHardness(final World world, final int x, final int y, final int z)
	{
		return this.hardness[world.getBlockMetadata(x, y, z)];
	}

	@Override
	public void breakBlock(final World world, final int x, final int y, final int z, final Block p_149749_5_, final int p_149749_6_){
		this.dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
	}

	@Override
	public void onBlockPlacedBy(final World world, final int x, final int y, final int z, final EntityLivingBase living, final ItemStack stack) {
		if(stack.hasTagCompound()){
			final NBTTagCompound tagCompound = stack.getTagCompound().getCompoundTag("fluid");
			if(tagCompound != null){
				final FluidStack fluid = FluidStack.loadFluidStackFromNBT(tagCompound);
				final TileEntity te = world.getTileEntity(x, y, z);
				if(te != null && te instanceof TileEntityBoatbench)
					((TileEntityBoatbench)te).getTank().setFluid(fluid);
			}
		}
	}



	@Override
	public void harvestBlock(final World p_149636_1_, final EntityPlayer p_149636_2_,
			final int p_149636_3_, final int p_149636_4_, final int p_149636_5_, final int p_149636_6_) {}

	@Override
	public boolean removedByPlayer(final World world, final EntityPlayer player, final int x, final int y, final int z, final boolean willHarvest) {
		final ItemStack stack = new ItemStack(this, 1, this.damageDropped(world.getBlockMetadata(x, y, z)));
		final TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityBoatbench && ((TileEntityBoatbench)te).isShouldHandleFluids()){
			final IFluidTank tank = ((TileEntityBoatbench)te).getTank();
			if(tank.getFluid() != null){
				final NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setTag("fluid", tank.getFluid().writeToNBT(new NBTTagCompound()));;
				stack.setTagCompound(tagCompound);
			}
		}
		if (!player.capabilities.isCreativeMode || player.isSneaking())
			this.dropRandomly(world, x, y, z, stack);
		return world.setBlockToAir(x, y, z);
	}

	protected void dropRandomly(final World world, final int x, final int y, final int z, final ItemStack stack){
		if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")){
			final float f = 0.7F;
			final double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			final double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			final double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			final EntityItem entityitem = new EntityItem(world, x + d0, y + d1, z + d2, stack);
			entityitem.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entityitem);
		}
	}

	@Override
	public int damageDropped(final int meta) {
		return meta;
	}

	@Override
	public boolean canSustainPlant(final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection direction, final IPlantable plantable)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_)
	{
		final TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking())
			return false;
		final int meta = world.getBlockMetadata(x, y, z);
		player.openGui(BetterOceans.getInstance(), meta == 0 ? ModData.GUIIDs.BASIC_BOAT_BENCH:ModData.GUIIDs.INTER_BOAT_BENCH, world, x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int meta) {
		return new TileEntityBoatbench(meta > 0);
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

	private IIcon[][] icons;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(final IIconRegister register){
		this.icons = new IIcon[2][3];
		this.icons[0][0] = register.registerIcon(ModData.MOD_ID+":basicbenchbottom");
		this.icons[0][1] = register.registerIcon(ModData.MOD_ID+":basicbenchtop");
		this.icons[0][2] = register.registerIcon(ModData.MOD_ID+":basicbenchside");

		this.icons[1][0] = register.registerIcon(ModData.MOD_ID+":interbenchbottom");
		this.icons[1][1] = register.registerIcon(ModData.MOD_ID+":interbenchtop");
		this.icons[1][2] = register.registerIcon(ModData.MOD_ID+":interbenchside");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final int side, final int meta)
	{
		return this.icons[meta][Math.min(side, 2)];
	}

}

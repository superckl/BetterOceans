package me.superckl.betteroceans.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.ExtensionMethod;
import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.reference.ModBlocks;
import me.superckl.betteroceans.reference.ModData;
import me.superckl.betteroceans.reference.ModItems;
import me.superckl.betteroceans.utility.BlockHelper;
import me.superckl.betteroceans.utility.LogHelper;
import me.superckl.betteroceans.utility.NumberUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

@ExtensionMethod(BlockHelper.class)
public class BlockSeaweed extends BlockBO implements IPlantable{
	
	public BlockSeaweed(){
		super(Material.water);
		this.setBlockName("seaweed").setHardness(1).setResistance(0).setTickRandomly(true);
	}
	
	@Override
	public boolean isOpaqueCube(){
		return false;
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int s)
    {
        return blockAccess.getBlock(x, y, z) != Blocks.water;
    }
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }
	
	@Override
	public boolean renderAsNormalBlock()
    {
        return true;
    }
	
	@Override
	public int getRenderType(){
		return 1;
	}
	
	@Override
    public int quantityDropped(Random rand)
    {
		return 1+(int)Math.round(2*rand.nextDouble());
    }

	@Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return ModItems.seaweed;
    }
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        return false;
    }
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_)
    {
		if(!this.isTop(world, x, y, z)){
			this.dropBlockAsItem(world, x, y+1, z, world.getBlockMetadata(x, y+1, z), 0);
			this.breakBlock(world, x, y+1, z, p_149749_5_, p_149749_6_);
			world.setBlock(x, y+1, z, BetterOceans.getInstance().getConfig().isSeaweedToWater() ? Blocks.water:Blocks.air);
		}
		super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
    }
	
	@Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
    	if(world.isRemote)
    		return;
    	if(!this.isTop(world, x, y, z))
    		return;
    	int height = this.getHeight(world, x, y, z);
    	int meta = world.getBlockMetadata(x, y, z);
    	int maxHeight = meta == 0 ? 1:(meta & 1) == 1 ? 2:3;
    	if(maxHeight <= height || rand.nextInt(15) != 0)
    		return;
    	int newMeta = getMetaFor(height, height);
    	world.setBlock(x, y, z, ModBlocks.seaweed, newMeta, 1 & 2);
    }
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z){
		return this.canBlockStay(world, x, y, z);
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z){
		//TODO
		return (world.isWaterSourceAt(x, y, z)
				&& world.isWaterSourceAt(x-1, y, z, this) && world.isWaterSourceAt(x+1, y, z, this))
				&& world.isWaterSourceAt(x, y, z-1, this) && world.isWaterSourceAt(x, y, z+1, this)
				&& world.isWaterSourceAt(x, y+1, z, this)
				&& (world.getBlock(x, y-1, z) == Blocks.dirt || world.getBlock(x, y-1, z) == Blocks.sand
						|| world.getBlock(x, y-1, z) == this);
	}
	
    private int getHeight(World world, int x, int y, int z){
    	int runs = 0;
    	while(!this.isTop(world, x, y, z)){
    		y++;
    		if(++runs > 256) //Infinite loop prevention
    			return 256;
    	}
    	return BlockHelper.getHeight(world, x, y, z, true);
    }
    
    private boolean isTop(World world, int x, int y, int z){
    	return world.getBlock(x, y+1, z) != this;
    }
    
	private IIcon[] icons;
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register){
		this.icons = new IIcon[] 
				{register.registerIcon(ModData.MOD_ID+":seaweed1"),
				register.registerIcon(ModData.MOD_ID+":seaweed20"),
				register.registerIcon(ModData.MOD_ID+":seaweed21"),
				register.registerIcon(ModData.MOD_ID+":seaweed30"),
				register.registerIcon(ModData.MOD_ID+":seaweed31"),
				register.registerIcon(ModData.MOD_ID+":seaweed32")};
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	int pos = 0;
    	if(meta == 0){
    		pos = 0;
    	}else if((meta & 1) == 1){
    		if((meta & 4) == 4)
    			pos = 1;
    		else if((meta & 8) == 8)
    			pos = 2;
    	}else if((meta & 2) == 2){
    		if((meta & 4) == 4)
    			pos = 3;
    		else if((meta & 8) == 8)
    			pos = 4;
    		else if (meta == 2)
    			pos = 5;
    	}
        return this.icons[pos];
    }
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
		return EnumPlantType.Water;
	}

	@Override
	public Block getPlant(IBlockAccess world, int x, int y, int z) {
		return this;
	}

	@Override
	public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}
	
	public static int getMetaFor(int height, int pos){
		if(height == 1){
			return 0;
		}else if(height == 2){
			if(pos == 0)
				return 1 + 4;
			else if (pos == 1)
				return 1 + 8;
		}else if(height == 3){
			if(pos == 0)
				return 2 + 4;
			else if(pos == 1)
				return 2 + 8;
			else if(pos == 2)
				return 2;
		}
		return 0;
	}
}

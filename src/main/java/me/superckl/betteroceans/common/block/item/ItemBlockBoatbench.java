package me.superckl.betteroceans.common.block.item;

import java.util.List;

import me.superckl.betteroceans.common.utility.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class ItemBlockBoatbench extends ItemBlockBO{

	public ItemBlockBoatbench(final Block block) {
		super(block);
		this.setHasSubtypes(true);
	}

	@Override
	protected boolean isNameDamageReliant() {
		return true;
	}

	@Override
	public int getMetadata(final int meta)
	{
		return meta;
	}

	@Override
	public void addInformation(final ItemStack stack, final EntityPlayer player, final List list, final boolean par4) {
		if(stack.hasTagCompound()){
			final NBTTagCompound tagCompound = stack.getTagCompound().getCompoundTag("fluid");
			if(tagCompound != null){
				final FluidStack fluid = FluidStack.loadFluidStackFromNBT(tagCompound);
				if(fluid.amount != 0)
					list.add(StringHelper.build("Contains ",fluid.amount,"mb of ",fluid.getFluid().getLocalizedName(fluid)));
			}
		}
	}



}

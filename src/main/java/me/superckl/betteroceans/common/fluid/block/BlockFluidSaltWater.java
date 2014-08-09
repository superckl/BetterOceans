package me.superckl.betteroceans.common.fluid.block;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidSaltWater extends BlockFluidClassic{

	public BlockFluidSaltWater(final Fluid fluid, final Material material) {
		super(fluid, material);
		this.setBlockName("saltwater");
	}

	@Override
	public IIcon getIcon(final int side, final int meta)
	{
		return side != 0 && side != 1 ? Blocks.flowing_water.getIcon(side, meta) : Blocks.water.getIcon(side, meta);
	}

}

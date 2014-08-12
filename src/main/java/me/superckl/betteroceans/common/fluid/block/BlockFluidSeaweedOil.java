package me.superckl.betteroceans.common.fluid.block;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidSeaweedOil extends BlockFluidClassic{

	public BlockFluidSeaweedOil(final Fluid fluid, final Material material) {
		super(fluid, material);
		this.setBlockName("seaweedoil");
	}

}
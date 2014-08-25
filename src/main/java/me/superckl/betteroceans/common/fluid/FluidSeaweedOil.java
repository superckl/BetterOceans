package me.superckl.betteroceans.common.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidSeaweedOil extends Fluid{

	public FluidSeaweedOil(final String fluidName) {
		super(fluidName);
	}

	@Override
	public String getLocalizedName(final FluidStack stack) {
		if(stack.getFluid() == this)
			return "Seaweed Oil";
		return super.getLocalizedName(stack);
	}



}

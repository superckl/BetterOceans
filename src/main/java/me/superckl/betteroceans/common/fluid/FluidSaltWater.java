package me.superckl.betteroceans.common.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidSaltWater extends Fluid{

	public FluidSaltWater(final String fluidName) {
		super(fluidName);
		//this.setIcons(FluidRegistry.WATER.getStillIcon(), FluidRegistry.WATER.getFlowingIcon());
	}

	@Override
	public String getLocalizedName(final FluidStack stack) {
		if(stack.getFluid() == this)
			return "Salt Water";
		return super.getLocalizedName(stack);
	}

}

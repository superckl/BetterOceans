package me.superckl.betteroceans.common.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidSaltWater extends Fluid{

	public FluidSaltWater(final String fluidName) {
		super(fluidName);
		this.setIcons(FluidRegistry.WATER.getStillIcon(), FluidRegistry.WATER.getFlowingIcon());
	}

}

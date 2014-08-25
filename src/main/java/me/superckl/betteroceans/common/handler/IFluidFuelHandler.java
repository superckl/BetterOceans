package me.superckl.betteroceans.common.handler;

import me.superckl.betteroceans.common.parts.BoatPart;
import net.minecraftforge.fluids.Fluid;

public interface IFluidFuelHandler {

	public int getFuelUsageFor(final Fluid fluid, final BoatPart part);

}

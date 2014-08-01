package me.superckl.betteroceans.common.utility;

import java.util.Arrays;
import java.util.List;

import me.superckl.betteroceans.common.entity.EntityModularBoat;
import me.superckl.betteroceans.common.parts.BoatPart;

public class BoatHelper {

	public static boolean hasParts(final EntityModularBoat boat, final BoatPart.Type ... types){
		final List<BoatPart.Type> toCheck = Arrays.asList(types);
		for(final BoatPart part:boat.getBoatParts())
			toCheck.remove(part.getType());
		return toCheck.isEmpty();
	}

	public static double compoundSpeedModifiers(final EntityModularBoat boat){
		double modifier = 1D;
		for(final BoatPart part:boat.getBoatParts())
			modifier *= part.getSpeedModifier();
		return modifier;
	}

}

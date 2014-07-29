package me.superckl.betteroceans.common.utility;

import java.util.Arrays;
import java.util.List;

import me.superckl.betteroceans.common.BoatPart;
import me.superckl.betteroceans.common.entity.IEntityBoat;

public class BoatHelper {

	public static boolean hasParts(final IEntityBoat boat, final BoatPart.Type ... types){
		final List<BoatPart.Type> toCheck = Arrays.asList(types);
		for(final BoatPart part:boat.getBoatParts())
			toCheck.remove(part.getType());
		return toCheck.isEmpty();
	}

}

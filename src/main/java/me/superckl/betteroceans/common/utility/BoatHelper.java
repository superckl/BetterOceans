package me.superckl.betteroceans.common.utility;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.superckl.betteroceans.common.entity.EntityModularBoat;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.parts.PartEnd;
import me.superckl.betteroceans.common.parts.PartSide;

public class BoatHelper {

	public static boolean hasParts(final EntityModularBoat boat, final BoatPart.Type ... types){
		final List<BoatPart.Type> toCheck = new ArrayList<BoatPart.Type>(Arrays.asList(types));
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

	public static double compoundSinkModifiers(final EntityModularBoat boat){
		double modifier = 1D;
		for(final BoatPart part:boat.getBoatParts())
			modifier *= part.getSinkChanceModifier();
		return modifier;
	}

	public static double compoundIntegrityFactors(final EntityModularBoat boat){
		double modifier = 1D;
		for(final BoatPart part:boat.getBoatParts())
			modifier *= part.getIntegrityFactor();
		return modifier;
	}

	public static boolean hasSide(final EntityModularBoat boat, final boolean left){
		for(final BoatPart part:boat.getBoatParts())
			if(part instanceof PartSide)
				if(((PartSide)part).isLeftSide() == left)
					return true;
		return false;
	}

	public static boolean hasEnd(final EntityModularBoat boat, final boolean front){
		for(final BoatPart part:boat.getBoatParts())
			if(part instanceof PartEnd)
				if(((PartEnd)part).isFront() == front)
					return true;
		return false;
	}

	public static void writePartToBuffer(final BoatPart part, final ByteBuf buf){
		buf.writeInt(part.getPartConstructorID());
	}

	public static BoatPart readPartFromBuffer(final ByteBuf buf){
		return BoatPart.deserialize(buf.readInt());
	}

}

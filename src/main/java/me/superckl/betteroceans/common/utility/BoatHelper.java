package me.superckl.betteroceans.common.utility;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import me.superckl.betteroceans.common.entity.EntityModularBoat;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.parts.BoatPart.Type;
import me.superckl.betteroceans.common.parts.PartEnd;
import me.superckl.betteroceans.common.parts.PartSide;
import me.superckl.betteroceans.common.parts.TypeRequirement;

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

	public static double compoundTurnModifiers(final EntityModularBoat boat){
		double modifier = 1D;
		for(final BoatPart part:boat.getBoatParts())
			modifier *= part.getTurnModifier();
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

	public static boolean areRequirementsSatisfied(final BoatPart part, final EntityModularBoat boat){
		final List<TypeRequirement> required = new ArrayList<TypeRequirement>();
		part.getRequiredTypesWithComplexities(required);
		required.remove(null); //Safety measure
		final List<BoatPart> parts = boat.getBoatParts();
		while(required.size() > 0){
			final Type type = required.get(0).getType();
			final List<Integer> complex = BoatHelper.extractComplexities(required, type);
			final Integer[] array = complex.toArray(new Integer[complex.size()]);
			Arrays.sort(array);
			final List<Integer> complex2 = BoatHelper.extractComplexitiesFromParts(parts, type);
			final Integer[] array2 = complex2.toArray(new Integer[complex2.size()]);
			Arrays.sort(array2);
			int index = 0;
			for(final Integer i:array2)
				if(array[index].intValue() <= i.intValue()){
					array[index] = null;
					if(++index >= array.length)
						break;
				}
			if(index < array.length && !CollectionHelper.isNull(array))
				return false;
		}
		return true;
	}

	/**
	 * NOTE: This method WILL remove elements from the collection
	 */
	public static List<Integer> extractComplexities(final List<TypeRequirement> required, final Type type){
		final List<Integer> complex = new ArrayList<Integer>();
		//First we sort out the requirements of the same type.
		final Iterator<TypeRequirement> it = required.iterator();
		TypeRequirement req;
		while(it.hasNext())
			if((req = it.next()).getType() == type){
				complex.add(req.getComplexity());
				it.remove();
			}
		return complex;
	}

	/**
	 * NOTE: This method will NOT remove elements from the collection
	 */
	public static List<Integer> extractComplexitiesFromParts(final List<BoatPart> parts, final Type type){
		final List<Integer> complex = new ArrayList<Integer>();
		final Iterator<BoatPart> it = parts.iterator();
		BoatPart req;
		while(it.hasNext())
			if((req = it.next()).getType() == type)
				complex.add(req.getComplexity());
		return complex;
	}

	public static void writePartToBuffer(final BoatPart part, final ByteBuf buf){
		buf.writeInt(part.getPartID());
	}

	public static BoatPart readPartFromBuffer(final ByteBuf buf){
		return BoatPart.deserialize(buf.readInt());
	}

}

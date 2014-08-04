package me.superckl.betteroceans.common.reference;

import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.parts.PartBottom;
import me.superckl.betteroceans.common.parts.PartEnd;
import me.superckl.betteroceans.common.parts.PartSide;

public class BoatParts {

	//These are just for reference, don't use them for boats. make new ones

	public static final BoatPart woodenBottom = new PartBottom.PartWoodenBottom();
	public static final BoatPart woodenSideLeft = new PartSide.PartWoodenSide(true);
	public static final BoatPart woodenSideRight = new PartSide.PartWoodenSide(false);
	public static final BoatPart woodenEndFront = new PartEnd.PartWoodenEnd(true);
	public static final BoatPart woodenEndBack = new PartEnd.PartWoodenEnd(false);
	public static final BoatPart[] allParts = new BoatPart[] {BoatParts.woodenBottom, BoatParts.woodenSideLeft, BoatParts.woodenSideRight, BoatParts.woodenEndFront, BoatParts.woodenEndBack};

}

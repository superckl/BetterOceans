package me.superckl.betteroceans.common.reference;

import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.parts.PartBottom;
import me.superckl.betteroceans.common.parts.PartEnd;
import me.superckl.betteroceans.common.parts.PartSide;

public abstract class BoatParts {

	//These are just for reference, don't use them for boats. make new ones

	public static final BoatPart woodenBottom = new PartBottom.PartWoodenBottom();
	public static final BoatPart woodenSideLeft = new PartSide.PartWoodenSide(true);
	//public static final BoatPart woodenSideRight = new PartSide.PartWoodenSide(false);
	public static final BoatPart woodenEndFront = new PartEnd.PartWoodenEnd(true);
	//public static final BoatPart woodenEndBack = new PartEnd.PartWoodenEnd(false);

	public static final BoatPart ironBottom = new PartBottom.PartIronBottom();
	public static final BoatPart ironSideLeft = new PartSide.PartIronSide(true);
	//public static final BoatPart ironSideRight = new PartSide.PartIronSide(false);
	public static final BoatPart ironEndFront = new PartEnd.PartIronEnd(true);
	//public static final BoatPart ironEndBack = new PartEnd.PartIronEnd(false);

	public static final BoatPart[] allParts = new BoatPart[] {BoatParts.woodenBottom, BoatParts.woodenSideLeft, BoatParts.woodenEndFront, BoatParts.ironBottom, BoatParts.ironSideLeft, BoatParts.ironEndFront};

}

package me.superckl.betteroceans.common.utility;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
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
	
	public static void writePartToBuffer(BoatPart part, ByteBuf buf){
		final NBTTagCompound comp = new NBTTagCompound();
		part.serialize(comp);
		buf.writeInt(comp.getInteger("ID"));
		buf.writeBoolean(comp.hasKey("boolFlag"));
		buf.writeBoolean(comp.getBoolean("boolFlag"));
	}
	
	public static BoatPart readPartFromBuffer(ByteBuf buf){
		final NBTTagCompound comp = new NBTTagCompound();
		comp.setInteger("ID", buf.readInt());
		if(buf.readBoolean())
			comp.setBoolean("boolFlag", buf.readBoolean());
		else
			buf.readBoolean(); //To advance it properly
		return BoatPart.deserialize(comp);
	}

}

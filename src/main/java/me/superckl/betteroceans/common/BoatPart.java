package me.superckl.betteroceans.common;

public interface BoatPart {

	public Type getType();

	public static enum Type{
		BOTTOM,
		SIDE,
		END;
	}

	public static enum Material{

	}

}

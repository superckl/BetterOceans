package me.superckl.betteroceans.utility;

import net.minecraft.util.MathHelper;

public class NumberUtil {

	public static int translateToBitPos(int number) throws IllegalArgumentException{
		int pos = (int) Math.round(Math.log(number)/Math.log(2));
		if((int)Math.pow(2, pos) % number != 0)
			throw new IllegalArgumentException("Number holds more than one bit position: "+number);
		return pos+1;
	}
	
}

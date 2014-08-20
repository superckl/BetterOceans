package me.superckl.betteroceans.common.utility;


public class NumberHelper {

	public static int translateToBitPos(final int number) throws IllegalArgumentException{
		final int pos = (int) Math.round(Math.log(number)/Math.log(2));
		if((int)Math.pow(2, pos) % number != 0)
			throw new IllegalArgumentException("Number holds more than one bit position: "+number);
		return pos+1;
	}

	public static int sum(final int ... toSum){
		int sum = 0;
		for(final int i:toSum)
			sum += i;
		return sum;
	}

	public static int sum(final int toIndex, final int ... toSum){
		int sum = 0;
		for(int i = 0; i < toIndex; i++)
			sum += toSum[i];
		return sum;
	}

	public static boolean contains(final int[] array, final int ... numbers){
		for(final int i:array)
			for(final int j:numbers)
				if(i == j)
					return true;
		return false;
	}

	public static int getFillAmount(final int capacity, final int amount){
		if(capacity >= amount)
			return amount;
		return capacity;
	}

}

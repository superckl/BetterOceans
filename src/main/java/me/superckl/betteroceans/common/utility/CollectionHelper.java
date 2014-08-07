package me.superckl.betteroceans.common.utility;

import java.util.Map;
import java.util.Map.Entry;

public class CollectionHelper {

	public static <T, G> T getByValue(final Map<T, G> map, final G value){
		for(final Entry<T, G> entry:map.entrySet())
			if(entry.getValue().equals(value))
				return entry.getKey();
		return null;
	}

	public static <T> int find(final T toFind, final T[] in){
		for(int i = 0; i < in.length; i++)
			if(in[i] == toFind)
				return i;
		return -1;
	}

}

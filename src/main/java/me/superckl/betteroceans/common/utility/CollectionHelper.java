package me.superckl.betteroceans.common.utility;

import java.util.Map;
import java.util.Map.Entry;

public class CollectionHelper {

	public static <T, G> T getByValue(final Map<T, G> map, final G value){
		for(final Entry<T, G> entry:map.entrySet())
			if(entry.getValue().equals(value)){
				LogHelper.info(entry.getKey()+":"+entry.getValue());
				return entry.getKey();
			}
		LogHelper.info("Didn't find value!");
		return null;
	}

}

package me.superckl.betteroceans.common.utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConstructorWrapper<T> {

	@Getter
	private final Class<T> clazz;
	@Getter
	private final Object[] arguments;
	
	/**
	 * NOTE: if your constructor takes a primitive argument, you must use the wrapped form or deserialization will fail.
	 * i.e. Instead of boolean, you must use {@link Boolean}
	 */
	public T newInstance(){
		try {
			return clazz.getDeclaredConstructor(ReflectionHelper.toClassArray(arguments)).newInstance(arguments);
		} catch (Exception e) {
			LogHelper.error("Failed to instantiate new part!");
			e.printStackTrace();
		}
		return null;
	}
	
}

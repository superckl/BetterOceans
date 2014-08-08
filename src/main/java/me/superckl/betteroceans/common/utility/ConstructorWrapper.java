package me.superckl.betteroceans.common.utility;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class ConstructorWrapper<T> {

	@Getter
	private final Class<? extends T> clazz;
	/**
	 * When setting this field, make sure the changes are reflected on both client and server, or deserialization may fail.
	 */
	@Getter
	@Setter
	@NonNull
	private Object[] arguments;

	/**
	 * NOTE: if your constructor takes a primitive argument, you must use the wrapped form or deserialization will fail.
	 * i.e. Instead of boolean, you must use {@link Boolean}
	 */
	public T newInstance(){
		try {
			return this.clazz.getDeclaredConstructor(BOReflectionHelper.toClassArray(this.arguments)).newInstance(this.arguments);
		} catch (final Exception e) {
			LogHelper.error("Failed to instantiate new part!");
			e.printStackTrace();
		}
		return null;
	}

}

package me.superckl.betteroceans.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionUtil {

	public static void setFinalStatic(final Class<?> clazz, final String field, final Object toPut, final boolean coverTracks){
		try {
			final Field toSet = clazz.getDeclaredField(field);
			final boolean access = toSet.isAccessible();
			toSet.setAccessible(true);
			final boolean isFinal = (Modifier.FINAL & toSet.getModifiers()) == Modifier.FINAL;
			final Field modifiers = Field.class.getDeclaredField("modifiers");
			final boolean accessModifiers = modifiers.isAccessible();
			modifiers.setAccessible(true);
			modifiers.setInt(toSet, toSet.getModifiers() & ~Modifier.FINAL);
			toSet.set(null, toPut);
			if(coverTracks){
				if(isFinal)
					modifiers.setInt(toSet, toSet.getModifiers() & Modifier.FINAL);
				modifiers.setAccessible(accessModifiers);
				toSet.setAccessible(access);
			}
		} catch (final Exception e) {
			LogHelper.error("Failed to set static final field "+field+" in class "+clazz.getCanonicalName()+" to "+toPut+". coverTracks: "+coverTracks);
			e.printStackTrace();
		}
	}

}

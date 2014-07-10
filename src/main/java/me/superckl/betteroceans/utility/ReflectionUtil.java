package me.superckl.betteroceans.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import ch.epfl.lamp.compiler.msil.util.Table.FieldMarshal;

public class ReflectionUtil {

	public static void setFinalStatic(Class<?> clazz, String field, Object toPut, boolean coverTracks){
		try {
			Field toSet = clazz.getDeclaredField(field);
			boolean access = toSet.isAccessible();
			toSet.setAccessible(true);
			boolean isFinal = ((Modifier.FINAL & toSet.getModifiers()) == Modifier.FINAL);
			Field modifiers = Field.class.getDeclaredField("modifiers");
			boolean accessModifiers = modifiers.isAccessible();
			modifiers.setAccessible(true);
			modifiers.setInt(toSet, toSet.getModifiers() & ~Modifier.FINAL);
			toSet.set(null, toPut);
			if(coverTracks){
				if(isFinal){
					modifiers.setInt(toSet, toSet.getModifiers() & Modifier.FINAL);
				}
				modifiers.setAccessible(accessModifiers);
				toSet.setAccessible(access);
			}
		} catch (Exception e) {
			LogHelper.error("Failed to set static final field "+field+" in class "+clazz.getCanonicalName()+" to "+toPut+". coverTracks: "+coverTracks);
			e.printStackTrace();
		}
	}
	
}

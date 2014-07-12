package me.superckl.betteroceans.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import net.minecraft.world.biome.BiomeGenBase;

public class ReflectionUtil {

	public static boolean setFinalStatic(final Class<?> clazz, final Object toPut, final boolean coverTracks, final String ... names){
		try {
			Field toSet = null;
			for(final Field field:clazz.getDeclaredFields())
				for(final String name:names)
					if(field.getName().equals(name)){
						toSet = field;
						break;
					}
			if(toSet == null)
				throw new IllegalArgumentException("Could not find "+Arrays.toString(names));
			return ReflectionUtil.setFinalStatic(toSet, toPut, coverTracks);
		} catch (final Exception e) {
			LogHelper.error("Failed to set static final field in class "+clazz.getCanonicalName()+" to "+toPut+". coverTracks: "+coverTracks);
			e.printStackTrace();
		}
		return false;
	}

	public static boolean setFinalStatic(final Field toSet, final Object toPut, final boolean coverTracks){
		try {
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
			LogHelper.error("Failed to set static final field "+toSet.getName()+" in class "+toSet.getDeclaringClass().getCanonicalName()+" to "+toPut+". coverTracks: "+coverTracks);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//public static <T> T getPrivate(Class<T> type, )

	public static boolean setFieldStatic(final Field field, final Object toSet){
		field.setAccessible(true);
		try {
			field.set(null, toSet);
		} catch (final Exception e) {
			LogHelper.warn("Failed to set field "+field.getName()+" to "+toSet);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static Field findBiomeGenField(final int id){
		try {
			for(final Field field:((Class<?>)BiomeGenBase.class).getDeclaredFields()){
				field.setAccessible(true);
				if((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC && BiomeGenBase.class.isAssignableFrom(field.getType())){
					final Object obj = field.get(null);
					if(obj == null)
						continue;
					if(((BiomeGenBase)obj).biomeID == id)
						return field;
				}
			}
		} catch (final Exception e) {
			LogHelper.error("Failed to find biome field: "+id);
			e.printStackTrace();
		}
		return null;
	}

}

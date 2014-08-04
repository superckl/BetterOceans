package me.superckl.betteroceans.common.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import net.minecraft.world.biome.BiomeGenBase;

public class ReflectionHelper {

	public static boolean setFinalStatic(final Class<?> clazz, final Object toPut, final boolean coverTracks, final String ... names){
		try {
			final Field toSet = ReflectionHelper.find(clazz, names);
			if(toSet == null)
				throw new IllegalArgumentException("Could not find "+Arrays.toString(names));
			return ReflectionHelper.setFinalStatic(toSet, toPut, coverTracks);
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

	public static Object retrieveStatic(final Class<?> clazz, final boolean coverTracks, final String ... names){
		try {
			final Field toGet = ReflectionHelper.find(clazz, names);
			if(toGet == null)
				throw new IllegalArgumentException("Could not find "+Arrays.toString(names));
			final boolean access = toGet.isAccessible();
			toGet.setAccessible(true);
			final Object obj = toGet.get(null);
			if(coverTracks)
				toGet.setAccessible(access);
			return obj;
		} catch (final Exception e) {
			LogHelper.warn("Failed to get field in "+clazz.getCanonicalName());
			e.printStackTrace();
		}
		return null;
	}

	public static Field find(final Class<?> clazz, final String ... names){
		for(final Field field:clazz.getDeclaredFields())
			for(final String name:names)
				if(field.getName().equals(name))
					return field;
		return null;
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

	public static <T> Class<?>[] toClassArray(final T[] array){
		final Class<?>[] classArray = new Class<?>[array.length];
		for(int i = 0; i < array.length; i++)
			classArray[i] = array[i].getClass();
		return classArray;
	}

}

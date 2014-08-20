package me.superckl.betteroceans.common.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class BOReflectionHelper {

	public static <T> Field removeFinal(final Class <? super T > clazz, final String... fieldNames)
	{
		final Field field = ReflectionHelper.findField(clazz, ObfuscationReflectionHelper.remapFieldNames(clazz.getName(), fieldNames));

		try
		{
			final Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return field;
	}

	public static <T, E> void setPrivateFinalValue(final Class <? super T > classToAccess, final T instance, final E value, final String... fieldNames)
	{
		final Field field = ReflectionHelper.findField(classToAccess, ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames));

		try
		{
			final Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

			field.set(instance, value);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	public static <T> Class<?>[] toClassArray(final T[] array){
		final Class<?>[] classArray = new Class<?>[array.length];
		for(int i = 0; i < array.length; i++)
			classArray[i] = array[i].getClass();
		return classArray;
	}

	public static StackTraceElement retrieveCallingStackTraceElement(){
		return BOReflectionHelper.retrieveCallingStackTraceElement(3);
	}

	public static StackTraceElement retrieveCallingStackTraceElement(final int depth){
		return new Throwable().getStackTrace()[depth];
	}

}

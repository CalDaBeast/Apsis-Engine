package com.apsis.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * An intelligent class for batch-calling methods.
 * 
 * @author Cal Stephens
 * @param <T> the type the Runner is built to be ran on
 * @see Runner#runOn(java.lang.Object, java.lang.Object...) 
 */
public abstract class Runner<T> {

	//for converting privatives to their wrapper classes
	public final static HashMap<Class<?>, Class<?>> primatives = new HashMap<Class<?>, Class<?>>();

	static {
		primatives.put(boolean.class, Boolean.class);
		primatives.put(byte.class, Byte.class);
		primatives.put(short.class, Short.class);
		primatives.put(char.class, Character.class);
		primatives.put(int.class, Integer.class);
		primatives.put(long.class, Long.class);
		primatives.put(float.class, Float.class);
		primatives.put(double.class, Double.class);
	}

	/**
	 * Finds and calls the method in the Runner instance that
	 * accepts the arguments used in this method call.
	 *
	 * @param run The object to run the method on
	 * @param args Arguments matching the desired parameters
	 * @return the object returned by the called method.
	 * Will be null if no matching method was found or if the
	 * found method vas returned <code>void</code>.
	 * @see Runner#runOnAll(java.util.List, java.lang.Object...) 
	 */
	public final Object runOn(T run, Object... args) {
		if (run == null) throw new IllegalArgumentException("Cannot run methods on a null object.");
		for (Method method : this.getClass().getDeclaredMethods()) {
			Class[] param = method.getParameterTypes();
			//check if method arguments match
			if (param.length != args.length + 1) continue;
			if (!run.getClass().isAssignableFrom(param[0])) continue;
			if (args.length != 0) {
				boolean matching = true;
				for (int i = 0; i < args.length; i++) {
					Class check = param[i + 1];
					if (primatives.containsKey(check)) check = primatives.get(check);
					if (!args[i].getClass().isAssignableFrom(check)) {
						matching = false;
						break;
					}
				}
				if (!matching) continue;
			}
			Object[] callArgs = new Object[args.length + 1];
			callArgs[0] = run;
			System.arraycopy(args, 0, callArgs, 1, args.length);
			try {
				if (!method.isAccessible()) method.setAccessible(true);
				return method.invoke(this, callArgs);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
				throw new IllegalArgumentException("There was a problem running method " + method + " on object " + this
						+ " (Exception=" + ex + ")");
			}
		}
		return null;
	}

	/**
	 * Runs the {@link Runner#runOn(java.lang.Object, java.lang.Object...) runOn()}
	 * method on all of the objects included in the given list.
	 *
	 * @param list The list of objects to run the method on
	 * @param args The arguments for the method
	 * @return an array of the values returned by the method.
	 * list[0]'s returned value will be returned[0].
	 * @see Runner#runOn(java.lang.Object, java.lang.Object...)
	 */
	public final Object[] runOnAll(List<T> list, Object... args) {
		Object[] returns = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {
			returns[i] = runOn(list.get(i), args);
		}
		return returns;
	}

	/**
	 * Runs the {@link Runner#runOn(java.lang.Object, java.lang.Object...) runOn()}
	 * method on all of the objects included in the given array.
	 *
	 * @param array The array of objects to run the method on
	 * @param args The arguments for the method
	 * @return an array of the values returned by the method.
	 * list[0]'s returned value will be returned[0].
	 * @see Runner#runOn(java.lang.Object, java.lang.Object...)
	 */
	public final Object[] runOnAll(T[] array, Object... args) {
		Object[] returns = new Object[array.length];
		for (int i = 0; i < array.length; i++) {
			returns[i] = runOn(array[i], args);
		}
		return returns;
	}

}

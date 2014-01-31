package com.apsis.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Handles subscription and invocation of listeners.
 * Allows for custom events to be registered as easily
 * as stock events are registered.
 *
 * @author Cal Stephens
 */
public class EventHandler {

	private final HashMap<Class<? extends Event>, HashMap<Method, ArrayList<Listener>>> listeners = new HashMap<>();

	/**
	 * Triggers all listener methods that are subscribed to the given event
	 *
	 * @param event the event being triggered
	 * @see EventHandler#subscribe(java.lang.reflect.Method, com.apsis.event.Listener)
	 * @see EventHandler#subscribeAll(com.apsis.event.Listener)
	 */
	public synchronized void triggerEvent(Event event) {
		if (!listeners.containsKey(event.getClass())) return;
		HashMap<Method, ArrayList<Listener>> eventListeners = listeners.get(event.getClass());
		Iterator<Entry<Method, ArrayList<Listener>>> i = eventListeners.entrySet().iterator();
		while (i.hasNext()) {
			Entry<Method, ArrayList<Listener>> entry = i.next();
			for (Listener listener : entry.getValue()) {
				invoke(entry.getKey(), listener, event, entry.getValue());
			}
		}
	}

	/**
	 * Triggers all listener methods that are subscribed to the given event
	 * only for the given objects
	 *
	 * @param event the event being triggered
	 * @param triggerOn objects the trigger the event on
	 * @see EventHandler#subscribe(java.lang.reflect.Method, com.apsis.event.Listener)
	 * @see EventHandler#subscribeAll(com.apsis.event.Listener)
	 */
	public synchronized void triggerEvent(Event event, Listener... triggerOn) {
		if (!listeners.containsKey(event.getClass())) return;
		HashMap<Method, ArrayList<Listener>> eventListeners = listeners.get(event.getClass());
		Iterator<Entry<Method, ArrayList<Listener>>> i = eventListeners.entrySet().iterator();
		while (i.hasNext()) {
			Entry<Method, ArrayList<Listener>> entry = i.next();
			for (Listener trigger : triggerOn) {
				if (entry.getValue().contains(trigger)) {
					invoke(entry.getKey(), trigger, event, entry.getValue());
				}
			}
		}
	}

	/**
	 * Subscribes the given method of the given listener
	 * to the matching event.<br>
	 * The given method will be called when the given event
	 * is triggered until the listener is removed.<br>
	 * Will do nothing if the method + object have already
	 * been subscribed to the given even or if the method
	 * is not a valid listener as described in
	 * {@link EventHandler#methodIsListener(java.lang.reflect.Method)
	 * EventHandler.methodIsListener(...)}
	 *
	 * @param method the method being subscribed to an event
	 * @param listener the Listener instance that owns the method
	 * @see EventHandler#subscribeAll(com.apsis.event.Listener)
	 * @see EventHandler#unsubscribe(java.lang.reflect.Method, com.apsis.event.Listener)
	 */
	public synchronized void subscribe(Method method, Listener listener) {
		if (method == null) throw new IllegalArgumentException("Method cannot be null.");
		if (listener == null) throw new IllegalArgumentException("Listener Object cannot be null.");
		if (!methodIsListener(method)) throw new IllegalArgumentException("Method must be a valid listener.");
		Class<? extends Event> event = getEventOfMethod(method);
		HashMap<Method, ArrayList<Listener>> eventListeners;
		if (listeners.containsKey(event)) eventListeners = listeners.get(event);
		else {
			eventListeners = new HashMap<>();
			listeners.put(event, eventListeners);
		}
		if (eventListeners.containsKey(method)) {
			ArrayList<Listener> methodObjects = eventListeners.get(method);
			if (!methodObjects.contains(listener)) methodObjects.add(listener);
		} else {
			ArrayList<Listener> methodObjects = new ArrayList<Listener>();
			methodObjects.add(listener);
			eventListeners.put(method, methodObjects);
		}
	}

	/**
	 * Subscribes all of the listener methods in the
	 * given listener to their corresponding event.<br>
	 * Definition for valid Event Listener methods described in
	 * {@link EventHandler#methodIsListener(java.lang.reflect.Method)
	 * EventHandler.methodIsListener(...)}
	 *
	 * @param listener the listener object to scan for listener methods
	 * @return a list of the valid methods that were subscribed to their events
	 * @see EventHandler#unsubscribeAll(com.apsis.event.Listener)
	 * @see EventHandler#subscribe(java.lang.reflect.Method, com.apsis.event.Listener)
	 */
	public synchronized ArrayList<Method> subscribeAll(Listener listener) {
		if (listener == null) throw new IllegalArgumentException("Listener cannot be null.");
		ArrayList<Method> subscribed = new ArrayList<>();
		for (Method method : listener.getClass().getMethods()) {
			if (methodIsListener(method)) {
				subscribe(method, listener);
				subscribed.add(method);
			}
		}
		return subscribed;
	}

	/**
	 * Unsubscribes all listener methods in the given listener.
	 *
	 * @param listener the listener object to scan for listener methods
	 * @return a list of the methods that were unsubscribed from their events
	 * @see EventHandler#subscribeAll(com.apsis.event.Listener)
	 * @see EventHandler#unsubscribe(java.lang.reflect.Method, com.apsis.event.Listener)
	 */
	public synchronized ArrayList<Method> unsubscribeAll(Listener listener) {
		if (listener == null) throw new IllegalArgumentException("Listener cannot be null.");
		ArrayList<Method> unsubscribed = new ArrayList<>();
		for (Method method : listener.getClass().getMethods()) {
			if (methodIsListener(method)) {
				unsubscribe(method, listener);
				unsubscribed.add(method);
			}
		}
		return unsubscribed;
	}

	/**
	 * Unsubscribes the method from the event it is listening to.<br>
	 * The given method will no longer be called when
	 * the event is subscribes to is triggered.<br>
	 * Does nothing if the method + object is not currently
	 * subscribed to an event.
	 *
	 * @param method the method being unsubscribed
	 * @param listener the Listener instance that owns the method
	 * @see EventHandler#subscribe(java.lang.Class, java.lang.reflect.Method, com.apsis.event.Listener)
	 */
	public synchronized void unsubscribe(Method method, Listener listener) {
		Class<? extends Event> event = getEventOfMethod(method);
		if (event == null) throw new IllegalArgumentException("Method must be a valid listener.");
		HashMap<Method, ArrayList<Listener>> eventListeners;
		if (listeners.containsKey(event)) eventListeners = listeners.get(event);
		else return;
		if (!eventListeners.containsKey(method)) return;
		if (eventListeners.size() == 1) listeners.remove(event);
		else {
			ArrayList<Listener> methodObjects = eventListeners.get(method);
			if (!methodObjects.contains(listener)) return;
			if (methodObjects.size() == 1) eventListeners.remove(method);
			else methodObjects.remove(listener);
		}
	}

	/**
	 * Gets the class of the event that the method
	 * is built for.
	 * Will return null if the method is not a valid listener as described in
	 * {@link EventHandler#methodIsListener(java.lang.reflect.Method)
	 * EventHandler.methodIsListener(...)}
	 *
	 * @param method the method to check
	 * @return the class of the event of the given method
	 * @see EventHandler#methodIsListener(java.lang.reflect.Method)
	 */
	public Class<? extends Event> getEventOfMethod(Method method) {
		if (!methodIsListener(method)) return null;
		return (Class<? extends Event>) method.getParameterTypes()[0];
	}

	/**
	 * Checks whether of not the given method is a valid listener.
	 * <br><br>
	 * <b>Criteria:</b><br>
	 * <li>The method has an attached {@link EventListener} annotation</li>
	 * <li>The method has one parameter</li>
	 * <li>The method's parameter is an object that
	 * extends {@link Event} but is not simply {@link Event}</li>
	 *
	 * @param method the method to check
	 * @return whether or not the given method is an event
	 */
	public boolean methodIsListener(Method method) {
		if (method == null) return false;
		if (method.getAnnotation(EventListener.class) == null) return false;
		Class[] parameters = method.getParameterTypes();
		if (parameters.length != 1) return false;
		if (Event.class.equals(parameters[0])) return false;
		return Event.class.isAssignableFrom(parameters[0]);
	}

	/**
	 * Used by {@link EventHandler#triggerEvent(com.apsis.event.Event)}
	 *
	 * @param method the method to invoke
	 * @param listener the listener to invoke the method on
	 * @param event the triggered event
	 * @param others the other listeners
	 */
	private synchronized void invoke(Method method, Listener listener, Event event, ArrayList<Listener> others) {
		try {
			method.invoke(listener, event);
		} catch (IllegalAccessException | InvocationTargetException ex) {
			//unsubscribes the invalid method
			if (!methodIsListener(method)) {
				for (Listener unsubscribe : others) {
					unsubscribe(method, unsubscribe);
				}
				throw new IllegalArgumentException("Method (" + method + ") is not a valid listener. "
						+ "How was it in the listeners HashMap...?");
			}
			throw new IllegalArgumentException("There was a problem calling the method " + method);
		}
	}

}

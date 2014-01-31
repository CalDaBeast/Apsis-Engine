package com.apsis.world;

import com.apsis.event.EventHandler;
import com.apsis.event.Listener;
import com.apsis.event.world.*;
import com.apsis.render.Graphics;
import com.apsis.render.Renderable;
import com.apsis.util.Group;
import com.apsis.util.Runner;

/**
 * @author Cal Stephens
 */
public class World extends Group<PointObject> implements Renderable {
	
	private final EventHandler handler = new EventHandler();

	/**
	 * Renders all of the objects contained
	 * in the World
	 *
	 * @param g the Graphics implementation to render with
	 */
	@Override
	public void render(Graphics g) {
		Runner<PointObject> render = new Runner<PointObject>() {
			public void render(PointObject object, Graphics g) {
				if (object instanceof Renderable) {
					((Renderable) object).render(g);
				}
			}
		};
		this.runOnAll(render, g);
	}
	
	private long lastTickStart = 0L;
	private long lastTickEnd = 0L;
	private long lastTickDuration = 0L;

	/**
	 * Manually starts a tick
	 */
	public void tick() {
		long tickStart = System.currentTimeMillis();
		TickEvent tick = new TickEvent(lastTickStart, lastTickEnd, lastTickDuration, tickStart);
		handler.triggerEvent(tick);
		lastTickStart = tickStart;
		lastTickEnd = System.currentTimeMillis();
		lastTickDuration = lastTickEnd - lastTickStart;
	}

	/**
	 * Gets the EventHandler of the World
	 *
	 * @return the world's Event Handler
	 * @see EventHandler
	 */
	public EventHandler getEventHandler() {
		return handler;
	}
	
	@Override
	public synchronized void add(PointObject... objects) {
		for (PointObject object : objects) {
			if (object instanceof Listener) {
				Listener listener = (Listener) object;
				handler.triggerEvent(new AddToWorldEvent(), listener);
				handler.subscribeAll(listener);
			}
			addToGroup(object);
		}
	}
	
	@Override
	public synchronized void remove(PointObject... objects) {
		for (PointObject object : objects) {
			if (object instanceof Listener) {
				Listener listener = (Listener) object;
				handler.triggerEvent(new RemoveFromWorldEvent(), listener);
				handler.unsubscribeAll(listener);
			}
			removeFromGroup(object);
		}
	}

	/**
	 * A direct call to the masked method {@link Group#add(java.lang.Object...)}
	 *
	 * @param object the object to add
	 */
	private synchronized void addToGroup(PointObject object) {
		super.add(object);
	}

	/**
	 * A direct call to the masked method {@link Group#add(java.lang.Object...)}
	 *
	 * @param object the object to add
	 */
	private synchronized void removeFromGroup(PointObject object) {
		super.remove(object);
	}
	
}

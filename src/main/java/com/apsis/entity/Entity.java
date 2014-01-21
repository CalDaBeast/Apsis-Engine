package com.apsis.entity;

import com.apsis.event.Listener;
import com.apsis.util.Point;
import com.apsis.world.PointObject;

/**
 * @author Cal Stephens
 */
public class Entity extends PointObject implements Listener {

	public Entity(Point pos, double rot) {
		super(pos, rot);
	}

}

package com.apsis.world;

import com.apsis.util.Group;
import com.apsis.util.Groupable;
import com.apsis.util.Point;

/**
 * An object that exists on a Coordinate Plane.
 * Also possesses Rotation.
 *
 * @author Cal Stephens
 * @see Point
 */
public class PointObject implements Groupable{

	private Point pos;
	private double rot = 0;
	private Group<PointObject> owningGroup = null;
	
	/**
	 * @param pos The location of the object
	 */
	public PointObject(Point pos) {
		this.pos = pos;
	}

	/**
	 * @param pos The location of the object
	 * @param rot The rotation of the object in degrees
	 */
	public PointObject(Point pos, double rot) {
		this.pos = pos;
		this.rot = rot;
	}

	/**
	 * @return the X Coordinate of the object's location
	 */
	public double x() {
		return pos.x();
	}
	
	/**
	 * @return the Y Coordinate of the object's location
	 */
	public double y() {
		return pos.y();
	}
	
	/**
	 * @return the Point representation of the object's location
	 * @see PointObject#getPos()
	 */
	public Point pos() {
		return pos;
	}

	/**
	 * @return the Point representation of the object's location
	 * @see PointObject#pos()
	 */
	public Point getPos() {
		return pos;
	}

	/**
	 * @param pos the new position of the object
	 * @see PointObject#moveTo(com.apsis.util.Point)
	 */
	public void setPos(Point pos) {
		this.pos = pos;
	}

	/**
	 * Moves the object to the specified point.
	 *
	 * @param pos the new position of the object
	 * @see PointObject#setPos(com.apsis.util.Point) 
	 */
	public void moveTo(Point pos) {
		this.pos = pos;
	}
	
	/**
	 * Adds the given x and y to the x and y of
	 * the object's position.
	 *
	 * @param x the X to add
	 * @param y the Y to add
	 */
	public void move(double x, double y) {
		pos.add(x, y);
	}

	/**
	 * @return the Rotation of the object
	 */
	public double getRot() {
		return rot;
	}

	/**
	 * Rotates the object amount degrees counter-clockwise.
	 * Rotate by a negative number to rotate clockwise.
	 *
	 * @param amount the amount of degrees to rotate
	 */
	public void rotate(double amount) {
		rot += amount;
	}

	/**
	 * @param rot the new rotation of the object
	 */
	public void setRot(double rot) {
		this.rot = rot;
	}

	/**
	 * @return the Group that this object is contained in, null if it is not in a group. 
	 */
	public Group<PointObject> getOwningGroup(){
		return owningGroup;
	}
	
	/**
	 * @return the World that this object is contained in, null if it is not in a world. 
	 */
	public World getOwningWorld(){
		if(owningGroup instanceof World) return (World) owningGroup;
		return null;
	}
	
	@Override
	public boolean addedTo(Group<? extends Groupable> group) {
		if(group.getContent().contains(this)){
			throw new IllegalArgumentException(group + " already contains this object.");
		}
		this.owningGroup = (Group<PointObject>) group;
		return true;
	}

	@Override
	public void removedFrom(Group<? extends Groupable> group) {
		this.owningGroup = null;
	}

}

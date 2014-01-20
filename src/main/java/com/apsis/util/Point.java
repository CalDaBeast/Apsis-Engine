package com.apsis.util;

import java.awt.geom.AffineTransform;

/**
 * A 2-dimensional point using X and Y
 *
 * @author Cal Stephens
 */
public class Point {

	private double x;
	private double y;

	/**
	 * Creates a new Point
	 *
	 * @param x the X of the point
	 * @param y the Y of the point
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates a new Point
	 *
	 * @param x the X of the point
	 * @param y the Y of the point
	 * @return the new point
	 */
	public static Point make(double x, double y) {
		return new Point(x, y);
	}

	/**
	 * Converts an AWT Point to an Apsis Point
	 *
	 * @param awtPoint the AWT point
	 * @see java.awt.Point
	 */
	public Point(java.awt.Point awtPoint) {
		this(awtPoint.getX(), awtPoint.getY());
	}

	/**
	 * @return the X value of the point
	 * @see Point#getX
	 */
	public double x() {
		return x;
	}

	/**
	 * @return the Y value of the point
	 * @see Point#getY()
	 */
	public double y() {
		return y;
	}

	/**
	 * @return the X value of the point
	 * @see Point#x()
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the Y value of the point
	 * @see Point#y()
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param x the x to set
	 * @return <code>return this;</code> for chaining
	 */
	public Point setX(double x) {
		this.x = x;
		return this;
	}

	/**
	 * @param y the y to set
	 * @return <code>return this;</code> for chaining
	 */
	public Point setY(double y) {
		this.y = y;
		return this;
	}

	/**
	 * Sets both the X and Y of the point
	 *
	 * @param x the x to set
	 * @param y the y to set
	 * @return <code>return this;</code> for chaining
	 */
	public Point set(double x, double y) {
		setX(x);
		setY(y);
		return this;
	}

	/**
	 * Adds the given X and Y to the X and Y of the Point
	 *
	 * @param x the X to add
	 * @param y the Y to add
	 * @return <code>return this;</code> for chaining
	 */
	public Point add(double x, double y) {
		setX(x() + x);
		setY(y() + y);
		return this;
	}

	/**
	 * Adds the X and Y of the given point to the X and Y of the Point
	 *
	 * @param point the point to reference
	 * @return <code>return this;</code> for chaining
	 */
	public Point add(Point point) {
		return add(point.x(), point.y());
	}

	/**
	 * Subtracts the given X and Y to the X and Y of the Point
	 *
	 * @param x the X to subtract
	 * @param y the Y to subtract
	 * @return <code>return this;</code> for chaining
	 */
	public Point subtract(double x, double y) {
		setX(x() - x);
		setY(y() - y);
		return this;
	}

	/**
	 * Subtracts the X and Y of the given point to the X and Y of the Point
	 *
	 * @param point the point to reference
	 * @return <code>return this;</code> for chaining
	 */
	public Point subtract(Point point) {
		return subtract(point.x(), point.y());
	}

	/**
	 * Multiplies the X and Y of the Point with given X and Y
	 *
	 * @param x the X to multiply
	 * @param y the Y to multiply
	 * @return <code>return this;</code> for chaining
	 */
	public Point multiply(double x, double y) {
		setX(x() * x);
		setY(y() * y);
		return this;
	}

	/**
	 * Multiplies the X and Y of the Point with the X and Y of the given point
	 *
	 * @param point the point to reference
	 * @return <code>return this;</code> for chaining
	 */
	public Point multiply(Point point) {
		return multiply(point.x(), point.y());
	}

	/**
	 * Divides the X and Y of the Point with the given X and Y
	 *
	 * @param x the X to divide by
	 * @param y the Y to divide by
	 * @return <code>return this;</code> for chaining
	 */
	public Point divide(double x, double y) {
		setX(x() / x);
		setY(y() / y);
		return this;
	}

	/**
	 * Divides the X and Y of the Point with the X and Y of the given point
	 *
	 * @param point the point to reference
	 * @return <code>return this;</code> for chaining
	 */
	public Point divide(Point point) {
		return divide(point.x(), point.y());
	}
	
	/**
	 * Rotates the point X degrees counterclockwise around a circle with its 
	 * center at the given point and its radius the distance between the two points.
	 *
	 * @param around the point to reference
	 * @param degrees the number of degrees to rotate the point by
	 * @return <code>return this;</code> for chaining
	 */
	public Point rotateAround(Point around, double degrees) {
		double[] pt = {x, y};
		AffineTransform.getRotateInstance(Math.toRadians(degrees), around.x(), around.y()).transform(pt, 0, pt, 0, 1);
		setX(pt[0]);
		setY(pt[1]);
		return this;
	}

	/**
	 * Calculates the distance this point and the given point
	 *
	 * @param point the other point
	 * @return the distance between the two points
	 */
	public double distance(Point point) {
		return Math.sqrt(Math.pow(this.x() - point.x(), 2) - Math.pow(this.y() - point.y(), 2));
	}

	/**
	 * Calculates the midpoint of a line drawn between this point and the given point
	 *
	 * @param point the other point
	 * @return the midpoint
	 */
	public Point midpoint(Point point) {
		return Point.make((point.x() + this.x()) / 2, (point.y() + this.y()) / 2);
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 97 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
		hash = 97 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final Point other = (Point) obj;
		if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) return false;
		return Double.doubleToLongBits(this.y) == Double.doubleToLongBits(other.y);
	}

	@Override
	public String toString() {
		return "Point{" + "x=" + x + ", y=" + y + '}';
	}

}

package com.apsis.render;

import com.apsis.util.Color;
import com.apsis.util.Point;

/**
 * @author Cal Stephens
 */
public interface Graphics {

	public void drawRectangle(Color color, Point origin, Point dim);
	
	public void drawRectangle(Color color, Point origin, Point dim, double rot, Point centerOfRot);
	
	public void drawOval(Color color, Point origin, Point dim);
	
	public void drawOval(Color color, Point origin, Point dim, double rot, Point centerOfRot);
	
	public void drawPolygon(Color color, Point origin, Point[] points);
	
	public void drawPolygon(Color color, Point origin, Point[] points, double rot, Point centerOfRot);
	
	public void drawLine(Color color, Point start, Point end, double width);
	
}

package com.apsis.render;

/**
 * @author Cal Stephens
 */
public interface Renderable {

	/**
	 * Renders the object
	 * @param g the Graphics implementation to render with
	 * @see Graphics
	 */
	public void render(Graphics g);
	
}

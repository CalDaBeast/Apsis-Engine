package com.cal.test;

import com.apsis.event.Listener;
import com.apsis.render.Window;
import com.apsis.util.Point;
import com.apsis.world.World;

/**
 * @author Cal Stephens
 */
public class DeploymentTest implements Listener {

	public static void main(String[] args) {
		Window window = new Window(Point.make(250, 250));
		World world = new World();
		window.setRenderingWorld(world);
		window.startRendering();
	}

	

}

package com.cal.test;

import com.apsis.event.EventHandler;
import com.apsis.event.EventListener;
import com.apsis.event.Listener;
import com.apsis.event.TickEvent;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 * @author Cal Stephens
 */
public class DeploymentTest implements Listener{

	public static void main(String[] args) {
		EventHandler handler = new EventHandler();
		DeploymentTest test = new DeploymentTest();
		handler.subscribeAll(test);
		handler.triggerEvent(new TickEvent());
		handler.triggerEvent(new TickEvent());
		handler.unsubscribeAll(test);
		handler.triggerEvent(new TickEvent());
	}
	
	@EventListener
	public void onTick(TickEvent e){
		System.out.println("tick tock");
	}

	public DeploymentTest() {
		/*
		System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
		try {
			Display.setDisplayMode(new DisplayMode(250, 250));
			Display.create(new PixelFormat(8, 0, 0, 8));
			initGL(250, 250);
		} catch (LWJGLException e) {
			e.printStackTrace(System.out);
			System.exit(0);
		}
		*/
	}

	public final void initGL(int x, int y) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, x, 0, y, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	public void startRendering() {
		while (!Display.isCloseRequested()) {

		}
		Display.destroy();
		System.exit(0);
	}

}

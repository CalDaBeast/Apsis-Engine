package com.apsis.render;

import com.apsis.util.Point;
import com.apsis.world.World;
import java.io.File;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

/**
 * @author Cal Stephens
 */
public class Window {

	private World renderingWorld = null;
	private Graphics graphics = new Graphics();
	
	public Window(Point windowDim) {
		System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
		try {
			Display.setDisplayMode(new DisplayMode((int) windowDim.x(), (int) windowDim.y()));
			Display.create(new PixelFormat(8, 0, 0, 8));
			initGL((int) windowDim.x(), (int) windowDim.y());
		} catch (LWJGLException e) {
			e.printStackTrace(System.out);
			System.exit(0);
		}
	}

	public final void initGL(int x, int y) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, x, 0, y, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	public void setRenderingWorld(World world){
		this.renderingWorld = world;
	}
	
	public void startRendering(){
		if(renderingWorld == null) throw new IllegalArgumentException("Rendering World must be set before rendering can start.");
		while (!Display.isCloseRequested()) {
			renderingWorld.render(graphics);
		}
		Display.destroy();
		System.exit(0);
	}

}

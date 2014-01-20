package com.cal.test;

import java.io.File;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

/**
 * @author Cal Stephens
 */
public class DeploymentTest {

	public static void main(String[] args) {
		DeploymentTest test = new DeploymentTest();
		test.startRendering();
		/*
		 * Runner<String> run = new Runner<String>(){
		 * public String multiply(String s, int times){
		 * System.out.println(s + " " + times);
		 * String build = "";
		 * for(int i = 0; i < times; i++){
		 * build += s;
		 * }
		 * return build;
		 * }
		 * };
		 * System.out.println(run.runOn("Hello", 10));
		 */
	}

	public DeploymentTest() {
		System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
		try {
			Display.setDisplayMode(new DisplayMode(250, 250));
			Display.create(new PixelFormat(8, 0, 0, 8));
			initGL(250, 250);
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

	public void startRendering() {
		while (!Display.isCloseRequested()) {

		}
		Display.destroy();
		System.exit(0);
	}

}

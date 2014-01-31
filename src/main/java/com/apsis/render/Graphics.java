package com.apsis.render;

import com.apsis.util.Color;
import com.apsis.util.Point;
import org.lwjgl.opengl.GL11;

/**
 * An OpenGL implementation of the Apsis Graphics system
 *
 * @author Cal Stephens
 */
public class Graphics {

	public void drawRectangle(Color color, Point origin, Point dim) {
		drawRectangle(color, origin, dim, 0, origin);
	}

	public void drawRectangle(Color color, Point origin, Point dim, double rot, Point centerOfRot) {
		GL11.glPushMatrix();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			//GL11.glTranslated(origin.x(), origin.y(), 0);
			GL11.glRotated(rot, 0, 0, 1);
			GL11.glBegin(GL11.GL_QUADS);
				if (color != null) GL11.glColor4d(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, color.getAlpha() / 255.0);
				GL11.glVertex2d(origin.x(), origin.y());
				GL11.glVertex2d(origin.x() + dim.x(), origin.y());
				GL11.glVertex2d(origin.x(), origin.y() + dim.y());
				GL11.glVertex2d(origin.x() + dim.x(), origin.y() + dim.y());
			GL11.glEnd();
		GL11.glPopMatrix();
	}

	public void drawOval(Color color, Point origin, Point dim) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void drawOval(Color color, Point origin, Point dim, double rot, Point centerOfRot) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void drawPolygon(Color color, Point origin, Point[] points) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void drawPolygon(Color color, Point origin, Point[] points, double rot, Point centerOfRot) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void drawLine(Color color, Point start, Point end, double width) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}

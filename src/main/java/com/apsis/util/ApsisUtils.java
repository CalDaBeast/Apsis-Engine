package com.apsis.util;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 * Assorted utility methods for the Apsis Engine
 *
 * @author Cal Stephens
 */
public class ApsisUtils {

	private static OS operatingSystem = null;

	/**
	 * Retrieves the name of the user's operating
	 * system and stores in an {@link OS} object.
	 *
	 * @return the {@link OS} object of the operating system
	 * @see OS
	 */
	public OS getOperatingSystem() {
		if (operatingSystem == null) {
			String name = System.getProperty("os.name");
			OS system;
			if (name.startsWith("Windows")) system = OS.Windows;
			else if (name.startsWith("Mac")) system = OS.Mac;
			else system = OS.Other;
			system.setName(name);
			operatingSystem = system;
		}
		return operatingSystem;
	}

	/**
	 * A qualification of the user's Operating System
	 * as detected by Java.
	 *
	 * @see ApsisUtils#getOperatingSystem()
	 * @see OS#Windows
	 * @see OS#Mac
	 * @see OS#Other
	 */
	public enum OS {

		/**
		 * A variation of Mac OS X
		 */
		Mac,
		/**
		 * A variation of Microsoft Windows
		 */
		Windows,
		/**
		 * Likely a flavor of Linux.
		 * Could be something else such as Solaris,
		 * but honestly who uses Solaris?
		 */
		Other;

		private String fullName = "";

		private void setName(String name) {
			this.fullName = name;
		}

		/**
		 * The full itemized name of the Operating
		 * System as originally detected by Java.
		 *
		 * @return the full system name
		 */
		public String getFullName() {
			return fullName;
		}

	}

	/**
	 * Gets the dimensions of the primary screen
	 *
	 * @return a Point representation of the screen dimensions
	 * (width = x, height = y)
	 * @see Point
	 */
	public Point getScreenDimensions() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		return Point.make(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
	}

	/**
	 * Gets the point that would align the given object at the center
	 * of the given container.
	 * Assumes bottom-left point dominance
	 * 
	 * @param objectDimensions dimensions of the object to align
	 * @param containerDimensions dimensions of the container (screen, window, etc.)
	 * @return the calculated point
	 */
	public Point getCenterForObject(Point objectDimensions, Point containerDimensions) {
		return new Point((containerDimensions.x() / 2) - (objectDimensions.x() / 2),
				(containerDimensions.y() / 2) - (objectDimensions.y() / 2));
	}

}

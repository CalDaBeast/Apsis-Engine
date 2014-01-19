package com.apsis.util;

import java.util.Random;

/**
 * The Color object referenced when Apsis renders
 * Uses the RGB model.
 *
 * @author Cal Stephens
 */
public class Color {

	//presets
	public final static Color WHITE = new Color("FFFFFF");
	public final static Color PINK = new Color("FF00FF");
	public final static Color MAGENTA = new Color("FF00FF");
	public final static Color RED = new Color("FF0000");
	public final static Color MAROON = new Color("800000");
	public final static Color ORANGE = new Color("FFA500");
	public final static Color BROWN = new Color("A52A2A");
	public final static Color YELLOW = new Color("FFFF00");
	public final static Color LIGHT_GRAY = new Color("C0C0C0");
	public final static Color GRAY = new Color("808080");
	public final static Color DARK_GRAY = new Color("383838");
	public final static Color DARK_GREEN = new Color("008000");
	public final static Color LIGHT_GREEN = new Color("00FF00");
	public final static Color OLIVE_GREEN = new Color("808000");
	public final static Color CYAN = new Color("00FFFF");
	public final static Color BLUE = new Color("0000FF");
	public final static Color DARK_BLUE = new Color("0000A0");
	public final static Color LIGHT_BLUE = new Color("ADD8E6");
	public final static Color VIOLET = new Color("800080");
	public final static Color PURPLE = new Color("800080");
	public final static Color BLACK = new Color("000000");
	/**
	 * A list of the avaliable Preset Colors.
	 * COLORS[X]'s name is COLOR_NAMES[X]
	 * @see Color#COLOR_NAMES
	 */
	public final static Color[] COLORS = {WHITE, PINK, MAGENTA, RED, MAROON, ORANGE, BROWN, YELLOW,
		LIGHT_GRAY, GRAY, DARK_GRAY, LIGHT_GREEN, OLIVE_GREEN, CYAN, BLUE, DARK_BLUE, LIGHT_BLUE,
		VIOLET, PURPLE, BLACK};
	/**
	 * A list of the names of the avaliable Preset Colors.
	 * COLOR_NAMES[X]'s representative color is COLORS[X]
	 * @see Color#COLORS
	 */
	public final static String[] COLOR_NAMES = {"WHITE", "PINK", "MAGENTA", "RED", "MAROON",
		"ORANGE", "BROWN", "YELLOW", "LIGHT_GRAY", "GRAY", "DARK_GRAY", "LIGHT_GREEN", "OLIVE_GREEN",
		"CYAN", "BLUE", "DARK_BLUE", "LIGHT_BLUE", "VIOLET", "PURPLE", "BLACK"};
	//end presets

	/**
	 * Creates and returns a random color.
	 * 
	 * <b>if preset == false:</b>	
	 * Creates a random color with Red, Green, and Blue values
	 * randomly assigned between 0 and 255.
	 * <b>if preset == true:</b>
	 * Chooses a random color from the list of presets.
	 * 
	 * @param preset whether or not to use only the preset list
	 * @return the randomly selected color based on the above criteria
	 * @see Color#COLORS
	 */
	public static Color getRandomColor(boolean preset) {
		Random r = new Random();
		if (preset) return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
		else return COLORS[r.nextInt(COLORS.length)];
	}

	private double red;
	private double green;
	private double blue;
	private double alpha;
	private String name = null;

	/**
	 * All values must be between <b>0 and 255</b>.
	 * The Alpha of the color will be assumed as 255 (fully opaque);
	 *
	 * @param red the Red component of the color
	 * @param green the Green component of the color
	 * @param blue the Blue component of the color
	 */
	public Color(double red, double green, double blue) {
		this(red, green, blue, 255);
	}

	/**
	 * All values must be between <b>0 and 255</b>.
	 *
	 * @param red the Red component of the color
	 * @param green the Green component of the color
	 * @param blue the Blue component of the color
	 * @param alpha the Alpha component of the color
	 */
	public Color(double red, double green, double blue, double alpha) {
		setRed(red);
		setGreen(green);
		setBlue(blue);
		setAlpha(alpha);
	}

	/**
	 * Parses a hexadecimal string into an RGB color.
	 * String must be of the format #xxXXxx or xxXXxx
	 * where X/x stands for a hexadecimal digit [0-F].
	 *
	 * @param hex
	 */
	public Color(String hex) {
		if (hex.length() != 6 && (hex.length() != 7 && hex.startsWith("#"))) {
			throw new IllegalArgumentException("String must be of the format #xxxxxx or xxXXxx"
					+ " where X/x stands for a hexadecimal digit [0-F]");
		}
		char[] chars = hex.toCharArray();
		int base = (chars[0] == '#' ? 1 : 0);
		String r = chars[base] + "" + chars[base + 1];
		String g = chars[base + 2] + "" + chars[base + 3];
		String b = chars[base + 4] + "" + chars[base + 5];
		try {
			setRed(Integer.parseInt(r, 16));
			setGreen(Integer.parseInt(g, 16));
			setBlue(Integer.parseInt(b, 16));
			setAlpha(255);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("The given string (" + hex + ") could not be parsed.");
		}
	}

	/**
	 * @return the Red component of the color
	 */
	public double getRed() {
		return red;
	}

	/**
	 * Set the Red component of the color.
	 * Will be set to 255 if the input value is greater than 255
	 * and 0 if the input value is less than 0.
	 *
	 * @param red the new Red component of the color
	 */
	public final void setRed(double red) {
		if (red > 255) red = 255;
		else if (red < 0) red = 0;
		this.red = red;
	}

	/**
	 * @return the Green component of the color
	 */
	public double getGreen() {
		return green;
	}

	/**
	 * Set the Green component of the color.
	 * Will be set to 255 if the input value is greater than 255
	 * and 0 if the input value is less than 0.
	 *
	 * @param green the new Green component of the color
	 */
	public final void setGreen(double green) {
		if (green > 255) green = 255;
		else if (green < 0) green = 0;
		this.green = green;
	}

	/**
	 * @return the Blue component of the color
	 */
	public double getBlue() {
		return blue;
	}

	/**
	 * Set the Blue component of the color.
	 * Will be set to 255 if the input value is greater than 255
	 * and 0 if the input value is less than 0.
	 *
	 * @param blue the new Blue component of the color
	 */
	public final void setBlue(double blue) {
		if (blue > 255) blue = 255;
		else if (blue < 0) blue = 0;
		this.blue = blue;
	}

	/**
	 * Sets all of the color components of the color,
	 * creating a gray scale color with intensity <i>value</i>.
	 *
	 * @param value the new color component value
	 * @see Color.lighten(), Color.darken()
	 */
	public final void setAll(double value) {
		setRed(value);
		setGreen(value);
		setBlue(value);
	}

	/**
	 * @return the Alpha component of the color
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * Set the Alpha component of the color.
	 * Will be set to 255 if the input value is greater than 255
	 * and 0 if the input value is less than 0.
	 *
	 * @param alpha the new Alpha component of the color
	 */
	public final void setAlpha(double alpha) {
		if (alpha > 255) alpha = 255;
		else if (alpha < 0) alpha = 0;
		this.alpha = alpha;
	}

	/**
	 * Lowers the color values (RGB) of the color by 50, darkening it.
	 *
	 * @return the color that the method was run on, newly darkened (<code>return this;</code>)
	 */
	public Color darken() {
		return adjust(-50);
	}

	/**
	 * Raises the color values (RGB) of the color by 50, lightening it.
	 *
	 * @return the color that the method was run on, newly lightened (<code>return this;</code>)
	 */
	public Color lighten() {
		return adjust(50);
	}

	/**
	 * Adjusts the color values (RGB) of the color by the specified amount
	 *
	 * @param amount The amount to adjust the color values by
	 * @return the color that the method was run on, newly adjusted (<code>return this;</code>)
	 */
	public Color adjust(double amount) {
		setRed(getRed() + amount);
		setGreen(getRed() + amount);
		setBlue(getRed() + amount);
		return this;
	}

	/**
	 * Creates a String representing the color in hexadecimal,
	 * formated as <b>#rrggbb</b>.
	 *
	 * @return the Hexadecimal representation
	 */
	public String toHexString() {
		int r = (int) Math.round(red);
		int g = (int) Math.round(green);
		int b = (int) Math.round(blue);
		return "#" + ((Integer.toHexString(r).length() == 1 ? "0" : "") + Integer.toHexString(r)
				+ (Integer.toHexString(g).length() == 1 ? "0" : "") + Integer.toHexString(g)
				+ (Integer.toHexString(b).length() == 1 ? "0" : "") + Integer.toHexString(b)).toUpperCase();
	}

	/**
	 * @param name the new Name of the color.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the color.
	 * If no name has been set, the name of
	 * the most similar preset is returned instead.
	 *
	 * @return the name of the color
	 * @see Color#COLOR_NAMES Preset Names
	 */
	public String getName() {
		if (name == null) {
			//finds the most similar preset
			String mostSimilar = null;
			double diff = 255;
			for (int i = 0; i < COLORS.length; i++) {
				Color c = COLORS[i];
				double diffc = (Math.abs(c.getRed() - red) + Math.abs(c.getGreen() - green) + Math.abs(c.getBlue() - blue));
				if (diffc < diff) {
					mostSimilar = COLOR_NAMES[i];
					diff = diffc;
				}
			}
			return mostSimilar;
		}
		return name;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + (int) (Double.doubleToLongBits(this.red) ^ (Double.doubleToLongBits(this.red) >>> 32));
		hash = 97 * hash + (int) (Double.doubleToLongBits(this.green) ^ (Double.doubleToLongBits(this.green) >>> 32));
		hash = 97 * hash + (int) (Double.doubleToLongBits(this.blue) ^ (Double.doubleToLongBits(this.blue) >>> 32));
		hash = 97 * hash + (int) (Double.doubleToLongBits(this.alpha) ^ (Double.doubleToLongBits(this.alpha) >>> 32));
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final Color other = (Color) obj;
		if (Double.doubleToLongBits(this.red) != Double.doubleToLongBits(other.red)) return false;
		if (Double.doubleToLongBits(this.green) != Double.doubleToLongBits(other.green)) return false;
		if (Double.doubleToLongBits(this.blue) != Double.doubleToLongBits(other.blue)) return false;
		return Double.doubleToLongBits(this.alpha) == Double.doubleToLongBits(other.alpha);
	}

	@Override
	public String toString() {
		return "Color[" + (name == null ? "" : "name=" + name + ",")
				+ "r=" + Math.round(red) + ",g=" + Math.round(green) + ",b=" + Math.round(blue)
				+ (alpha == 256 ? "" : ",a=" + Math.round(alpha)) + ",hexa=" + toHexString() + "]";
	}

}

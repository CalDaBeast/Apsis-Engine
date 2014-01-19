package com.apsis.util;

/**
 * A secondary Color representation
 * using the HSV (Hue, Saturation, Value) model.
 *
 * Uses RGB to HSV and HSV to RGB algorithms from
 * <a href=http://www.cs.rit.edu/~ncs/color/t_convert.html>this site</a>.
 *
 * @author Cal Stephens
 */
public class ColorHSV {

	private double hue;
	private double saturation;
	private double value;
	private double alpha;

	/**
	 * Creates an HSV-modeled color based on an RGB representation
	 * Translated into Java from
	 * <a href=http://www.cs.rit.edu/~ncs/color/t_convert.html>these C algorithms</a>.
	 *
	 * @param rgb
	 */
	public ColorHSV(Color rgb) {
		setAlpha(rgb.getAlpha());
		//convert RGB to values between 0 and 1
		double red = rgb.getRed() / 255;
		double green = rgb.getRed() / 255;
		double blue = rgb.getRed() / 255;

		double min, max, delta;
		min = Math.min(red, Math.min(green, blue));
		max = Math.max(red, Math.min(green, blue));
		setValue(max);
		delta = max - min;

		if (max != 0) saturation = delta / max;
		else {
			setSaturation(0);
			setHue(-1);
			return;
		}

		double hue;
		if (red == max) hue = (green - blue) / delta;		   // between yellow and magenta
		else if (green == max) hue = 2 + (blue - red) / delta; // between cyan and yellow
		else hue = 4 + (red - green) / delta;	               // between magenta and cyan
		setHue(hue * 60);
	}

	/**
	 * The Alpha of the color will be assumed as 255 (fully opaque);
	 *
	 * @param hue the Hue component of the color
	 * @param saturation the Saturation component of the color
	 * @param value the Value component of the color
	 */
	public ColorHSV(double hue, double saturation, double value) {
		this(hue, saturation, value, 255);
	}

	/**
	 * @param hue the Hue component of the color
	 * @param saturation the Saturation component of the color
	 * @param value the Value component of the color
	 * @param alpha the Alpha component of the color
	 */
	public ColorHSV(double hue, double saturation, double value, double alpha) {
		setHue(hue);
		setSaturation(saturation);
		setValue(value);
		setAlpha(alpha);
	}

	/**
	 * @return the Hue component of the color
	 */
	public double getHue() {
		return hue;
	}

	/**
	 * Set the Hue component of the color.
	 * Will be wrapped to within 0 and 360 if not already.
	 * EX: <i>370 -> 10, -10 -> 350</i>
	 *
	 * Can be -1 is the saturation is 0.
	 *
	 * @param hue the new Hue component of the color
	 */
	public final void setHue(double hue) {
		if (hue == -1) {
			if (saturation != 0) {
				this.hue = -1;
				return;
			} else throw new IllegalArgumentException("The Hue of a color can only be -1 if the Saturation is 0");
		}
		if (hue <= 0) hue = 360 * -((int) hue / 360) + (360 - Math.abs(hue));
		if (hue > 360) hue -= ((int) hue / 360) * 360;
		this.hue = hue;
	}

	/**
	 * @return the Saturation component of the color
	 */
	public double getSaturation() {
		return saturation;
	}

	/**
	 * Set the Saturation component of the color.
	 * Will be set to 1 is the input value is greater than 1
	 * and set to 0 if the input value is less than 0
	 *
	 * Must be 0 if the hue is -1.
	 *
	 * @param saturation the new Saturation component of the color
	 */
	public final void setSaturation(double saturation) {
		if (saturation <= 0) {
			if (hue != -1) {
				this.saturation = 0;
				return;
			} else throw new IllegalArgumentException("The Saturation must be 0 if the hue is -1");
		}
		if (saturation > 1) saturation = 1;
		else if (saturation < 0) saturation = 0;
		this.saturation = saturation;
	}

	/**
	 * @return the Value (brightness) component of the color
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Set the Value (brightness) component of the color.
	 * Will be set to 1 if the input value is greater than 1
	 * and 0 if the input value is less than 0.
	 *
	 * @param value the new Value (brightness) component of the color
	 */
	public final void setValue(double value) {
		if (value > 1) value = 1;
		else if (value < 0) value = 0;
		this.value = value;
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
	 * Converts the color from the HSV model to the RGB model.
	 * Translated into Java from
	 * <a href=http://www.cs.rit.edu/~ncs/color/t_convert.html>these C algorithms</a>.
	 *
	 * @return the color in RGB
	 */
	public Color inRGB() {
		Color rgb = new Color(0, 0, 0);
		int i;
		double f, p, q, t;

		if (saturation == 0) {
			rgb.setAll(value * 255);
			return rgb;
		}

		i = (int) Math.floor(hue / 60);
		f = hue - i; // factorial part of h
		p = value * (1 - saturation);
		q = value * (1 - saturation * f);
		t = value * (1 - saturation * (1 - f));

		switch (i) {
			case 0:
				rgb.setRed(value);
				rgb.setGreen(t);
				rgb.setBlue(p);
				break;
			case 1:
				rgb.setRed(q);
				rgb.setGreen(value);
				rgb.setBlue(p);
				break;
			case 2:
				rgb.setRed(p);
				rgb.setGreen(value);
				rgb.setBlue(t);
				break;
			case 3:
				rgb.setRed(p);
				rgb.setGreen(q);
				rgb.setBlue(value);
				break;
			case 4:
				rgb.setRed(t);
				rgb.setGreen(p);
				rgb.setBlue(value);
				break;
			default: // case 5:
				rgb.setRed(value);
				rgb.setGreen(p);
				rgb.setBlue(q);
				break;
		}
		return new Color(rgb.getRed() * 255, rgb.getGreen() * 255, rgb.getBlue() * 255, this.getAlpha());
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 53 * hash + (int) (Double.doubleToLongBits(this.hue) ^ (Double.doubleToLongBits(this.hue) >>> 32));
		hash = 53 * hash + (int) (Double.doubleToLongBits(this.saturation) ^ (Double.doubleToLongBits(this.saturation) >>> 32));
		hash = 53 * hash + (int) (Double.doubleToLongBits(this.value) ^ (Double.doubleToLongBits(this.value) >>> 32));
		hash = 53 * hash + (int) (Double.doubleToLongBits(this.alpha) ^ (Double.doubleToLongBits(this.alpha) >>> 32));
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final ColorHSV other = (ColorHSV) obj;
		if (Double.doubleToLongBits(this.hue) != Double.doubleToLongBits(other.hue)) return false;
		if (Double.doubleToLongBits(this.saturation) != Double.doubleToLongBits(other.saturation)) return false;
		return Double.doubleToLongBits(this.value) == Double.doubleToLongBits(other.value);
	}

	

	@Override
	public String toString() {
		return "Color[h=" + Math.round(hue) + ",s=" + saturation + ",v=" + value
				+ (alpha == 256 ? "" : ",a=" + Math.round(alpha)) + "]";
	}

}

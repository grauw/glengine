package nl.grauw.glengine.model;

import nl.grauw.glengine.gl.GLEngineException;

public class Color {
	
	private final float red, green, blue, alpha;
	
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color RED = new Color(1, 0, 0);
	public static final Color GREEN = new Color(0, 1, 0);
	public static final Color BLUE = new Color(0, 0, 1);
	public static final Color CYAN = new Color(0, 1, 1);
	public static final Color MAGENTA = new Color(1, 0, 1);
	public static final Color YELLOW = new Color(1, 1, 0);
	public static final Color WHITE = new Color(1, 1, 1);
	
	public Color(final float red, final float green, final float blue) {
		this(red, green, blue, 1);
	}
	
	public Color(final float red, final float green, final float blue, final float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public float getRed() {
		return red;
	}
	
	public float getGreen() {
		return green;
	}
	
	public float getBlue() {
		return blue;
	}
	
	public float getAlpha() {
		return alpha;
	}
	
	public float getLuminance() {
		return (red + green + blue) / 3;
	}
	
	public Color darker() {
		return darker(0.5f);
	}
	
	public Color darker(final float percentage) {
		if (percentage <= 0f || percentage >= 1f)
			throw new GLEngineException("Percentage must be between 0 and 1.");
		
		return new Color(red * percentage, green * percentage, blue * percentage, alpha);
	}
	
	public Color lighter() {
		return lighter(0.5f);
	}
	
	public Color lighter(final float percentage) {
		if (percentage <= 0f || percentage >= 1f)
			throw new GLEngineException("Percentage must be between 0 and 1.");
		
		return new Color(1f - (1f - red) * percentage, 1f - (1f - green) * percentage, 1f - (1f - blue) * percentage, alpha);
	}
	
}

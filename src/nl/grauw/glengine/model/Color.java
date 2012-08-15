package nl.grauw.glengine.model;

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
	
	public Color(float r, float g, float b) {
		this(r, g, b, 1);
	}
	
	public Color(float r, float g, float b, float a) {
		this.red = r;
		this.green = g;
		this.blue = b;
		this.alpha = a;
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
	
	public Color darker() {
		return darker(0.5f);
	}
	
	public Color darker(float percentage) {
		if (percentage <= 0f || percentage >= 1f)
			throw new RuntimeException("Percentage must be between 0 and 1.");
		
		return new Color(red * percentage, green * percentage, blue * percentage, alpha);
	}
	
	public Color lighter() {
		return lighter(0.5f);
	}
	
	public Color lighter(float percentage) {
		if (percentage <= 0f || percentage >= 1f)
			throw new RuntimeException("Percentage must be between 0 and 1.");
		
		return new Color(1f - (1f - red) * percentage, 1f - (1f - green) * percentage, 1f - (1f - blue) * percentage, alpha);
	}
	
}

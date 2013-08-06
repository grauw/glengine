package nl.grauw.glengine.model;

public class Size {
	
	private final float width, height, depth;
	
	public Size(float width, float height) {
		this(width, height, 0);
	}
	
	public Size(float width, float height, float depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getDepth() {
		return depth;
	}
	
}

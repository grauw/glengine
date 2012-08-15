package nl.grauw.glengine.model;

public class Size {
	
	private final float width, height, depth;
	
	public Size(final float width, final float height) {
		this(width, height, 0);
	}
	
	public Size(final float width, final float height, final float depth) {
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

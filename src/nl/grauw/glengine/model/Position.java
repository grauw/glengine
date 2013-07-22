package nl.grauw.glengine.model;

public class Position {
	
	private final float x, y, z;
	
	public Position(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}
	
}

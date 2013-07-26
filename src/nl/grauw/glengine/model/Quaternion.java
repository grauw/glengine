package nl.grauw.glengine.model;

public class Quaternion {
	
	private final float x;
	private final float y;
	private final float z;
	private final float w;
	
	public Quaternion() {
		x = y = z = 0;
		w = 1;
	}
	
	public Quaternion(float x, float y, float z, float angle) {
		float sinAngle2 = (float)Math.sin(angle / 2);
		this.x = x * sinAngle2;
		this.y = y * sinAngle2;
		this.z = z * sinAngle2;
		this.w = (float)Math.cos(angle / 2);
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
	
	public float getW() {
		return w;
	}
	
}

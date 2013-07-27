package nl.grauw.glengine.model;

public class Quaternion {
	
	public static final Quaternion IDENTITY = new Identity();
	
	private final float x;
	private final float y;
	private final float z;
	private final float w;
	
	private Quaternion(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
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
	
	public Quaternion multiply(Quaternion other) {
		if (other == IDENTITY)
			return this;
		return new Quaternion(
			w * other.x + x * other.w + y * other.z - z * other.y,
			w * other.y + y * other.w + z * other.x - x * other.z,
			w * other.z + z * other.w + x * other.y - y * other.x,
			w * other.w - x * other.x - y * other.y - z * other.z
		);
	}
	
	public static class Axis extends Quaternion {
		
		public Axis(float x, float y, float z, float angle) {
			this(x, y, z, (float)Math.sin(angle / 2), (float)Math.cos(angle / 2));
		}
		
		private Axis(float x, float y, float z, float sinAngle2, float cosAngle2) {
			super(x * sinAngle2, y * sinAngle2, z * sinAngle2, cosAngle2);
		}
		
	}
	
	private static final class Identity extends Quaternion {
		
		public Identity() {
			super(0, 0, 0, 1);
		}
		
		@Override
		public Quaternion multiply(Quaternion other) {
			return other;
		}
		
	}
	
}

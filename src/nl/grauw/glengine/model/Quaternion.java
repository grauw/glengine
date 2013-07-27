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
		
	}
	
}

package nl.grauw.glengine.model;

import android.util.FloatMath;

public class Vector {
	
	private final float x, y, z;
	
	public static final Vector ZERO = new Vector(0, 0, 0);
	
	public Vector(final float x, final float y, final float z) {
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
	
	public float getLength() {
		return FloatMath.sqrt(x * x + y * y + z * z);
	}
	
	public Vector add(final Vector other) {
		return new Vector(x + other.x, y + other.y, z + other.z);
	}
	
	public Vector subtract(final Vector other) {
		return new Vector(x - other.x, y - other.y, z - other.z);
	}
	
	public Vector scale(final float scalar) {
		return new Vector(x * scalar, y * scalar, z * scalar);
	}
	
	public Vector normalize() {
		float length = FloatMath.sqrt(x * x + y * y + z * z);
		if (length == 0)
			return ZERO;
		return new Vector(x / length, y / length, z / length);
	}
	
	public Vector cross(final Vector other) {
		return new Vector(
				y * other.z - z * other.y,
				z * other.x - x * other.z,
				x * other.y - y * other.x
			);
	}
	
	public Vector normalizedCross(final Vector other) {
		float x = this.y * other.z - this.z * other.y;
		float y = this.z * other.x - this.x * other.z;
		float z = this.x * other.y - this.y * other.x;
		float length = FloatMath.sqrt(x * x + y * y + z * z);
		if (length == 0)
			return ZERO;
		return new Vector(x / length, y / length, z / length);
	}
	
}

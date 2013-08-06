package nl.grauw.glengine.model;

import java.nio.ByteBuffer;

public class Vector3 implements VertexAttribute {
	
	private final float x, y, z;
	
	public static final Zero ZERO = new Zero();
	public static final One ONE = new One();
	
	public static final AttributeInfo attributeInfo = new AttributeInfo(3, AttributeType.FLOAT);
	
	public Vector3(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(Vector2 vector) {
		this(vector.getX(), vector.getY(), 0);
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
		return (float)Math.sqrt(x * x + y * y + z * z);
	}
	
	public Vector3 add(final Vector3 other) {
		if (other == ZERO)
			return this;
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}
	
	public Vector3 subtract(final Vector3 other) {
		if (other == ZERO)
			return this;
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}
	
	public Vector3 multiply(final Vector3 other) {
		if (other == ONE)
			return this;
		if (other == ZERO)
			return ZERO;
		return new Vector3(x * other.x, y * other.y, z * other.z);
	}
	
	public Vector3 multiply(final float scalar) {
		if (scalar == 1)
			return this;
		return new Vector3(x * scalar, y * scalar, z * scalar);
	}
	
	public Vector3 normalize() {
		float length = (float)Math.sqrt(x * x + y * y + z * z);
		if (length == 0)
			return ZERO;
		return new Vector3(x / length, y / length, z / length);
	}
	
	public Vector3 cross(final Vector3 other) {
		return new Vector3(
			y * other.z - z * other.y,
			z * other.x - x * other.z,
			x * other.y - y * other.x
		);
	}
	
	public float dot(final Vector3 other) {
		return x * other.x + y * other.y + z * other.z;
	}
	
	public Vector3 interpolate(final Vector3 other, final Vector3 weight) {
		return new Vector3(
			x + (other.x - x) * weight.x,
			y + (other.y - y) * weight.y,
			z + (other.z - z) * weight.z
		);
	}
	
	public Vector3 interpolate(final Vector3 other, final float weight) {
		return new Vector3(
			x + (other.x - x) * weight,
			y + (other.y - y) * weight,
			z + (other.z - z) * weight
		);
	}
	
	@Override
	public void write(ByteBuffer buffer) {
		buffer.putFloat(x);
		buffer.putFloat(y);
		buffer.putFloat(z);
	}
	
	@Override
	public AttributeInfo getAttributeInfo() {
		return attributeInfo;
	}
	
	private static final class Zero extends Vector3 {
		
		public Zero() {
			super(0, 0, 0);
		}
		
		@Override
		public Vector3 add(Vector3 other) {
			return other;
		}
		
		@Override
		public Vector3 multiply(Vector3 other) {
			return this;
		}
		
	}
	
	private static final class One extends Vector3 {
		
		public One() {
			super(1, 1, 1);
		}
		
		@Override
		public Vector3 multiply(Vector3 other) {
			return other;
		}
		
	}
	
}

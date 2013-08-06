package nl.grauw.glengine.model;

import java.nio.ByteBuffer;

public class Vector4 implements VertexAttribute {
	
	private final float x, y, z, w;
	
	public static final Zero ZERO = new Zero();
	public static final One ONE = new One();
	public static final Origin ORIGIN = new Origin();
	
	public static final AttributeInfo attributeInfo = new AttributeInfo(4, AttributeType.FLOAT);
	
	public Vector4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4(float scalar) {
		this(scalar, scalar, scalar, scalar);
	}
	
	public Vector4(Vector2 vector, float z, float w) {
		this(vector.getX(), vector.getY(), z, w);
	}
	
	public Vector4(float x, Vector2 vector, float w) {
		this(x, vector.getX(), vector.getY(), w);
	}
	
	public Vector4(float x, float y, Vector2 vector) {
		this(x, y, vector.getX(), vector.getY());
	}
	
	public Vector4(Vector3 vector, float w) {
		this(vector.getX(), vector.getY(), vector.getZ(), w);
	}
	
	public Vector4(float x, Vector3 vector) {
		this(x, vector.getX(), vector.getY(), vector.getZ());
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
	
	public float getLength() {
		return (float)Math.sqrt(x * x + y * y + z * z + w * w);
	}
	
	public float getLengthSquared() {
		return x * x + y * y + z * z + w * w;
	}
	
	public Vector4 add(Vector4 other) {
		if (other == ZERO)
			return this;
		return new Vector4(x + other.x, y + other.y, z + other.z, w + other.w);
	}
	
	public Vector4 subtract(Vector4 other) {
		if (other == ZERO)
			return this;
		return new Vector4(x - other.x, y - other.y, z - other.z, w - other.w);
	}
	
	public Vector4 multiply(Vector4 other) {
		if (other == ONE)
			return this;
		if (other == ZERO)
			return ZERO;
		return new Vector4(x * other.x, y * other.y, z * other.z, w * other.w);
	}
	
	public Vector4 multiply(float scalar) {
		if (scalar == 1)
			return this;
		return new Vector4(x * scalar, y * scalar, z * scalar, w * scalar);
	}
	
	public Vector4 normalize() {
		float length = (float)Math.sqrt(x * x + y * y + z * z + w * w);
		if (length == 0)
			return ZERO;
		return new Vector4(x / length, y / length, z / length, w / length);
	}
	
	public float dot(Vector4 other) {
		return x * other.x + y * other.y + z * other.z + w * other.w;
	}
	
	public Vector4 interpolate(Vector4 other, Vector4 weight) {
		return new Vector4(
			x + (other.x - x) * weight.x,
			y + (other.y - y) * weight.y,
			z + (other.z - z) * weight.z,
			w + (other.w - w) * weight.w
		);
	}
	
	public Vector4 interpolate(Vector4 other, float weight) {
		return new Vector4(
			x + (other.x - x) * weight,
			y + (other.y - y) * weight,
			z + (other.z - z) * weight,
			w + (other.w - w) * weight
		);
	}
	
	@Override
	public void write(ByteBuffer buffer) {
		buffer.putFloat(x);
		buffer.putFloat(y);
		buffer.putFloat(z);
		buffer.putFloat(w);
	}
	
	@Override
	public AttributeInfo getAttributeInfo() {
		return attributeInfo;
	}
	
	private static final class Zero extends Vector4 {
		
		public Zero() {
			super(0, 0, 0, 0);
		}
		
		@Override
		public Vector4 add(Vector4 other) {
			return other;
		}
		
		@Override
		public Vector4 multiply(Vector4 other) {
			return this;
		}
		
	}
	
	private static final class One extends Vector4 {
		
		public One() {
			super(1, 1, 1, 1);
		}
		
		@Override
		public Vector4 multiply(Vector4 other) {
			return other;
		}
		
	}
	
	private static final class Origin extends Vector4 {
		
		public Origin() {
			super(0, 0, 0, 1);
		}
		
		@Override
		public Vector4 normalize() {
			return this;
		}
		
	}
	
}

package nl.grauw.glengine.model;

import java.nio.ByteBuffer;

import nl.grauw.glengine.model.VertexArray.AttributeInfo;
import nl.grauw.glengine.model.VertexArray.AttributeType;
import nl.grauw.glengine.model.VertexArray.VertexAttribute;

public class Vector implements VertexAttribute {
	
	private final float x, y, z;
	
	public static final Zero ZERO = new Zero();
	public static final One ONE = new One();
	
	public static final AttributeInfo attributeInfo = new AttributeInfo(3, AttributeType.FLOAT);
	
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
		return (float)Math.sqrt(x * x + y * y + z * z);
	}
	
	public Vector add(final Vector other) {
		if (other == ZERO)
			return this;
		return new Vector(x + other.x, y + other.y, z + other.z);
	}
	
	public Vector subtract(final Vector other) {
		if (other == ZERO)
			return this;
		return new Vector(x - other.x, y - other.y, z - other.z);
	}
	
	public Vector multiply(final Vector other) {
		if (other == ONE)
			return this;
		if (other == ZERO)
			return ZERO;
		return new Vector(x * other.x, y * other.y, z * other.z);
	}
	
	public Vector multiply(final float scalar) {
		if (scalar == 1)
			return this;
		return new Vector(x * scalar, y * scalar, z * scalar);
	}
	
	public Vector normalize() {
		float length = (float)Math.sqrt(x * x + y * y + z * z);
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
	
	public float dot(final Vector other) {
		return x * other.x + y * other.y + z * other.z;
	}
	
	public Vector interpolate(final Vector other, final Vector weight) {
		return new Vector(
			x + (other.x - x) * weight.x,
			y + (other.y - y) * weight.y,
			z + (other.z - z) * weight.z
		);
	}
	
	public Vector interpolate(final Vector other, final float weight) {
		return new Vector(
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
	
	private static final class Zero extends Vector {
		
		public Zero() {
			super(0, 0, 0);
		}
		
		@Override
		public Vector add(Vector other) {
			return other;
		}
		
		@Override
		public Vector multiply(Vector other) {
			return this;
		}
		
	}
	
	private static final class One extends Vector {
		
		public One() {
			super(1, 1, 1);
		}
		
		@Override
		public Vector multiply(Vector other) {
			return other;
		}
		
	}
	
}

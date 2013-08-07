package nl.grauw.glengine.model;

public class Vector2 {
	
	private final float x, y;
	
	public static final Zero ZERO = new Zero();
	public static final One ONE = new One();
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2(float scalar) {
		this(scalar, scalar);
	}
	
	public Vector2(Vector3 vector) {
		this(vector.getX(), vector.getY());
	}
	
	public Vector2(Vector4 vector) {
		this(vector.getX(), vector.getY());
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getLength() {
		return (float)Math.sqrt(x * x + y * y);
	}
	
	public float getLengthSquared() {
		return x * x + y * y;
	}
	
	public Vector2 add(Vector2 other) {
		if (other == ZERO)
			return this;
		return new Vector2(x + other.x, y + other.y);
	}
	
	public Vector2 subtract(Vector2 other) {
		if (other == ZERO)
			return this;
		return new Vector2(x - other.x, y - other.y);
	}
	
	public Vector2 multiply(Vector2 other) {
		if (other == ONE)
			return this;
		if (other == ZERO)
			return ZERO;
		return new Vector2(x * other.x, y * other.y);
	}
	
	public Vector2 multiply(float scalar) {
		if (scalar == 1)
			return this;
		return new Vector2(x * scalar, y * scalar);
	}
	
	public Vector2 normalize() {
		float length = (float)Math.sqrt(x * x + y * y);
		if (length == 0)
			return ZERO;
		return new Vector2(x / length, y / length);
	}
	
	public float dot(Vector2 other) {
		return x * other.x + y * other.y;
	}
	
	public Vector2 interpolate(Vector2 other, Vector2 weight) {
		return new Vector2(
			x + (other.x - x) * weight.x,
			y + (other.y - y) * weight.y
		);
	}
	
	public Vector2 interpolate(Vector2 other, float weight) {
		return new Vector2(
			x + (other.x - x) * weight,
			y + (other.y - y) * weight
		);
	}
	
	public boolean equals(Vector2 other) {
		return x == other.x && y == other.y;
	}
	
	private static final class Zero extends Vector2 {
		
		public Zero() {
			super(0, 0);
		}
		
		@Override
		public Vector2 add(Vector2 other) {
			return other;
		}
		
		@Override
		public Vector2 multiply(Vector2 other) {
			return this;
		}
		
	}
	
	private static final class One extends Vector2 {
		
		public One() {
			super(1, 1);
		}
		
		@Override
		public Vector2 multiply(Vector2 other) {
			return other;
		}
		
	}
	
}

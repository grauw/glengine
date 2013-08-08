package nl.grauw.glengine.model;

public class Vector2 extends Matrix {
	
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
	
	public final float getX() {
		return x;
	}
	
	public final float getY() {
		return y;
	}
	
	@Override
	public final int getColumns() {
		return 1;
	}
	
	@Override
	public final int getRows() {
		return 2;
	}
	
	@Override
	public final float[] getElements() {
		return new float[] { x, y };
	}
	
	public final float getLength() {
		return (float)Math.sqrt(x * x + y * y);
	}
	
	public final float getLengthSquared() {
		return x * x + y * y;
	}
	
	public Vector2 add(Vector2 other) {
		if (other == ZERO)
			return this;
		return new Vector2(x + other.x, y + other.y);
	}
	
	public final Vector2 add(float otherX, float otherY) {
		if (otherX == 0 && otherY == 0)
			return this;
		return new Vector2(x + otherX, y + otherY);
	}
	
	public final Vector2 subtract(Vector2 other) {
		if (other == ZERO)
			return this;
		return new Vector2(x - other.x, y - other.y);
	}
	
	public final Vector2 subtract(float otherX, float otherY) {
		if (otherX == 0 && otherY == 0)
			return this;
		return new Vector2(x - otherX, y - otherY);
	}
	
	public Vector2 negate() {
		return new Vector2(-x, -y);
	}
	
	public Vector2 multiply(Vector2 other) {
		if (other == ONE)
			return this;
		if (other == ZERO)
			return other;
		return new Vector2(x * other.x, y * other.y);
	}
	
	public final Vector2 multiply(float otherX, float otherY) {
		if (otherX == 1 && otherY == 1)
			return this;
		if (otherX == 0 && otherY == 0)
			return ZERO;
		return new Vector2(x * otherX, y * otherY);
	}
	
	public final Vector2 multiply(float scalar) {
		if (scalar == 1)
			return this;
		return new Vector2(x * scalar, y * scalar);
	}
	
	public final float dot(Vector2 other) {
		return x * other.x + y * other.y;
	}
	
	public Vector2 invert() {
		return new Vector2(1 / x, 1 / y);
	}
	
	public final Vector2 normalize() {
		float length = (float)Math.sqrt(x * x + y * y);
		if (length == 0)
			return ZERO;
		return new Vector2(x / length, y / length);
	}
	
	public final Vector2 interpolate(Vector2 other, Vector2 weight) {
		return new Vector2(
			x + (other.x - x) * weight.x,
			y + (other.y - y) * weight.y
		);
	}
	
	public final Vector2 interpolate(Vector2 other, float weight) {
		return new Vector2(
			x + (other.x - x) * weight,
			y + (other.y - y) * weight
		);
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof Vector2 ? equals((Vector2)other) : super.equals(other);
	}
	
	public final boolean equals(Vector2 other) {
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
		public Vector2 negate() {
			return this;
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
		
		@Override
		public Vector2 invert() {
			return this;
		}
		
	}
	
}

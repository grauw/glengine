package nl.grauw.glengine.model;

public class Vector3 extends Matrix {
	
	private final float x, y, z;
	
	public static final Zero ZERO = new Zero();
	public static final One ONE = new One();
	
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(float scalar) {
		this(scalar, scalar, scalar);
	}
	
	public Vector3(Vector2 vector, float z) {
		this(vector.getX(), vector.getY(), z);
	}
	
	public Vector3(float x, Vector2 vector) {
		this(x, vector.getX(), vector.getY());
	}
	
	public Vector3(Vector4 vector) {
		this(vector.getX(), vector.getY(), vector.getZ());
	}
	
	public final float getX() {
		return x;
	}
	
	public final float getY() {
		return y;
	}
	
	public final float getZ() {
		return z;
	}
	
	@Override
	public final int getColumns() {
		return 1;
	}
	
	@Override
	public final int getRows() {
		return 3;
	}
	
	@Override
	public final float[] getElements() {
		return new float[] { x, y, z };
	}
	
	public final float getLength() {
		return (float)Math.sqrt(x * x + y * y + z * z);
	}
	
	public float getLengthSquared() {
		return x * x + y * y + z * z;
	}
	
	public Vector3 add(Vector3 other) {
		if (other == ZERO)
			return this;
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}
	
	public final Vector3 subtract(Vector3 other) {
		if (other == ZERO)
			return this;
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}
	
	public Vector3 negate() {
		return new Vector3(-x, -y, -z);
	}
	
	public Vector3 multiply(Vector3 other) {
		if (other == ONE)
			return this;
		if (other == ZERO)
			return ZERO;
		return new Vector3(x * other.x, y * other.y, z * other.z);
	}
	
	public final Vector3 multiply(float scalar) {
		if (scalar == 1)
			return this;
		return new Vector3(x * scalar, y * scalar, z * scalar);
	}
	
	public final Vector3 cross(Vector3 other) {
		return new Vector3(
			y * other.z - z * other.y,
			z * other.x - x * other.z,
			x * other.y - y * other.x
		);
	}
	
	public final float dot(Vector3 other) {
		return x * other.x + y * other.y + z * other.z;
	}
	
	public Vector3 invert() {
		return new Vector3(1 / x, 1 / y, 1 / z);
	}
	
	public final Vector3 normalize() {
		float length = (float)Math.sqrt(x * x + y * y + z * z);
		if (length == 0)
			return ZERO;
		return new Vector3(x / length, y / length, z / length);
	}
	
	public final Vector3 interpolate(Vector3 other, Vector3 weight) {
		return new Vector3(
			x + (other.x - x) * weight.x,
			y + (other.y - y) * weight.y,
			z + (other.z - z) * weight.z
		);
	}
	
	public final Vector3 interpolate(Vector3 other, float weight) {
		return new Vector3(
			x + (other.x - x) * weight,
			y + (other.y - y) * weight,
			z + (other.z - z) * weight
		);
	}
	
	public final boolean equals(Vector3 other) {
		return x == other.x && y == other.y && z == other.z;
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
		public Vector3 negate() {
			return this;
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
		
		@Override
		public Vector3 invert() {
			return this;
		}
		
	}
	
}

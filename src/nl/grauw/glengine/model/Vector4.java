package nl.grauw.glengine.model;

public class Vector4 extends Matrix {
	
	private final float x, y, z, w;
	
	public static final Zero ZERO = new Zero();
	public static final One ONE = new One();
	public static final Origin ORIGIN = new Origin();
	
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
	
	public final float getX() {
		return x;
	}
	
	public final float getY() {
		return y;
	}
	
	public final float getZ() {
		return z;
	}
	
	public final float getW() {
		return w;
	}
	
	@Override
	public final int getColumns() {
		return 1;
	}
	
	@Override
	public final int getRows() {
		return 4;
	}
	
	@Override
	public final float[] getElements() {
		return new float[] { x, y, z, w };
	}
	
	public final float getLength() {
		return (float)Math.sqrt(x * x + y * y + z * z + w * w);
	}
	
	public final float getLengthSquared() {
		return x * x + y * y + z * z + w * w;
	}
	
	public Vector4 add(Vector4 other) {
		if (other == ZERO)
			return this;
		return new Vector4(x + other.x, y + other.y, z + other.z, w + other.w);
	}
	
	public final Vector4 add(float otherX, float otherY, float otherZ, float otherW) {
		if (otherX == 0 && otherY == 0 && otherZ == 0 && otherW == 0)
			return this;
		return new Vector4(x + otherX, y + otherY, z + otherZ, w + otherW);
	}
	
	public final Vector4 subtract(Vector4 other) {
		if (other == ZERO)
			return this;
		return new Vector4(x - other.x, y - other.y, z - other.z, w - other.w);
	}
	
	public final Vector4 subtract(float otherX, float otherY, float otherZ, float otherW) {
		if (otherX == 0 && otherY == 0 && otherZ == 0 && otherW == 0)
			return this;
		return new Vector4(x - otherX, y - otherY, z - otherZ, w - otherW);
	}
	
	public Vector4 negate() {
		return new Vector4(-x, -y, -z, -w);
	}
	
	public Vector4 multiply(Vector4 other) {
		if (other == ONE)
			return this;
		if (other == ZERO)
			return other;
		return new Vector4(x * other.x, y * other.y, z * other.z, w * other.w);
	}
	
	public final Vector4 multiply(float otherX, float otherY, float otherZ, float otherW) {
		if (otherX == 1 && otherY == 1 && otherZ == 1 && otherW == 1)
			return this;
		if (otherX == 0 && otherY == 0 && otherZ == 0 && otherW == 0)
			return ZERO;
		return new Vector4(x * otherX, y * otherY, z * otherZ, w * otherW);
	}
	
	public final Vector4 multiply(float scalar) {
		if (scalar == 1)
			return this;
		return new Vector4(x * scalar, y * scalar, z * scalar, w * scalar);
	}
	
	public final float dot(Vector4 other) {
		return x * other.x + y * other.y + z * other.z + w * other.w;
	}
	
	public Vector4 invert() {
		return new Vector4(1 / x, 1 / y, 1 / z, 1 / w);
	}
	
	public Vector4 normalize() {
		float length = (float)Math.sqrt(x * x + y * y + z * z + w * w);
		if (length == 0)
			return ZERO;
		return new Vector4(x / length, y / length, z / length, w / length);
	}
	
	public final Vector4 interpolate(Vector4 other, Vector4 weight) {
		return new Vector4(
			x + (other.x - x) * weight.x,
			y + (other.y - y) * weight.y,
			z + (other.z - z) * weight.z,
			w + (other.w - w) * weight.w
		);
	}
	
	public final Vector4 interpolate(Vector4 other, float weight) {
		return new Vector4(
			x + (other.x - x) * weight,
			y + (other.y - y) * weight,
			z + (other.z - z) * weight,
			w + (other.w - w) * weight
		);
	}
	
	public final boolean equals(Vector4 other) {
		return x == other.x && y == other.y && z == other.z && w == other.w;
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
		public Vector4 negate() {
			return this;
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
		
		@Override
		public Vector4 invert() {
			return this;
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

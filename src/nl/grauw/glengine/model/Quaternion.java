package nl.grauw.glengine.model;

public class Quaternion {
	
	public static final Identity IDENTITY = new Identity();
	
	private final float x;
	private final float y;
	private final float z;
	private final float w;
	
	public Quaternion(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
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
	
	public final float getMagnitude() {
		return (float)Math.sqrt(x * x + y * y + z * z + w * w);
	}
	
	public final float getMagnitudeSquared() {
		return x * x + y * y + z * z + w * w;
	}
	
	/** Returns a unit quaternion (versor). */
	public Quaternion normalize() {
		float magnitude = getMagnitude();
		return new Quaternion(x / magnitude, y / magnitude, z / magnitude, w / magnitude);
	}
	
	/** Inverts the rotation. */
	public Quaternion conjugate() {
		return new Quaternion(-x, -y, -z, w);
	}
	
	public Quaternion reciprocal() {
		float length = x * x + y * y + z * z + w * w;
		return new Quaternion(-x / length, -y / length, -z / length, w / length);
	}
	
	public double getAngle() {
		return Math.acos(w) * 2;
	}
	
	public Vector3 getAxis() {
		float sinAngle = (float)Math.sqrt(1 - w * w);
		if (Math.abs(sinAngle) < 0.0005f)
			sinAngle = 1;
		return new Vector3(x / sinAngle, y / sinAngle, z / sinAngle);
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
	
	/** Note that you probably want to use a unit quaternion. */
	public Vector3 multiply(Vector3 vector) {
		if (vector == Vector3.ZERO)
			return vector;

		float vx = vector.getX(), vy = vector.getY(), vz = vector.getZ();
		float tx = 2 * (y * vz - z * vy);
		float ty = 2 * (z * vx - x * vz);
		float tz = 2 * (x * vy - y * vx);
		
		return new Vector3(
			vx + w * tx + y * tz - z * ty,
			vy + w * ty + z * tx - x * tz,
			vz + w * tz + x * ty - y * tx
		);
	}
	
	/** Note that you probably want to use a unit quaternion. */
	public Vector4 multiply(Vector4 vector) {
		if (vector == Vector4.ZERO || vector == Vector4.ORIGIN)
			return vector;
		
		float vx = vector.getX(), vy = vector.getY(), vz = vector.getZ();
		float tx = 2 * (y * vz - z * vy);
		float ty = 2 * (z * vx - x * vz);
		float tz = 2 * (x * vy - y * vx);
		
		return new Vector4(
			vx + w * tx + y * tz - z * ty,
			vy + w * ty + z * tx - x * tz,
			vz + w * tz + x * ty - y * tx,
			vector.getW()
		);
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof Quaternion ? equals((Quaternion)other) : super.equals(other);
	}
	
	public final boolean equals(Quaternion other) {
		return x == other.x && y == other.y && z == other.z && w == other.w;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + x + ", " + y + ", " + z + ", " + w + "]";
	}
	
	public static final class Axis extends Quaternion {
		
		public Axis(Vector3 vector, double angle) {
			this(vector.getX(), vector.getY(), vector.getZ(), angle);
		}
		
		public Axis(float x, float y, float z, double angle) {
			this(x, y, z, (float)Math.sin(angle / 2), (float)Math.cos(angle / 2));
		}
		
		private Axis(float x, float y, float z, float sinAngle2, float cosAngle2) {
			super(x * sinAngle2, y * sinAngle2, z * sinAngle2, cosAngle2);
		}
		
	}
	
	public static final class EulerXYZ extends Quaternion {
		
		public EulerXYZ(Vector3 angles) {
			this(angles.getX(), angles.getY(), angles.getZ());
		}
		
		public EulerXYZ(double x, double y, double z) {
			this(
				(float)Math.sin(x / 2), (float)Math.cos(x / 2),
				(float)Math.sin(y / 2), (float)Math.cos(y / 2),
				(float)Math.sin(z / 2), (float)Math.cos(z / 2)
			);
		}
		
		private EulerXYZ(float sx, float cx, float sy, float cy, float sz, float cz) {
			super(
				sx * cy * cz + cx * sy * sz,
				cx * sy * cz - sx * cy * sz,
				cx * cy * sz + sx * sy * cz,
				cx * cy * cz - sx * sy * sz
			);
		}
		
	}
	
	public static final class Identity extends Quaternion {
		
		private Identity() {
			super(0, 0, 0, 1);
		}
		
		@Override
		public Quaternion multiply(Quaternion other) {
			return other;
		}
		
		@Override
		public Vector3 multiply(Vector3 vector) {
			return vector;
		}
		
		@Override
		public Vector4 multiply(Vector4 vector) {
			return vector;
		}
		
		@Override
		public Quaternion normalize() {
			return this;
		}
		
		@Override
		public Quaternion conjugate() {
			return this;
		}
		
	}
	
}

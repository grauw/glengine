package nl.grauw.glengine.model;

import nl.grauw.glengine.gl.GLEngineException;

public class Matrix3x3 extends Matrix {
	
	public static final Identity IDENTITY = new Identity();
	
	protected final float[] elements;
	
	/** Elements must be specified in column-major order. */
	public Matrix3x3(float... elements) {
		if (elements.length != 9)
			throw new GLEngineException("Number of elements does not match matrix size.");
		this.elements = elements;
	}
	
	public Matrix3x3(Matrix other) {
		elements = copy(3, 3, other);
	}
	
	public Matrix3x3(float scalar) {
		this(scalar, 0, 0, 0, scalar, 0, 0, 0, scalar);
	}
	
	@Override
	public final int getColumns() {
		return 3;
	}
	
	@Override
	public final int getRows() {
		return 3;
	}
	
	@Override
	public final float[] getElements() {
		return elements;
	}
	
	public Matrix3x3 transpose() {
		return new Matrix3x3(transpose(3, 3, elements));
	}
	
	public Matrix3x3 multiply(Matrix3x3 other) {
		if (other == IDENTITY)
			return this;
		return new Matrix3x3(multiply(3, 3, elements, 3, 3, other.elements));
	}
	
	public final Matrix3x3 multiply(Identity other) {
		return this;
	}
	
	public Vector3 multiply(Vector3 vector) {
		return multiply(vector.getX(), vector.getY(), vector.getZ());
	}
	
	public Vector3 multiply(float x, float y, float z) {
		return new Vector3(
			elements[0] * x + elements[3] * y + elements[6] * z,
			elements[1] * x + elements[4] * y + elements[7] * z,
			elements[2] * x + elements[5] * y + elements[8] * z
		);
	}
	
	public final Vector3 getColumn(int index) {
		if (index < 0 || index > 2)
			throw new GLEngineException("Column index out of range.");
		return new Vector3(elements[index * 3], elements[index * 3 + 1], elements[index * 3 + 2]);
	}
	
	public final Vector3 getRow(int index) {
		if (index < 0 || index > 2)
			throw new GLEngineException("Row index out of range.");
		return new Vector3(elements[index], elements[index + 3], elements[index + 6]);
	}
	
	public static final class Identity extends Matrix3x3 {
		
		private Identity() {
			super(1, 0, 0, 0, 1, 0, 0, 0, 1);
		}
		
		@Override
		public Matrix3x3 transpose() {
			return this;
		}
		
		@Override
		public Matrix multiply(Matrix other) {
			if (other.getRows() != 3)
				throw new GLEngineException("Incompatible matrices for multiplication.");
			return other;
		}
		
		@Override
		public Matrix3x3 multiply(Matrix3x3 other) {
			return other;
		}
		
		@Override
		public Vector3 multiply(Vector3 vector) {
			return vector;
		}
		
		@Override
		public Vector3 multiply(float x, float y, float z) {
			return new Vector3(x, y, z);
		}
		
	}
	
	public static final class Translation extends Matrix3x3 {
		
		public Translation(float x, float y) {
			super(1, 0, 0, 0, 1, 0, x, y, 1);
		}
		
		public Translation(Vector2 vector) {
			this(vector.getX(), vector.getY());
		}
		
		@Override
		public Matrix3x3 multiply(Matrix3x3 other) {
			return new Matrix3x3(
				elements[6] * other.elements[2] + other.elements[0], elements[7] * other.elements[2] + other.elements[1], other.elements[2],
				elements[6] * other.elements[5] + other.elements[3], elements[7] * other.elements[5] + other.elements[4], other.elements[5],
				elements[6] * other.elements[8] + other.elements[6], elements[7] * other.elements[8] + other.elements[7], other.elements[8]
			);
		}
		
		@Override
		public Vector3 multiply(float x, float y, float z) {
			return new Vector3(elements[6] * z + x, elements[7] * z + y, z);
		}
		
	}
	
	public static final class Scale extends Matrix3x3 {
		
		public Scale(float x, float y, float z) {
			super(x, 0, 0, 0, y, 0, 0, 0, z);
		}
		
		public Scale(float x, float y) {
			this(x, y, 1);
		}
		
		public Scale(Vector3 vector) {
			this(vector.getX(), vector.getY(), vector.getZ());
		}
		
		public Scale(Vector2 vector) {
			this(vector.getX(), vector.getY());
		}
		
		@Override
		public Matrix3x3 multiply(Matrix3x3 other) {
			return new Matrix3x3(
				elements[0] * other.elements[0], elements[4] * other.elements[1], elements[8] * other.elements[2],
				elements[0] * other.elements[3], elements[4] * other.elements[4], elements[8] * other.elements[5],
				elements[0] * other.elements[6], elements[4] * other.elements[7], elements[8] * other.elements[8]
			);
		}
		
		@Override
		public Vector3 multiply(float x, float y, float z) {
			return new Vector3(elements[0] * x, elements[4] * y, elements[8] * z);
		}
		
	}
	
	public static final class Rotation extends Matrix3x3 {
		
		/**
		 * 2D rotation (identical to ZRotation).
		 * @param angle Rotation angle in radians.
		 */
		public Rotation(double angle) {
			super(
				(float)Math.cos(angle), (float)Math.sin(angle), 0,
				-(float)Math.sin(angle), (float)Math.cos(angle), 0,
				0, 0, 1
			);
		}
		
		public Rotation(float x, float y, float z, double angle) {
			this(new Quaternion.Axis(x, y, z, angle));
		}
		
		public Rotation(Quaternion quaternion) {
			super(quaternionToMatrix(quaternion));
		}
		
		private static float[] quaternionToMatrix(Quaternion quaternion) {
			float x = quaternion.getX(), y = quaternion.getY(), z = quaternion.getZ(), w = quaternion.getW();
			float sx = 2 * x, sy = 2 * y, sz = 2 * z;
			float wx = w * sx, wy = w * sy, wz = w * sz;
			float xx = x * sx, xy = x * sy, xz = x * sz;
			float yy = y * sy, yz = y * sz, zz = z * sz;
			return new float[] {
				1 - yy - zz, xy + wz, xz - wy,
				xy - wz, 1 - xx - zz, yz + wx,
				xz + wy, yz - wx, 1 - xx - yy
			};
		}
		
	}
	
	public static final class XRotation extends Matrix3x3 {
		
		public XRotation(double angle) {
			super(
				1, 0, 0,
				0, (float)Math.cos(angle), (float)Math.sin(angle),
				0, -(float)Math.sin(angle), (float)Math.cos(angle)
			);
		}
		
	}
	
	public static final class YRotation extends Matrix3x3 {
		
		public YRotation(double angle) {
			super(
				(float)Math.cos(angle), 0, -(float)Math.sin(angle),
				0, 1, 0,
				(float)Math.sin(angle), 0, (float)Math.cos(angle)
			);
		}
		
	}
	
	public static final class ZRotation extends Matrix3x3 {
		
		public ZRotation(double angle) {
			super(
				(float)Math.cos(angle), (float)Math.sin(angle), 0,
				-(float)Math.sin(angle), (float)Math.cos(angle), 0,
				0, 0, 1
			);
		}
		
	}
	
}
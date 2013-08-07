package nl.grauw.glengine.model;

import nl.grauw.glengine.gl.GLEngineException;

public class Matrix3x3 extends Matrix {
	
	public static final Identity IDENTITY = new Identity();
	
	private float[] elements;
	
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
	
	public Matrix3x3 multiply(Matrix3x3 other) {
		if (other == IDENTITY)
			return this;
		return new Matrix3x3(multiply(3, 3, elements, 3, 3, other.elements));
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
	
	public Vector3 getColumn(int index) {
		if (index < 0 || index > 2)
			throw new GLEngineException("Column index out of range.");
		return new Vector3(elements[index * 3], elements[index * 3 + 1], elements[index * 3 + 2]);
	}
	
	public Vector3 getRow(int index) {
		if (index < 0 || index > 2)
			throw new GLEngineException("Row index out of range.");
		return new Vector3(elements[index], elements[index + 3], elements[index + 6]);
	}
	
	static final class Identity extends Matrix3x3 {
		
		public Identity() {
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
		
		public Matrix3x3 multiply(Matrix3x3 other) {
			return other;
		}
		
	}
	
	public static class Translation extends Matrix3x3 {
		
		public Translation(float x, float y) {
			super(1, 0, 0, 0, 1, 0, x, y, 1);
		}
		
		public Translation(Vector2 vector) {
			this(vector.getX(), vector.getY());
		}
		
	}
	
	public static class Scale extends Matrix3x3 {
		
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
		
	}
	
	public static class Rotation extends Matrix3x3 {
		
		/**
		 * 2D rotation (identical to ZRotation).
		 * @param angle Rotation angle in radians.
		 */
		public Rotation(float angle) {
			super(
				(float)Math.cos(angle), (float)Math.sin(angle), 0,
				-(float)Math.sin(angle), (float)Math.cos(angle), 0,
				0, 0, 1
			);
		}
		
		public Rotation(float x, float y, float z, float angle) {
			this(new Quaternion.Axis(x, y, z, angle));
		}
		
		public Rotation(Quaternion quaternion) {
			this(quaternion, quaternion.getX(), quaternion.getY(), quaternion.getZ(), quaternion.getW());
		}
		
		private Rotation(Quaternion quaternion, float qx, float qy, float qz, float qw) {
			this(2 * qx * qx, 2 * qx * qy, 2 * qx * qz, 2 * qx * qw, 2 * qy * qy, 2 * qy * qz, 2 * qy * qw, 2 * qz * qz, 2 * qz * qw);
		}
		
		private Rotation(float qxx, float qxy, float qxz, float qxw, float qyy, float qyz, float qyw, float qzz, float qzw) {
			super(
				1 - qyy - qzz, qxy + qzw, qxz - qyw,
				qxy - qzw, 1 - qxx - qzz, qyz + qxw,
				qxz + qyw, qyz - qxw, 1 - qxx - qyy
			);
		}
		
	}
	
	public static class XRotation extends Matrix3x3 {
		
		public XRotation(float angle) {
			super(
				1, 0, 0,
				0, (float)Math.cos(angle), (float)Math.sin(angle),
				0, -(float)Math.sin(angle), (float)Math.cos(angle)
			);
		}
		
	}
	
	public static class YRotation extends Matrix3x3 {
		
		public YRotation(float angle) {
			super(
				(float)Math.cos(angle), 0, -(float)Math.sin(angle),
				0, 1, 0,
				(float)Math.sin(angle), 0, (float)Math.cos(angle)
			);
		}
		
	}
	
	public static class ZRotation extends Matrix3x3 {
		
		public ZRotation(float angle) {
			super(
				(float)Math.cos(angle), (float)Math.sin(angle), 0,
				-(float)Math.sin(angle), (float)Math.cos(angle), 0,
				0, 0, 1
			);
		}
		
	}
	
}
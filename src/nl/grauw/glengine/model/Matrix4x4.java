package nl.grauw.glengine.model;

import nl.grauw.glengine.gl.GLEngineException;

public class Matrix4x4 extends Matrix {
	
	public static final Identity IDENTITY = new Identity();
	
	public Matrix4x4(float... elements) {
		super(4, 4, elements);
	}
	
	public Matrix4x4 multiply(Matrix4x4 other) {
		if (other == IDENTITY)
			return this;
		return new Matrix4x4(multiply(4, 4, elements, 4, 4, other.elements));
	}
	
	public Vector4 multiply(Vector4 vector) {
		return multiply(vector.getX(), vector.getY(), vector.getZ(), vector.getW());
	}
	
	public Vector4 multiply(float x, float y, float z, float w) {
		return new Vector4(
			elements[0] * x + elements[4] * y + elements[8] * z + elements[12] * w,
			elements[1] * x + elements[5] * y + elements[9] * z + elements[13] * w,
			elements[2] * x + elements[6] * y + elements[10] * z + elements[14] * w,
			elements[3] * x + elements[7] * y + elements[11] * z + elements[15] * w
		);
	}
	
	public Vector4 getColumn(int index) {
		if (index < 0 || index > 3)
			throw new GLEngineException("Column index out of range.");
		return new Vector4(elements[index * 4], elements[index * 4 + 1], elements[index * 4 + 2], elements[index * 4 + 3]);
	}
	
	public Vector4 getRow(int index) {
		if (index < 0 || index > 3)
			throw new GLEngineException("Row index out of range.");
		return new Vector4(elements[index], elements[index + 4], elements[index + 8], elements[index + 12]);
	}
	
	static final class Identity extends Matrix4x4 {
		
		public Identity() {
			super(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
		}
		
		@Override
		public Matrix4x4 transpose() {
			return this;
		}
		
		@Override
		public Matrix multiply(Matrix other) {
			if (other.rows != columns)
				throw new GLEngineException("Incompatible matrices for multiplication.");
			return other;
		}
		
		public Matrix4x4 multiply(Matrix4x4 other) {
			return other;
		}
		
	}
	
	public static class Translation extends Matrix4x4 {
		
		public Translation(float x, float y, float z) {
			super(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, x, y, z, 1);
		}
		
		public Translation(Vector3 vector) {
			this(vector.getX(), vector.getY(), vector.getZ());
		}
		
	}
	
	public static class Scale extends Matrix4x4 {
		
		public Scale(float x, float y, float z) {
			super(x, 0, 0, 0, 0, y, 0, 0, 0, 0, z, 0, 0, 0, 0, 1);
		}
		
		public Scale(Vector3 vector) {
			this(vector.getX(), vector.getY(), vector.getZ());
		}
		
	}
	
	public static class Rotation extends Matrix4x4 {
		
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
				1 - qyy - qzz, qxy + qzw, qxz - qyw, 0,
				qxy - qzw, 1 - qxx - qzz, qyz + qxw, 0,
				qxz + qyw, qyz - qxw, 1 - qxx - qyy, 0,
				0, 0, 0, 1
			);
		}
		
	}
	
	public static class XRotation extends Matrix4x4 {
		
		public XRotation(float angle) {
			super(1, 0, 0, 0, 0, (float)Math.cos(angle), (float)Math.sin(angle), 0,
				0, -(float)Math.sin(angle), (float)Math.cos(angle), 0, 0, 0, 0, 1);
		}
		
	}
	
	public static class YRotation extends Matrix4x4 {
		
		public YRotation(float angle) {
			super((float)Math.cos(angle), 0, -(float)Math.sin(angle), 0, 0, 1, 0, 0,
				(float)Math.sin(angle), 0, (float)Math.cos(angle), 0, 0, 0, 0, 1);
		}
		
	}
	
	public static class ZRotation extends Matrix4x4 {
		
		public ZRotation(float angle) {
			super((float)Math.cos(angle), (float)Math.sin(angle), 0, 0,
				-(float)Math.sin(angle), (float)Math.cos(angle), 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
		}
		
	}
	
	public static class PerspectiveProjection extends Matrix4x4 {
		
		public PerspectiveProjection(float width, float height, float near, float far) {
			this(width / -2f, width / 2f, height / -2f, height / 2f, near, far);
		}
		
		public PerspectiveProjection(float left, float right, float bottom, float top, float near, float far) {
			super(
				(2 * near) / (right - left), 0, 0, 0,
				0, (2 * near) / (top - bottom), 0, 0,
				(right + left) / (right - left), (top + bottom) / (top - bottom), (far + near) / (near - far), -1,
				0, 0, 2 * near * far / (near - far), 0
			);
		}
		
	}
	
	public static class OrthographicProjection extends Matrix4x4 {
		
		public OrthographicProjection(float width, float height, float near, float far) {
			this(width / -2f, width / 2f, height / -2f, height / 2f, near, far);
		}
		
		public OrthographicProjection(float left, float right, float bottom, float top, float near, float far) {
			super(
				2 / (right - left), 0, 0, 0,
				0, 2 / (top - bottom), 0, 0,
				0, 0, 2 / (near - far), 0,
				(right + left) / (left - right), (top + bottom) / (bottom - top), (far + near) / (near - far), 1
			);
		}
		
	}
	
}
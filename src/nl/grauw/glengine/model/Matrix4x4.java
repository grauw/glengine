package nl.grauw.glengine.model;

import nl.grauw.glengine.gl.GLEngineException;

public class Matrix4x4 extends Matrix {
	
	public static final Identity IDENTITY = new Identity();
	
	protected final float[] elements;
	
	/** Elements must be specified in column-major order. */
	public Matrix4x4(float... elements) {
		if (elements.length != 16)
			throw new GLEngineException("Number of elements does not match matrix size.");
		this.elements = elements;
	}
	
	public Matrix4x4(Matrix other) {
		elements = copy(4, 4, other);
	}
	
	public Matrix4x4(float scalar) {
		this(scalar, 0, 0, 0, 0, scalar, 0, 0, 0, 0, scalar, 0, 0, 0, 0, scalar);
	}
	
	@Override
	public final int getColumns() {
		return 4;
	}
	
	@Override
	public final int getRows() {
		return 4;
	}
	
	@Override
	public final float[] getElements() {
		return elements;
	}
	
	public Matrix4x4 transpose() {
		return new Matrix4x4(transpose(4, 4, elements));
	}
	
	public Matrix4x4 multiply(Matrix4x4 other) {
		if (other == IDENTITY)
			return this;
		return new Matrix4x4(multiply(4, 4, elements, 4, 4, other.elements));
	}
	
	public final Matrix4x4 multiply(Identity other) {
		return this;
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
	
	public final Vector4 getColumn(int index) {
		if (index < 0 || index > 3)
			throw new GLEngineException("Column index out of range.");
		return new Vector4(elements[index * 4], elements[index * 4 + 1], elements[index * 4 + 2], elements[index * 4 + 3]);
	}
	
	public final Vector4 getRow(int index) {
		if (index < 0 || index > 3)
			throw new GLEngineException("Row index out of range.");
		return new Vector4(elements[index], elements[index + 4], elements[index + 8], elements[index + 12]);
	}
	
	public static final class Identity extends Matrix4x4 {
		
		private Identity() {
			super(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
		}
		
		@Override
		public Matrix4x4 transpose() {
			return this;
		}
		
		@Override
		public Matrix multiply(Matrix other) {
			if (other.getRows() != 4)
				throw new GLEngineException("Incompatible matrices for multiplication.");
			return other;
		}
		
		@Override
		public Matrix4x4 multiply(Matrix4x4 other) {
			return other;
		}
		
		@Override
		public Vector4 multiply(Vector4 vector) {
			return vector;
		}
		
		@Override
		public Vector4 multiply(float x, float y, float z, float w) {
			return new Vector4(x, y, z, w);
		}
		
	}
	
	public static final class Translation extends Matrix4x4 {
		
		public Translation(float x, float y, float z) {
			super(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, x, y, z, 1);
		}
		
		public Translation(Vector3 vector) {
			this(vector.getX(), vector.getY(), vector.getZ());
		}
		
		@Override
		public Matrix4x4 multiply(Matrix4x4 other) {
			return new Matrix4x4(
				elements[12] * other.elements[3] + other.elements[0], elements[13] * other.elements[3] + other.elements[1], elements[14] * other.elements[3] + other.elements[2], other.elements[3],
				elements[12] * other.elements[7] + other.elements[4], elements[13] * other.elements[7] + other.elements[5], elements[14] * other.elements[7] + other.elements[6], other.elements[7],
				elements[12] * other.elements[11] + other.elements[8], elements[13] * other.elements[11] + other.elements[9], elements[14] * other.elements[11] + other.elements[10], other.elements[11],
				elements[12] * other.elements[15] + other.elements[12], elements[13] * other.elements[15] + other.elements[13], elements[14] * other.elements[15] + other.elements[14], other.elements[15]
			);
		}
		
		@Override
		public Vector4 multiply(float x, float y, float z, float w) {
			return new Vector4(elements[12] * w + x, elements[13] * w + y, elements[14] * w + z, w);
		}
		
	}
	
	public static final class Scale extends Matrix4x4 {
		
		public Scale(float x, float y, float z, float w) {
			super(x, 0, 0, 0, 0, y, 0, 0, 0, 0, z, 0, 0, 0, 0, w);
		}
		
		public Scale(float x, float y, float z) {
			this(x, y, z, 1);
		}
		
		public Scale(Vector4 vector) {
			this(vector.getX(), vector.getY(), vector.getZ(), vector.getW());
		}
		
		public Scale(Vector3 vector) {
			this(vector.getX(), vector.getY(), vector.getZ());
		}
		
		@Override
		public Matrix4x4 multiply(Matrix4x4 other) {
			return new Matrix4x4(
				elements[0] * other.elements[0], elements[5] * other.elements[1], elements[10] * other.elements[2], elements[15] * other.elements[3],
				elements[0] * other.elements[4], elements[5] * other.elements[5], elements[10] * other.elements[6], elements[15] * other.elements[7],
				elements[0] * other.elements[8], elements[5] * other.elements[9], elements[10] * other.elements[10], elements[15] * other.elements[11],
				elements[0] * other.elements[12], elements[5] * other.elements[13], elements[10] * other.elements[14], elements[15] * other.elements[15]
			);
		}
		
		@Override
		public Vector4 multiply(float x, float y, float z, float w) {
			return new Vector4(elements[0] * x, elements[5] * y, elements[10] * z, elements[15] * w);
		}
		
	}
	
	public static final class Rotation extends Matrix4x4 {
		
		public Rotation(float x, float y, float z, double angle) {
			this(new Quaternion.Axis(x, y, z, angle));
		}
		
		public Rotation(Quaternion quaternion) {
			super(quaternionToMatrix(quaternion));
		}
		
		private static float[] quaternionToMatrix(Quaternion quaternion) {
			float qx = quaternion.getX(), qy = quaternion.getY(), qz = quaternion.getZ(), qw = quaternion.getW();
			float qxx = 2 * qx * qx, qxy = 2 * qx * qy, qxz = 2 * qx * qz;
			float qxw = 2 * qx * qw, qyy = 2 * qy * qy, qyz = 2 * qy * qz;
			float qyw = 2 * qy * qw, qzz = 2 * qz * qz, qzw = 2 * qz * qw;
			return new float[] {
				1 - qyy - qzz, qxy + qzw, qxz - qyw, 0,
				qxy - qzw, 1 - qxx - qzz, qyz + qxw, 0,
				qxz + qyw, qyz - qxw, 1 - qxx - qyy, 0,
				0, 0, 0, 1
			};
		}
		
	}
	
	public static final class XRotation extends Matrix4x4 {
		
		public XRotation(double angle) {
			super(
				1, 0, 0, 0,
				0, (float)Math.cos(angle), (float)Math.sin(angle), 0,
				0, -(float)Math.sin(angle), (float)Math.cos(angle), 0,
				0, 0, 0, 1
			);
		}
		
	}
	
	public static final class YRotation extends Matrix4x4 {
		
		public YRotation(double angle) {
			super(
				(float)Math.cos(angle), 0, -(float)Math.sin(angle), 0,
				0, 1, 0, 0,
				(float)Math.sin(angle), 0, (float)Math.cos(angle), 0,
				0, 0, 0, 1
			);
		}
		
	}
	
	public static final class ZRotation extends Matrix4x4 {
		
		public ZRotation(double angle) {
			super(
				(float)Math.cos(angle), (float)Math.sin(angle), 0, 0,
				-(float)Math.sin(angle), (float)Math.cos(angle), 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
			);
		}
		
	}
	
	public static final class TranslationRotationScale extends Matrix4x4 {
		
		public TranslationRotationScale(Vector3 translation, Quaternion rotation, Vector3 scale) {
			this(translation, rotation.getX(), rotation.getY(), rotation.getZ(), rotation.getW(), scale);
		}
		
		private TranslationRotationScale(Vector3 translation, float qx, float qy, float qz, float qw, Vector3 scale) {
			this(translation, 2 * qx * qx, 2 * qx * qy, 2 * qx * qz, 2 * qx * qw, 2 * qy * qy, 2 * qy * qz, 2 * qy * qw, 2 * qz * qz, 2 * qz * qw, scale.getX(), scale.getY(), scale.getZ());
		}
		
		private TranslationRotationScale(Vector3 translation, float qxx, float qxy, float qxz, float qxw, float qyy, float qyz, float qyw, float qzz, float qzw, float scaleX, float scaleY, float scaleZ) {
			super(
				(1 - qyy - qzz) * scaleX, (qxy + qzw) * scaleX, (qxz - qyw) * scaleX, 0,
				(qxy - qzw) * scaleY, (1 - qxx - qzz) * scaleY, (qyz + qxw) * scaleY, 0,
				(qxz + qyw) * scaleZ, (qyz - qxw) * scaleZ, (1 - qxx - qyy) * scaleZ, 0,
				translation.getX(), translation.getY(), translation.getZ(), 1
			);
		}
		
		public TranslationRotationScale(Vector3 translation, Quaternion rotation) {
			this(translation, rotation.getX(), rotation.getY(), rotation.getZ(), rotation.getW(), Vector3.ONE);
		}
		
		public TranslationRotationScale(Quaternion rotation, Vector3 scale) {
			this(Vector3.ZERO, rotation.getX(), rotation.getY(), rotation.getZ(), rotation.getW(), scale);
		}
		
		public TranslationRotationScale(Vector3 translation, Vector3 scale) {
			super(
				scale.getX(), 0, 0, 0,
				0, scale.getY(), 0, 0,
				0, 0, scale.getZ(), 0,
				translation.getX(), translation.getY(), translation.getZ(), 1
			);
		}
		
		/**
		 * Returns a matrix that transforms, then rotates, then scales.
		 * Depending on the arguments it will return simpler matrices, or even the identity matrix.
		 **/
		public static Matrix4x4 create(Vector3 translation, Quaternion rotation, Vector3 scale) {
			if (translation != Vector3.ZERO) {
				if (rotation != Quaternion.IDENTITY) {
					if (scale != Vector3.ONE)
						return new TranslationRotationScale(translation, rotation, scale);
					return new TranslationRotationScale(translation, rotation);
				}
				if (scale != Vector3.ONE)
					return new TranslationRotationScale(translation, scale);
				return new Translation(translation);
			}
			if (rotation != Quaternion.IDENTITY) {
				if (scale != Vector3.ONE)
					return new TranslationRotationScale(rotation, scale);
				return new Rotation(rotation);
			}
			if (scale != Vector3.ONE)
				return new Scale(scale);
			return IDENTITY;
		}
		
	}
	
	public static final class PerspectiveProjection extends Matrix4x4 {
		
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
	
	public static final class OrthographicProjection extends Matrix4x4 {
		
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
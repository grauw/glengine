package nl.grauw.glengine.model;

import java.util.Arrays;

import nl.grauw.glengine.gl.GLEngineException;

public class Matrix {
	
	public static final Identity IDENTITY = new Identity();
	
	protected final int columns;
	protected final int rows;
	private final float[] elements;
	
	/** Elements must be specified in column-major order. */
	public Matrix(int columns, int rows, float... elements) {
		if (columns < 1 || rows < 1)
			throw new GLEngineException("Matrix must have at least 1 column and 1 row.");
		if (elements.length != columns * rows)
			throw new GLEngineException("Number of elements does not match matrix size.");
		
		this.columns = columns;
		this.rows = rows;
		this.elements = elements;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public int getRows() {
		return rows;
	}
	
	public float getElement(int column, int row) {
		if (column < 0 || column >= columns || row < 0 || row >= rows)
			throw new GLEngineException("Column or row index out of bounds.");
		
		return elements[column * rows + row];
	}
	
	/**
	 * Returns matrix elements array in column-major order.
	 * Warning: do not modify this array.
	 **/
	public float[] getElements() {
		return elements;
	}
	
	public boolean equals(Matrix other) {
		return this == other || columns == other.columns && Arrays.equals(elements, other.elements);
	}
	
	public Matrix transpose() {
		float[] transposed = new float[elements.length];
		for (int column = 0; column < columns; column++)
			for (int row = 0; row < rows; row++)
				transposed[row * columns + column] = elements[column * rows + row];
		return new Matrix(rows, columns, transposed);
	}
	
	public Matrix multiply(Matrix other) {
		if (other == IDENTITY)
			return this;
		
		return new Matrix(other.columns, rows, multiply(columns, rows, elements, other.columns, other.rows, other.elements));
	}
	
	protected static float[] multiply(int columns, int rows, float[] elements, int otherColumns, int otherRows, float[] otherElements) {
		if (columns != otherRows)
			throw new GLEngineException("Incompatible matrices for multiplication.");
		float[] product = new float[otherColumns * rows];
		for (int column = 0; column < otherColumns; column++) {
			for (int row = 0; row < rows; row++) {
				float sum = elements[row] * otherElements[column * otherRows];
				for (int i = 1; i < columns; i++)
					sum += elements[i * rows + row] * otherElements[column * otherRows + i];
				product[column * rows + row] = sum;
			}
		}
		return product;
	}
	
	public static class Matrix4x4 extends Matrix {
		
		public Matrix4x4(float... elements) {
			super(4, 4, elements);
		}
		
	}
	
	private static final class Identity extends Matrix4x4 {
		
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

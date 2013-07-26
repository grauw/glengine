package nl.grauw.glengine.model;

import java.util.Arrays;

import nl.grauw.glengine.gl.GLEngineException;

public class Matrix {
	
	public static final Identity IDENTITY = new Identity();
	
	protected int columns;
	protected int rows;
	private float[] elements;
	
	/** Elements must be specified in column-major order. */
	public Matrix(int columns, int rows, float... elements) {
		if (columns < 1 || rows < 1)
			throw new GLEngineException("Matrix must have at least 1 column and 1 row.");
		if (elements.length != columns * rows)
			throw new GLEngineException("Data length does not match matrix size");
		
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
	
	public float getValue(int column, int row) {
		if (column < 0 || column >= columns || row < 0 || row >= rows)
			throw new GLEngineException("Column or row index out of bounds.");
		
		return elements[column * rows + row];
	}
	
	/** Returns matrix elements array in column-major order. */
	public float[] toArray() {
		return elements.clone();
	}
	
	public boolean equals(Matrix other) {
		return this == other || columns == other.columns && rows == other.rows && Arrays.equals(elements, other.elements);
	}
	
	public Matrix transpose() {
		float[] transposed = new float[elements.length];
		for (int column = 0; column < columns; column++)
			for (int row = 0; row < rows; row++)
				transposed[row * columns + column] = elements[column * rows + row];
		return new Matrix(rows, columns, transposed);
	}
	
	public Matrix multiply(Matrix other) {
		if (other.rows != columns)
			throw new GLEngineException("Incompatible matrices for multiplication.");
		
		float[] product = new float[other.columns * rows];
		
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < other.columns; column++) {
				float sum = 0;
				for (int i = 0; i < columns; i++) {
					sum += elements[i * rows + row] * other.elements[column * other.rows + i];
				}
				product[column * rows + row] = sum;
			}
		}
		
		return new Matrix(other.columns, rows, product);
	}
	
	public static class Identity extends Matrix {
		
		public Identity() {
			super(4, 4, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
		}
		
		@Override
		public Matrix transpose() {
			return this;
		}
		
		@Override
		public Matrix multiply(Matrix other) {
			if (other.rows != columns)
				throw new GLEngineException("Incompatible matrices for multiplication.");
			return other;
		}
		
	}
	
	public static class Translation extends Matrix {
		
		public Translation(float x, float y, float z) {
			super(4, 4, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, x, y, z, 1);
		}
		
	}
	
	public static class Scale extends Matrix {
		
		public Scale(float x, float y, float z) {
			super(4, 4, x, 0, 0, 0, 0, y, 0, 0, 0, 0, z, 0, 0, 0, 0, 1);
		}
		
	}
	
	public static class Rotation extends Matrix {
		
		/**
		 * Using a quaternion.
		 * 
		 * @param qx x * sin(angle / 2)
		 * @param qy y * sin(angle / 2)
		 * @param qz z * sin(angle / 2)
		 * @param qw cos(angle / 2)
		 **/
		public Rotation(float qx, float qy, float qz, float qw) {
			this(2 * qx * qx, 2 * qx * qy, 2 * qx * qz, 2 * qx * qw, 2 * qy * qy, 2 * qy * qz, 2 * qy * qw, 2 * qz * qz, 2 * qz * qw);
		}
		
		private Rotation(float qxx, float qxy, float qxz, float qxw, float qyy, float qyz, float qyw, float qzz, float qzw) {
			super(4, 4,
				1 - qyy - qzz, qxy + qzw, qxz - qyw, 0,
				qxy - qzw, 1 - qxx - qzz, qyz + qxw, 0,
				qxz + qyw, qyz - qxw, 1 - qxx - qyy, 0,
				0, 0, 0, 1
			);
		}
		
	}
	
	public static class AxisRotation extends Rotation {
		
		public AxisRotation(float x, float y, float z, float angle) {
			this(x, y, z, (float)Math.sin(angle / 2), (float)Math.cos(angle / 2));
		}
		
		private AxisRotation(float x, float y, float z, float sinAngle2, float cosAngle2) {
			super(x * sinAngle2, y * sinAngle2, z * sinAngle2, cosAngle2);
		}
		
	}
	
	public static class XRotation extends Matrix {
		
		public XRotation(float angle) {
			super(4, 4, 1, 0, 0, 0, 0, (float)Math.cos(angle), (float)Math.sin(angle), 0,
				0, -(float)Math.sin(angle), (float)Math.cos(angle), 0, 0, 0, 0, 1);
		}
		
	}
	
	public static class YRotation extends Matrix {
		
		public YRotation(float angle) {
			super(4, 4, (float)Math.cos(angle), 0, -(float)Math.sin(angle), 0, 0, 1, 0, 0,
				(float)Math.sin(angle), 0, (float)Math.cos(angle), 0, 0, 0, 0, 1);
		}
		
	}
	
	public static class ZRotation extends Matrix {
		
		public ZRotation(float angle) {
			super(4, 4, (float)Math.cos(angle), (float)Math.sin(angle), 0, 0,
				-(float)Math.sin(angle), (float)Math.cos(angle), 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
		}
		
	}
	
	public static class Projection extends Matrix {
		
		public Projection(float width, float height, float near, float far) {
			super(4, 4, width, 0, 0, 0, 0, height, 0, 0, 0, 0, far / (near - far), -1, 0, 0, near * far / (near - far), 0);
		}
		
	}
	
}

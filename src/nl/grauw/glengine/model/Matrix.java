package nl.grauw.glengine.model;

import java.util.Arrays;

import nl.grauw.glengine.gl.GLEngineException;

public abstract class Matrix {
	
	public abstract int getColumns();
	
	public abstract int getRows();
	
	/** Returns matrix elements array in column-major order. **/
	public abstract float[] getElements();
	
	public float getElement(int column, int row) {
		if (column < 0 || column >= getColumns() || row < 0 || row >= getRows())
			throw new GLEngineException("Column or row index out of bounds.");
		
		return getElements()[column * getRows() + row];
	}
	
	public boolean equals(Matrix other) {
		return this == other || getColumns() == other.getColumns() && Arrays.equals(getElements(), other.getElements());
	}
	
	public Matrix transpose() {
		return new AnyMatrix(getRows(), getColumns(), transpose(getColumns(), getRows(), getElements()));
	}
	
	public Matrix multiply(Matrix other) {
		return new AnyMatrix(other.getColumns(), getRows(), multiply(getColumns(), getRows(), getElements(), other.getColumns(), other.getRows(), other.getElements()));
	}
	
	protected static float[] transpose(int columns, int rows, float[] elements) {
		float[] transposed = new float[elements.length];
		for (int column = 0; column < columns; column++)
			for (int row = 0; row < rows; row++)
				transposed[row * columns + column] = elements[column * rows + row];
		return transposed;
	}
	
	protected static float[] multiply(int columns, int rows, float[] elements, int otherColumns, int otherRows, float[] otherElements) {
		if (columns != otherRows)
			throw new GLEngineException("Incompatible matrices for multiplication.");
		float[] product = new float[otherColumns * rows];
		for (int column = 0, otherColumn = 0, productIndex = 0; column < otherColumns; column++, otherColumn += otherRows) {
			for (int row = 0; row < rows; row++, productIndex++) {
				float sum = elements[row] * otherElements[otherColumn];
				for (int i = 1; i < columns; i++)
					sum += elements[i * rows + row] * otherElements[otherColumn + i];
				product[productIndex] = sum;
			}
		}
		return product;
	}
	
	protected static float[] copy(int columns, int rows, Matrix other) {
		return copy(columns, rows, other.getColumns(), other.getRows(), other.getElements());
	}
	
	protected static float[] copy(int columns, int rows, int otherColumns, int otherRows, float[] otherElements) {
		if (columns < 1 || rows < 1)
			throw new GLEngineException("Matrix must have at least 1 column and 1 row.");
		float[] copy = new float[columns * rows];
		for (int column = 0; column < columns; column++)
			for (int row = 0; row < rows; row++)
				copy[column * rows + row] = column < otherColumns && row < otherRows ?
					otherElements[column * otherRows + row] : column == row ? 1 : 0;
		return copy;
	}
	
	public static class AnyMatrix extends Matrix {
		
		protected final int columns;
		protected final int rows;
		protected final float[] elements;
		
		/** Elements must be specified in column-major order. */
		public AnyMatrix(int columns, int rows, float... elements) {
			if (columns < 1 || rows < 1)
				throw new GLEngineException("Matrix must have at least 1 column and 1 row.");
			if (elements.length != columns * rows)
				throw new GLEngineException("Number of elements does not match matrix size.");
			
			this.columns = columns;
			this.rows = rows;
			this.elements = elements;
		}
		
		public AnyMatrix(int columns, int rows, Matrix other) {
			this(columns, rows, copy(columns, rows, other));
		}
		
		@Override
		public final int getColumns() {
			return columns;
		}
		
		@Override
		public final int getRows() {
			return rows;
		}
		
		@Override
		public final float[] getElements() {
			return elements;
		}
		
	}
	
}

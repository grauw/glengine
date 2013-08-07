package nl.grauw.glengine.model;

import java.util.Arrays;

import nl.grauw.glengine.gl.GLEngineException;

public class Matrix {
	
	protected final int columns;
	protected final int rows;
	protected final float[] elements;
	
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
		return new Matrix(other.columns, rows, multiply(columns, rows, elements, other.columns, other.rows, other.elements));
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
	
}

package nl.grauw.glengine.model;

import java.util.Arrays;

import nl.grauw.glengine.gl.GLEngineException;

public class Matrix {
	
	private int columns;
	private int rows;
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
		return columns == other.columns && rows == other.rows && Arrays.equals(elements, other.elements);
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
	
}

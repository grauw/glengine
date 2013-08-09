package nl.grauw.glengine.model;

import nl.grauw.glengine.gl.GLEngineException;

public class Matrix2x2 extends Matrix {
	
	public static final Identity IDENTITY = new Identity();
	
	protected final float[] elements;
	
	/** Elements must be specified in column-major order. */
	public Matrix2x2(float... elements) {
		if (elements.length != 4)
			throw new GLEngineException("Number of elements does not match matrix size.");
		this.elements = elements;
	}
	
	public Matrix2x2(Matrix other) {
		elements = copy(2, 2, other);
	}
	
	public Matrix2x2(float scalar) {
		this(scalar, 0, 0, scalar);
	}
	
	@Override
	public final int getColumns() {
		return 2;
	}
	
	@Override
	public final int getRows() {
		return 2;
	}
	
	@Override
	public final float[] getElements() {
		return elements;
	}
	
	public Matrix2x2 transpose() {
		return new Matrix2x2(transpose(2, 2, elements));
	}
	
	public Matrix2x2 multiply(Matrix2x2 other) {
		if (other == IDENTITY)
			return this;
		return new Matrix2x2(multiply(2, 2, elements, 2, 2, other.elements));
	}
	
	public final Matrix2x2 multiply(Identity other) {
		return this;
	}
	
	public Vector2 multiply(Vector2 vector) {
		return multiply(vector.getX(), vector.getY());
	}
	
	public Vector2 multiply(float x, float y) {
		return new Vector2(
			elements[0] * x + elements[2] * y,
			elements[1] * x + elements[3] * y
		);
	}
	
	public final Vector2 getColumn(int index) {
		if (index < 0 || index > 1)
			throw new GLEngineException("Column index out of range.");
		return new Vector2(elements[index * 2], elements[index * 2 + 1]);
	}
	
	public final Vector2 getRow(int index) {
		if (index < 0 || index > 1)
			throw new GLEngineException("Row index out of range.");
		return new Vector2(elements[index], elements[index + 2]);
	}
	
	public static final class Identity extends Matrix2x2 {
		
		private Identity() {
			super(1, 0, 0, 1);
		}
		
		@Override
		public Matrix2x2 transpose() {
			return this;
		}
		
		@Override
		public Matrix multiply(Matrix other) {
			if (other.getRows() != 2)
				throw new GLEngineException("Incompatible matrices for multiplication.");
			return other;
		}
		
		@Override
		public Matrix2x2 multiply(Matrix2x2 other) {
			return other;
		}
		
		@Override
		public Vector2 multiply(Vector2 vector) {
			return vector;
		}
		
		@Override
		public Vector2 multiply(float x, float y) {
			return new Vector2(x, y);
		}
		
	}
	
	public static final class Translation extends Matrix2x2 {
		
		public Translation(float x) {
			super(1, 0, x, 1);
		}
		
		@Override
		public Vector2 multiply(float x, float y) {
			return new Vector2(elements[2] * y + x, y);
		}
		
	}
	
	public static final class Scale extends Matrix2x2 {
		
		public Scale(float x, float y) {
			super(x, 0, 0, y);
		}
		
		public Scale(float x) {
			this(x, 1);
		}
		
		public Scale(Vector2 vector) {
			this(vector.getX(), vector.getY());
		}
		
		@Override
		public Vector2 multiply(float x, float y) {
			return new Vector2(elements[0] * x, elements[3] * y);
		}
		
	}
	
	public static final class Rotation extends Matrix2x2 {
		
		/**
		 * 2D rotation.
		 * @param angle Rotation angle in radians.
		 */
		public Rotation(double angle) {
			super(
				(float)Math.cos(angle), (float)Math.sin(angle),
				-(float)Math.sin(angle), (float)Math.cos(angle)
			);
		}
		
	}
	
}
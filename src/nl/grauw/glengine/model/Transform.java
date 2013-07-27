package nl.grauw.glengine.model;

public class Transform {
	
	private Vector translation = Vector.ZERO;
	private Vector scale = Vector.ONE;
	private Quaternion rotation = Quaternion.IDENTITY;
	private Matrix matrix = Matrix.IDENTITY;
	private boolean dirty = false;
	
	public Transform() {
	}
	
	public Vector getTranslation() {
		return translation;
	}
	
	public void setTranslation(Vector translation) {
		this.translation = translation;
		dirty = true;
	}
	
	public void setTranslation(float x, float y, float z) {
		setTranslation(x == 0 && y == 0 && z == 0 ? Vector.ZERO : new Vector(x, y, z));
	}
	
	public Vector getScale() {
		return scale;
	}
	
	public void setScale(Vector scale) {
		this.scale = scale;
		dirty = true;
	}
	
	public void setScale(float x, float y, float z) {
		setScale(x == 1 && y == 1 && z == 1 ? Vector.ONE : new Vector(x, y, z));
	}
	
	public void setScale(float scale) {
		setScale(scale == 1 ? Vector.ONE : new Vector(scale, scale, scale));
	}
	
	public Quaternion getRotation() {
		return rotation;
	}
	
	public void setRotation(Quaternion rotation) {
		this.rotation = rotation;
		dirty = true;
	}
	
	public void setRotation(float x, float y, float z, float angle) {
		setRotation(angle == 0 ? Quaternion.IDENTITY : new Quaternion.Axis(x, y, z, angle));
	}
	
	public void setRotationXYZ(float x, float y, float z) {
		setRotation(x == 0 && y == 0 && z == 0 ? Quaternion.IDENTITY : new Quaternion.EulerXYZ(x, y, z));
	}
	
	public Matrix getMatrix() {
		if (dirty) {
			Matrix matrix = Matrix.IDENTITY;
			if (translation != Vector.ZERO)
				matrix = matrix.multiply(new Matrix.Translation(translation));
			if (rotation != Quaternion.IDENTITY)
				matrix = matrix.multiply(new Matrix.Rotation(rotation));
			if (scale != Vector.ONE)
				matrix = matrix.multiply(new Matrix.Scale(scale));
			this.matrix = matrix;
		}
		return matrix;
	}
	
}

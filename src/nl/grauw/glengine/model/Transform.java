package nl.grauw.glengine.model;

public class Transform {
	
	private Vector3 translation = Vector3.ZERO;
	private Vector3 scale = Vector3.ONE;
	private Quaternion rotation = Quaternion.IDENTITY;
	private Matrix matrix = Matrix4x4.IDENTITY;
	private boolean dirty = false;
	
	public Transform() {
	}
	
	public Vector3 getTranslation() {
		return translation;
	}
	
	public void setTranslation(Vector3 translation) {
		this.translation = translation;
		dirty = true;
	}
	
	public void setTranslation(float x, float y, float z) {
		setTranslation(x == 0 && y == 0 && z == 0 ? Vector3.ZERO : new Vector3(x, y, z));
	}
	
	public Vector3 getScale() {
		return scale;
	}
	
	public void setScale(Vector3 scale) {
		this.scale = scale;
		dirty = true;
	}
	
	public void setScale(float x, float y, float z) {
		setScale(x == 1 && y == 1 && z == 1 ? Vector3.ONE : new Vector3(x, y, z));
	}
	
	public void setScale(float scale) {
		setScale(scale == 1 ? Vector3.ONE : new Vector3(scale, scale, scale));
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
			Matrix matrix = Matrix4x4.IDENTITY;
			if (translation != Vector3.ZERO)
				matrix = matrix.multiply(new Matrix4x4.Translation(translation));
			if (rotation != Quaternion.IDENTITY)
				matrix = matrix.multiply(new Matrix4x4.Rotation(rotation));
			if (scale != Vector3.ONE)
				matrix = matrix.multiply(new Matrix4x4.Scale(scale));
			this.matrix = matrix;
		}
		return matrix;
	}
	
}

package nl.grauw.glengine.scene;

import java.util.ArrayList;

import nl.grauw.glengine.gl.GLContext;
import nl.grauw.glengine.model.Matrix4x4;
import nl.grauw.glengine.model.Transform;

public class Node implements Drawable {
	
	private final ArrayList<Drawable> children = new ArrayList<Drawable>();
	
	private final Transform transform = new Transform();
	
	public Node() {
	}
	
	public void add(Drawable child) {
		children.add(child);
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	@Override
	public void initialize(GLContext gl) {
		for (Drawable child : children)
			child.initialize(gl);
	}
	
	public void draw(GLContext gl, Matrix4x4 transform) {
		transform = transform.multiply(this.transform.getMatrix());
		
		for (Drawable child : children)
			child.draw(gl, transform);
	}
	
}

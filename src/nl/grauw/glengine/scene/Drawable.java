package nl.grauw.glengine.scene;

import nl.grauw.glengine.gl.GLContext;
import nl.grauw.glengine.model.Matrix4x4;

/**
 * A drawable object; an object that can be drawn.
 */
public interface Drawable {
	
	/**
	 * Perform initialisation on the GL thread.
	 * @param gl The GL drawing context.
	 */
	public void initialize(GLContext gl);
	
	/**
	 * Render the drawable.
	 * Subclasses should implement this method with the drawing code.
	 * 
	 * @param gl The GL drawing context.
	 */
	public void draw(GLContext gl, Matrix4x4 transform);
	
}

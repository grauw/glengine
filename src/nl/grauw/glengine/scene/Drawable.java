package nl.grauw.glengine.scene;

import nl.grauw.glengine.gl.Context;
import nl.grauw.glengine.model.Matrix;

/**
 * A drawable object; an object that can be drawn.
 */
public interface Drawable {
	
	/**
	 * Render the drawable.
	 * Subclasses should implement this method with the drawing code.
	 * 
	 * @param gl The GL drawing context.
	 */
	public void draw(Context gl, Matrix transform);
	
}

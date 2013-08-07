package nl.grauw.glengine.scene;

import nl.grauw.glengine.gl.GLContext;
import nl.grauw.glengine.gl.GLContext.PrimitiveType;
import nl.grauw.glengine.model.Matrix4x4;
import nl.grauw.glengine.model.Vertex;

public class StripPolygon extends Shape {
	
	public StripPolygon(Vertex... vertices) {
		super(PrimitiveType.TRIANGLE_STRIP);
		
		if (vertices.length < 3)
			throw new Error("At least three vertices required.");
		if ((vertices.length - 3) % 2 == 0)
			throw new Error("Vertices after the third must come in twos.");
		
		setVertices(vertices);
	}
	
	@Override
	public void draw(GLContext gl, Matrix4x4 transform) {
		gl.getCurrentProgram().setUniformValue("modelViewProjection", transform);
		draw(gl);
	}
	
}

package nl.grauw.glengine.scene;

import nl.grauw.glengine.gl.GLContext;
import nl.grauw.glengine.gl.GLContext.PrimitiveType;
import nl.grauw.glengine.model.Matrix;
import nl.grauw.glengine.model.VertexArray.Vertex;

public class StripPolygon extends Shape {
	
	private final Vertex[] vertices;
	
	public StripPolygon(Vertex... vertices) {
		if (vertices.length < 3)
			throw new Error("At least three vertices required.");
		if ((vertices.length - 3) % 2 == 0)
			throw new Error("Vertices after the third must come in two’s.");
		
		this.vertices = vertices;
	}
	
	@Override
	public Vertex[] getVertices() {
		return vertices;
	}
	
	@Override
	public void draw(GLContext gl, Matrix transform) {
		gl.getCurrentProgram().setUniformValue("modelViewProjection", transform);
		drawArrays(gl, PrimitiveType.TRIANGLE_STRIP, "position", "color");
	}
	
}

package nl.grauw.glengine.scene;

import nl.grauw.glengine.gl.GLContext;
import nl.grauw.glengine.gl.GLContext.PrimitiveType;
import nl.grauw.glengine.model.Matrix;
import nl.grauw.glengine.model.VertexArray.Vertex;

public class FanPolygon extends Shape {
	
	private final Vertex[] vertices;
	
	public FanPolygon(Vertex... vertices) {
		if (vertices.length < 3)
			throw new Error("At least three vertices required.");
		
		this.vertices = vertices;
	}
	
	@Override
	public Vertex[] getVertices() {
		return vertices;
	}
	
	@Override
	public void draw(GLContext gl, Matrix transform) {
		gl.getCurrentProgram().setUniformValue("modelViewProjection", transform);
		drawArrays(gl, PrimitiveType.TRIANGLE_FAN, "position", "color");
	}
	
}

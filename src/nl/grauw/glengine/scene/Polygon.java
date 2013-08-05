package nl.grauw.glengine.scene;

import nl.grauw.glengine.gl.GLContext;
import nl.grauw.glengine.gl.GLContext.PrimitiveType;
import nl.grauw.glengine.model.Matrix;
import nl.grauw.glengine.model.Vertex;

public class Polygon extends Shape {
	
	private final Vertex[] vertices;
	
	public Polygon(Vertex... vertices) {
		if (vertices.length < 3)
			throw new Error("At least three vertices required.");
		if (vertices.length % 3 != 0)
			throw new Error("Vertices must come in three’s.");
		
		this.vertices = vertices;
	}
	
	@Override
	public Vertex[] getVertices() {
		return vertices;
	}
	
	@Override
	public void draw(GLContext gl, Matrix transform) {
		gl.getCurrentProgram().setUniformValue("modelViewProjection", transform);
		drawArrays(gl, PrimitiveType.TRIANGLES, "position", "color");
	}
	
}

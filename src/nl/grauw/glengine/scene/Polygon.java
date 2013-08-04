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
	
	public Vertex[] getVertices() {
		return vertices;
	}
	
	@Override
	public void draw(GLContext gl, Matrix transform) {
		gl.getCurrentProgram().setUniformValue("modelViewProjection", transform);
		gl.setVertexAttribPointer("position", 3, 0, getVertexBuffer());
		gl.setVertexAttribPointer("color", 4, 0, getColorBuffer());
		gl.drawArrays(PrimitiveType.TRIANGLES, 0, vertices.length);
	}
	
}

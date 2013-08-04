package nl.grauw.glengine.scene;

import nl.grauw.glengine.gl.Context;
import nl.grauw.glengine.gl.Context.PrimitiveType;
import nl.grauw.glengine.model.Matrix;
import nl.grauw.glengine.model.Vertex;

public class FanPolygon extends Shape {
	
	private final Vertex[] vertices;
	
	public FanPolygon(Vertex... vertices) {
		if (vertices.length < 3)
			throw new Error("At least three vertices required.");
		
		this.vertices = vertices;
	}
	
	public Vertex[] getVertices() {
		return vertices;
	}
	
	@Override
	public void draw(Context gl, Matrix transform) {
		gl.getCurrentProgram().setUniformValue("modelViewProjection", transform);
		gl.setVertexAttribPointer("position", 3, 0, getVertexBuffer());
		gl.setVertexAttribPointer("color", 4, 0, getColorBuffer());
		gl.drawArrays(PrimitiveType.TRIANGLE_FAN, 0, vertices.length);
	}
	
}

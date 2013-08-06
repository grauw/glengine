package nl.grauw.glengine.scene;

import nl.grauw.glengine.gl.GLContext;
import nl.grauw.glengine.gl.GLContext.PrimitiveType;
import nl.grauw.glengine.model.Matrix;
import nl.grauw.glengine.model.Vertex;

public class Polygon extends Shape {
	
	public Polygon(Vertex... vertices) {
		if (vertices.length < 3)
			throw new Error("At least three vertices required.");
		if (vertices.length % 3 != 0)
			throw new Error("Vertices must come in threes.");
		
		setVertices(vertices);
		
		linkAttributes("position", "color");
	}
	
	@Override
	public void draw(GLContext gl, Matrix transform) {
		gl.getCurrentProgram().setUniformValue("modelViewProjection", transform);
		drawArrays(gl, PrimitiveType.TRIANGLES);
	}
	
}

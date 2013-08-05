package nl.grauw.glengine.model;

import java.nio.ByteBuffer;

import nl.grauw.glengine.gl.GLEngineException;
import nl.grauw.glengine.gl.Utils;
import nl.grauw.glengine.model.Vertex.VertexInfo;

public class VertexArray {
	
	private final Vertex[] vertices;
	private ByteBuffer buffer;
	
	public <V extends Vertex> VertexArray(V... vertices) {
		if (vertices.length == 0)
			throw new GLEngineException("Must provide at least 1 vertex.");
		
		this.vertices = vertices;
	}
	
	public ByteBuffer getBuffer() {
		if (buffer == null)
			buffer = createBuffer();
		return buffer;
	}
	
	public ByteBuffer createBuffer() {
		ByteBuffer buffer = Utils.createByteBuffer(vertices.length * vertices[0].getVertexInfo().getSize());
		for (Vertex vertex : vertices)
			vertex.write(buffer);
		return buffer;
	}
	
	public int getLength() {
		return vertices.length;
	}
	
	public VertexInfo getVertexInfo() {
		return vertices[0].getVertexInfo();
	}
	
}

package nl.grauw.glengine.model;

import java.nio.ByteBuffer;

import nl.grauw.glengine.gl.GLEngineException;
import nl.grauw.glengine.gl.Utils;

public class VertexArray {
	
	private final ByteBuffer buffer;
	
	public VertexArray(Vertex... vertices) {
		if (vertices.length < 1)
			throw new GLEngineException("Must provide at least 1 vertex.");
		
		buffer = Utils.createByteBuffer(vertices.length * vertices[0].getVertexInfo().getSize());
		
		for (Vertex vertex : vertices)
			vertex.write(this);
	}
	
	public ByteBuffer getBuffer() {
		return buffer;
	}
	
	public void writeFloat(float scalar) {
		buffer.putFloat(scalar);
	}
	
	public void writeFloat2(Vector2 vector) {
		buffer.putFloat(vector.getX());
		buffer.putFloat(vector.getY());
	}
	
	public void writeFloat3(Vector3 vector) {
		buffer.putFloat(vector.getX());
		buffer.putFloat(vector.getY());
		buffer.putFloat(vector.getZ());
	}
	
	public void writeFloat4(Vector4 vector) {
		buffer.putFloat(vector.getX());
		buffer.putFloat(vector.getY());
		buffer.putFloat(vector.getZ());
		buffer.putFloat(vector.getW());
	}
	public void writeFloat4(Color color) {
		buffer.putFloat(color.getRed());
		buffer.putFloat(color.getGreen());
		buffer.putFloat(color.getBlue());
		buffer.putFloat(color.getAlpha());
	}
	
}

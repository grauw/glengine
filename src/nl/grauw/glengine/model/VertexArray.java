package nl.grauw.glengine.model;

import java.nio.ByteBuffer;

import nl.grauw.glengine.Utils;
import nl.grauw.glengine.gl.GLEngineException;
import nl.grauw.glengine.model.Vertex.VertexInfo;

public class VertexArray {
	
	private final VertexInfo vertexInfo;
	private final int length;
	
	private final ByteBuffer buffer;
	
	public VertexArray(VertexInfo vertexInfo, int length) {
		if (length < 1)
			throw new GLEngineException("Vertex array length must be at least 1.");
		if (vertexInfo == null)
			throw new GLEngineException("Must specify vertex info.");
		
		this.vertexInfo = vertexInfo;
		this.length = length;
		
		buffer = Utils.createByteBuffer(length * vertexInfo.getSize());
	}
	
	public VertexArray(Vertex... vertices) {
		this(vertices.length > 0 ? vertices[0].getInfo() : null, vertices.length);
		
		for (Vertex vertex : vertices) {
			if (vertex.getInfo() != vertexInfo)
				throw new GLEngineException("Incompatible vertex.");
			vertex.write(this);
		}
	}
	
	public VertexInfo getVertexInfo() {
		return vertexInfo;
	}
	
	public int getLength() {
		return length;
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
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[length=" + length + ", vertexInfo=" + vertexInfo + "]";
	}
	
}

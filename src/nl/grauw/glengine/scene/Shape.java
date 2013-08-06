package nl.grauw.glengine.scene;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import nl.grauw.glengine.gl.GLContext;
import nl.grauw.glengine.gl.GLEngineException;
import nl.grauw.glengine.gl.Utils;
import nl.grauw.glengine.gl.GLContext.PrimitiveType;
import nl.grauw.glengine.model.Vertex;
import nl.grauw.glengine.model.Vertex.VertexInfo;
import nl.grauw.glengine.model.Vertex.AttributeInfo;

public abstract class Shape implements Drawable {
	
	private ByteBuffer vertexBuffer;
	
	/**
	 * Return the vertices for this shape.
	 * 
	 * @return The array of vertices.
	 */
	protected abstract Vertex[] getVertices();
	
	protected ByteBuffer getVertexBuffer() {
		if (vertexBuffer == null)
			vertexBuffer = createVertexBuffer();
		return vertexBuffer;
	}
	
	private ByteBuffer createVertexBuffer() {
		Vertex[] vertices = getVertices();
		ByteBuffer buffer = Utils.createByteBuffer(vertices.length * vertices[0].getVertexInfo().getSize());
		for (Vertex vertex : vertices)
			vertex.write(buffer);
		return buffer;
	}
	
	protected void setVertexAttribPointers(GLContext gl, String... attributeNames) {
		int[] attributeIndices = new int[attributeNames.length];
		for (int i = 0, length = attributeNames.length; i < length; i++)
			attributeIndices[i] = gl.getCurrentProgram().getAttribute(attributeNames[i]).getLocation();
		setVertexAttribPointers(gl, attributeIndices);
	}
	
	protected void setVertexAttribPointers(GLContext gl, int... attributeIndices) {
		VertexInfo vertexInfo = getVertices()[0].getVertexInfo();
		AttributeInfo[] attributeInfos = vertexInfo.getAttributeInfo();
		
//		if (attributeInfos.length != attributeIndices.length)
//			throw new GLEngineException("Not enough or too many attribute names provided.");
		if (attributeInfos.length < attributeIndices.length)
			throw new GLEngineException("Too many attribute names provided.");
		
		ByteBuffer buffer = getVertexBuffer();
		int offset = 0;
		int vertexSize = vertexInfo.getSize();
		for (int i = 0, length = attributeIndices.length; i < length; i++) {
			buffer.position(offset);
			gl.setVertexAttribPointer(attributeIndices[i], attributeInfos[i], vertexSize, buffer);
			offset += attributeInfos[i].getSize();
		}
	}
	
	protected void drawArrays(GLContext gl, PrimitiveType type, String... attributeNames) {
		int[] attributeIndices = new int[attributeNames.length];
		for (int i = 0, length = attributeNames.length; i < length; i++)
			attributeIndices[i] = gl.getCurrentProgram().getAttribute(attributeNames[i]).getLocation();
		drawArrays(gl, type, attributeIndices);
	}
	
	protected void drawArrays(GLContext gl, PrimitiveType type, int... attributeIndices) {
		for (int attributeIndex : attributeIndices)
			gl.enableVertexAttribArray(attributeIndex);
		
		setVertexAttribPointers(gl, attributeIndices);
		
		gl.drawArrays(type, 0, getVertices().length);
		
		for (int attributeIndex : attributeIndices)
			gl.disableVertexAttribArray(attributeIndex);
	}
	
	protected void drawElements(GLContext gl, PrimitiveType type, Buffer indexes, String... attributeNames) {
		int[] attributeIndices = new int[attributeNames.length];
		for (int i = 0, length = attributeNames.length; i < length; i++)
			attributeIndices[i] = gl.getCurrentProgram().getAttribute(attributeNames[i]).getLocation();
		drawElements(gl, type, indexes, attributeIndices);
	}
	
	protected void drawElements(GLContext gl, PrimitiveType type, Buffer indexes, int... attributeIndices) {
		for (int attributeIndex : attributeIndices)
			gl.enableVertexAttribArray(attributeIndex);
		
		setVertexAttribPointers(gl, attributeIndices);
		
		gl.drawElements(type, indexes);
		
		for (int attributeIndex : attributeIndices)
			gl.disableVertexAttribArray(attributeIndex);
	}
	
}

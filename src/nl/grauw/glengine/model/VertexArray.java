package nl.grauw.glengine.model;

import java.nio.ByteBuffer;

import nl.grauw.glengine.gl.GLEngineException;
import nl.grauw.glengine.gl.Utils;

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
	
	public interface Vertex {
		
		// XXX: writeVertex? serialise? toByteBuffer?
		public void write(ByteBuffer buffer);
		
		public VertexInfo getVertexInfo();
		
	}
	
	public interface VertexAttribute {
		
		// XXX: writeAttribute? serialise? toByteBuffer?
		public void write(ByteBuffer buffer);
		
		public AttributeInfo getAttributeInfo();
		
	}
	
	public static class VertexInfo {
		
		private int size;
		private AttributeInfo[] attributes;
		
		public VertexInfo(AttributeInfo... attributes) {
			this.attributes = attributes;
			
			int size = 0;
			for (AttributeInfo attribute : attributes)
				size += attribute.getSize();
			
			this.size = size;
		}
		
		public int getSize() {
			return size;
		}
		
		public AttributeInfo[] getAttributeInfo() {
			return attributes;
		}
		
	}
	
	public static class AttributeInfo {
		
		private int components;
		private AttributeType type;
		private boolean normalized;
		
		protected AttributeInfo(int components, AttributeType type) {
			this(components, type, false);
		}
		
		protected AttributeInfo(int components, AttributeType type, boolean normalized) {
			this.components = components;
			this.type = type;
			this.normalized = normalized;
		}
		
		public int getComponents() {
			return components;
		}
		
		public int getSize() {
			return type.size * components;
		}
		
		public AttributeType getType() {
			return type;
		}
		
		public boolean isNormalized() {
			return normalized;
		}
		
	}
	
	public enum AttributeType {
		BYTE(1),
		UNSIGNED_BYTE(1),
		SHORT(2),
		UNSIGNED_SHORT(2),
		FLOAT(4),
		FIXED(4);
		
		final int size;
		private AttributeType(int size) {
			this.size = size;
		}
		
		public int getSize() {
			return size;
		}
	}
	
}

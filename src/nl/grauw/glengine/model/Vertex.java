package nl.grauw.glengine.model;

import java.nio.ByteBuffer;

import nl.grauw.glengine.gl.GLEngineException;

public abstract class Vertex {
	
	public abstract void write(ByteBuffer buffer);
	
	public abstract VertexInfo getVertexInfo();
	
	protected void writeFloat(ByteBuffer buffer, float scalar) {
		buffer.putFloat(scalar);
	}
	
	protected void writeFloat2(ByteBuffer buffer, Vector2 vector) {
		buffer.putFloat(vector.getX());
		buffer.putFloat(vector.getY());
	}
	
	protected void writeFloat3(ByteBuffer buffer, Vector3 vector) {
		buffer.putFloat(vector.getX());
		buffer.putFloat(vector.getY());
		buffer.putFloat(vector.getZ());
	}
	
	protected void writeFloat4(ByteBuffer buffer, Vector4 vector) {
		buffer.putFloat(vector.getX());
		buffer.putFloat(vector.getY());
		buffer.putFloat(vector.getZ());
		buffer.putFloat(vector.getW());
	}
	protected void writeFloat4(ByteBuffer buffer, Color color) {
		buffer.putFloat(color.getRed());
		buffer.putFloat(color.getGreen());
		buffer.putFloat(color.getBlue());
		buffer.putFloat(color.getAlpha());
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
		
		public AttributeInfo(int components, AttributeType type) {
			this(components, type, false);
		}
		
		public AttributeInfo(int components, AttributeType type, boolean normalized) {
			if (components < 1 || components > 4)
				throw new GLEngineException("Invalid number of components.");
			
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
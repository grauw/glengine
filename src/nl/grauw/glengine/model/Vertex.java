package nl.grauw.glengine.model;

import java.nio.ByteBuffer;

import nl.grauw.glengine.model.VertexAttribute.AttributeInfo;

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
	
}
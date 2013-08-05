package nl.grauw.glengine.model;

import java.nio.ByteBuffer;

import nl.grauw.glengine.model.VertexAttribute.AttributeInfo;

public interface Vertex {
	
	// XXX: writeVertex? serialise? toByteBuffer?
	public void write(ByteBuffer buffer);
	
	public VertexInfo getVertexInfo();
	
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
package nl.grauw.glengine.model;

import java.nio.ByteBuffer;

public interface VertexAttribute {
	
	// XXX: writeAttribute? serialise? toByteBuffer?
	public void write(ByteBuffer buffer);
	
	public AttributeInfo getAttributeInfo();
	
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
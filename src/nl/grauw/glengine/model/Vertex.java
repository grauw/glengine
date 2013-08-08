package nl.grauw.glengine.model;

import java.util.Arrays;

import nl.grauw.glengine.gl.GLEngineException;

public abstract class Vertex {
	
	public abstract void write(VertexArray array);
	
	public abstract VertexInfo getInfo();
	
	public VertexAttribute[] getAttributes() {
		return getInfo().getAttributes();
	}
	
	public static class VertexInfo {
		
		private int size;
		private AttributeInfo[] attributeInfos;
		private VertexAttribute[] attributes;
		
		public VertexInfo(AttributeInfo... attributeInfos) {
			this.attributeInfos = attributeInfos;
			this.attributes = new VertexAttribute[attributeInfos.length];
			
			int offset = 0;
			for (int i = 0; i < attributeInfos.length; i++) {
				attributes[i] = new VertexAttribute(i, attributeInfos[i], offset);
				offset += attributeInfos[i].getSize();
			}
			
			this.size = offset;
		}
		
		public int getSize() {
			return size;
		}
		
		public VertexAttribute[] getAttributes() {
			return attributes;
		}
		
		public AttributeInfo[] getAttributeInfo() {
			return attributeInfos;
		}
		
		@Override
		public String toString() {
			return getClass().getSimpleName() + "[size=" + size + ", attributes=" + Arrays.toString(attributes) + "]";
		}
		
	}
	
	public static class VertexAttribute {
		
		private final int index;
		private final AttributeInfo info;
		private final int offset;
		
		private VertexAttribute(int index, AttributeInfo attributeInfo, int offset) {
			this.index = index;
			this.info = attributeInfo;
			this.offset = offset;
		}
		
		public int getIndex() {
			return index;
		}
		
		public AttributeInfo getInfo() {
			return info;
		}
		
		public int getOffset() {
			return offset;
		}
		
		@Override
		public String toString() {
			return getClass().getSimpleName() + "[index=" + index + ", attributeInfo=" + info + ", offset=" + offset + "]";
		}
		
	}
	
	public static class AttributeInfo {
		
		private String linkName;
		private int components;
		private AttributeType type;
		private boolean normalized;
		
		public AttributeInfo(String linkName, int components, AttributeType type) {
			this(linkName, components, type, false);
		}
		
		public AttributeInfo(String linkName, int components, AttributeType type, boolean normalized) {
			if (components < 1 || components > 4)
				throw new GLEngineException("Invalid number of components.");
			
			this.linkName = linkName;
			this.components = components;
			this.type = type;
			this.normalized = normalized;
		}
		
		public String getLinkName() {
			return linkName;
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
		
		@Override
		public String toString() {
			return getClass().getSimpleName() + "[linkName=" + linkName + ", components=" + components + ", type=" + type + ", normalized=" + normalized + "]";
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
package nl.grauw.glengine.model;

import java.nio.ByteBuffer;

public class Point2 implements VertexAttribute {
	
	private final float x, y;
	
	public static final AttributeInfo attributeInfo = new AttributeInfo(2, AttributeType.FLOAT);
	
	public Point2(final float x, final float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	@Override
	public void write(ByteBuffer buffer) {
		buffer.putFloat(x);
		buffer.putFloat(y);
	}
	
	@Override
	public AttributeInfo getAttributeInfo() {
		return attributeInfo;
	}
	
}

package nl.grauw.glengine.model;

import java.nio.ByteBuffer;

public class Point implements VertexAttribute {
	
	private final float x, y, z;
	
	public static final AttributeInfo attributeInfo = new AttributeInfo(3, AttributeType.FLOAT);
	
	public Point(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}
	
	@Override
	public void write(ByteBuffer buffer) {
		buffer.putFloat(x);
		buffer.putFloat(y);
		buffer.putFloat(z);
	}
	
	@Override
	public AttributeInfo getAttributeInfo() {
		return attributeInfo;
	}
	
}

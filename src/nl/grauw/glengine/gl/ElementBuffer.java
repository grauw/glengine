package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import nl.grauw.glengine.gl.GLContext.ElementType;

public final class ElementBuffer extends Buffer {
	
	private ElementType type;
	
	public ElementBuffer(GLContext context) {
		super(context, GL_ELEMENT_ARRAY_BUFFER);
	}
	
	public ElementType getType() {
		return type;
	}
	
	public int getElementCount() {
		return type == ElementType.UNSIGNED_SHORT ? getSize() / 2 : getSize();
	}
	
	@Override
	public boolean isBound() {
		return context.getBoundElementBuffer() == this;
	}
	
	@Override
	public void setData(ByteBuffer data, BufferUsage usage) {
		type = ElementType.UNSIGNED_BYTE;
		super.setData(data, usage);
	}
	
	@Override
	public void setData(ShortBuffer data, BufferUsage usage) {
		type = ElementType.UNSIGNED_SHORT;
		super.setData(data, usage);
	}
	
	@Override
	public void setData(FloatBuffer data, BufferUsage usage) {
		throw new GLEngineException("Unsupported buffer data type.");
	}
	
	@Override
	public void setData(int size, java.nio.Buffer data, BufferUsage usage) {
		if (data instanceof ByteBuffer)
			type = ElementType.UNSIGNED_BYTE;
		else if (data instanceof ShortBuffer)
			type = ElementType.UNSIGNED_SHORT;
		else
			throw new GLEngineException("Unsupported buffer data type.");
		
		super.setData(size, data, usage);
	}
	
	@Override
	public void setSubData(int offset, int size, java.nio.Buffer data) {
		if (type == ElementType.UNSIGNED_BYTE && !(data instanceof ByteBuffer) || type == ElementType.UNSIGNED_SHORT && !(data instanceof ShortBuffer))
			throw new GLEngineException("Incompatible buffer data type.");
		
		super.setSubData(offset, size, data);
	}
	
}

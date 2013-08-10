package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

public final class ElementBuffer extends Buffer {
	
	private int type;
	
	public ElementBuffer(GLContext context) {
		super(context, GL_ELEMENT_ARRAY_BUFFER);
	}
	
	public int getType() {
		return type;
	}
	
	public int getElementCount() {
		return type == GL_UNSIGNED_SHORT ? getSize() / 2 : getSize();
	}
	
	@Override
	public boolean isBound() {
		return context.getBoundElementBuffer() == this;
	}
	
	@Override
	public void setData(int size, java.nio.Buffer data, BufferUsage usage) {
		if (data instanceof ByteBuffer)
			type = GL_UNSIGNED_BYTE;
		else if (data instanceof ShortBuffer)
			type = GL_UNSIGNED_SHORT;
		else
			throw new GLEngineException("Unsupported buffer data type.");
		
		super.setData(size, data, usage);
	}
	
	@Override
	public void setSubData(int offset, int size, java.nio.Buffer data) {
		if (type == GL_UNSIGNED_BYTE && !(data instanceof ByteBuffer) || type == GL_UNSIGNED_SHORT && !(data instanceof ShortBuffer))
			throw new GLEngineException("Incompatible buffer data type.");
		
		super.setSubData(offset, size, data);
	}
	
}

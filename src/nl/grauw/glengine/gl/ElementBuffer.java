package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
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
	
	public void setData(ShortBuffer data, BufferUsage usage) {
		setData(data.remaining(), data, usage);
	}
	
	@Override
	public void setData(int size, ByteBuffer data, BufferUsage usage) {
		type = ElementType.UNSIGNED_BYTE;
		super.setData(size, data, usage);
	}
	
	/** Note, size is specified in short units. */
	public void setData(int size, ShortBuffer data, BufferUsage usage) {
		type = ElementType.UNSIGNED_SHORT;
		super.setData(size * 2, data, usage);
	}
	
	@Override
	public void setSubData(int offset, int size, ByteBuffer data) {
		if (type != ElementType.UNSIGNED_BYTE)
			throw new GLEngineException("Incompatible buffer data type.");
		
		super.setSubData(offset, size, data);
	}
	
	/** Note, offset and size are specified in short units. */
	public void setSubData(int offset, int size, ShortBuffer data) {
		if (type != ElementType.UNSIGNED_SHORT)
			throw new GLEngineException("Incompatible buffer data type.");
		
		super.setSubData(offset * 2, size * 2, data);
	}
	
}

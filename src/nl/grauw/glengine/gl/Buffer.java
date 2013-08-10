package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public abstract class Buffer {
	
	protected final GLContext context;
	private final int target;
	private int size = -1;
	private BufferUsage usage = null;
	final int handle;
	
	public Buffer(GLContext context, int target) {
		this.context = context;
		this.target = target;
		handle = GLUtils.glGenBuffer();
	}
	
	public abstract boolean isBound();
	
	public int getSize() {
		if (size == -1)
			throw new GLEngineException("Buffer object is not allocated yet.");
		return size;
	}
	
	public BufferUsage getUsage() {
		if (usage == null)
			throw new GLEngineException("Buffer object is not allocated yet.");
		return usage;
	}
	
	public boolean isAllocated() {
		return size != -1;
	}
	
	public void allocate(int size, BufferUsage usage) {
		setData(size, null, usage);
	}
	
	public void setData(ByteBuffer data, BufferUsage usage) {
		setData(data.remaining() * 1, data, usage);
	}
	
	public void setData(ShortBuffer data, BufferUsage usage) {
		setData(data.remaining() * 2, data, usage);
	}
	
	public void setData(FloatBuffer data, BufferUsage usage) {
		setData(data.remaining() * 4, data, usage);
	}
	
	public void setData(int size, java.nio.Buffer data, BufferUsage usage) {
		if (size < 0)
			throw new GLEngineException("Size must be positive.");
		if (!isBound())
			throw new GLEngineException("Buffer object is not bound.");
		
		this.size = size;
		this.usage = usage;
		glBufferData(target, size, data, usage.id);
	}
	
	public void setSubData(int offset, int size, java.nio.Buffer data) {
		if (offset + size > this.size)
			throw new GLEngineException("Offset + size exceeds allocation.");
		if (!isAllocated())
			throw new GLEngineException("Buffer object is not allocated yet.");
		if (!isBound())
			throw new GLEngineException("Buffer object is not bound.");
		
		glBufferSubData(target, offset, size, data);
	}
	
	public void invalidate() {
		if (!isAllocated())
			throw new GLEngineException("Buffer object is not allocated yet.");
		setData(size, null, usage);
	}
	
	public void delete() {
		GLUtils.glDeleteBuffer(handle);
	}
	
	public enum BufferUsage {
		STATIC_DRAW(GL_STATIC_DRAW),
		DYNAMIC_DRAW(GL_DYNAMIC_DRAW),
		STREAM_DRAW(GL_STREAM_DRAW);
		
		final int id;
		private BufferUsage(int id) {
			this.id = id;
		}
	}
	
}

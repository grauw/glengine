package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public abstract class Buffer {
	
	protected final GLContext context;
	private final int target;
	private final int size;
	private final BufferUsage usage;
	private final int handle;
	
	public Buffer(GLContext context, int target, int size, BufferUsage usage) {
		this.context = context;
		this.target = target;
		this.size = size;
		this.usage = usage;
		handle = Utils.glGenBuffer();
	}
	
	public void setData(java.nio.Buffer data) {
		glBindBuffer(target, handle);
		glBufferData(target, size, data, usage.id);
	}
	
	public void setSubData(int offset, int size, java.nio.Buffer data) {
		glBindBuffer(target, handle);
		glBufferSubData(target, offset, size, data);
	}
	
	public void invalidate() {
		setData(null);
	}
	
	public void delete() {
		Utils.glDeleteBuffer(handle);
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

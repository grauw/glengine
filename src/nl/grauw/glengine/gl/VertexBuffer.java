package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public final class VertexBuffer extends Buffer {
	
	public VertexBuffer(GLContext context, int size, BufferUsage usage) {
		super(context, GL_ARRAY_BUFFER, size, usage);
	}
	
}

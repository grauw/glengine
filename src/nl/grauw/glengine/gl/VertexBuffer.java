package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public final class VertexBuffer extends Buffer {
	
	public VertexBuffer(GLContext context) {
		super(context, GL_ARRAY_BUFFER);
	}
	
	@Override
	public boolean isBound() {
		return context.getBoundVertexBuffer() == this;
	}
	
}

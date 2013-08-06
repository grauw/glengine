package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public class ElementBuffer extends Buffer {
	
	public ElementBuffer(GLContext context, int size, BufferUsage usage) {
		super(context, GL_ELEMENT_ARRAY_BUFFER, size, usage);
	}
	
}

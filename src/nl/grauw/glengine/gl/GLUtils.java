package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

import java.nio.IntBuffer;

import nl.grauw.glengine.Utils;

final class GLUtils {
	
	private static final IntBuffer ONE_INT_BUFFER = Utils.createIntBuffer(1);
	
	/**
	 * A one-liner integer get call that reuses the buffer.
	 * Note: may only be called on the OpenGL thread.
	 */
	static int glGetInteger(int parameter) {
		glGetIntegerv(parameter, ONE_INT_BUFFER);
		return ONE_INT_BUFFER.get(0);
	}
	
	/**
	 * A one-liner program integer get call that reuses the buffer.
	 * Note: may only be called on the OpenGL thread.
	 */
	static int glGetProgramInteger(int handle, int parameter) {
		glGetProgramiv(handle, parameter, ONE_INT_BUFFER);
		return ONE_INT_BUFFER.get(0);
	}
	
	/**
	 * A one-liner shader integer get call that reuses the buffer.
	 * Note: may only be called on the OpenGL thread.
	 */
	static int glGetShaderInteger(int handle, int parameter) {
		glGetShaderiv(handle, parameter, ONE_INT_BUFFER);
		return ONE_INT_BUFFER.get(0);
	}
	
	static int glGenTexture() {
		glGenTextures(1, GLUtils.ONE_INT_BUFFER);
		return GLUtils.ONE_INT_BUFFER.get(0);
	}
	
	static int glGenBuffer() {
		glGenBuffers(1, GLUtils.ONE_INT_BUFFER);
		return GLUtils.ONE_INT_BUFFER.get(0);
	}
	
	static void glDeleteTexture(int handle) {
		GLUtils.ONE_INT_BUFFER.put(0, handle);
		glDeleteTextures(1, GLUtils.ONE_INT_BUFFER);
	}
	
	static void glDeleteBuffer(int handle) {
		GLUtils.ONE_INT_BUFFER.put(0, handle);
		glDeleteBuffers(1, GLUtils.ONE_INT_BUFFER);
	}
	
}

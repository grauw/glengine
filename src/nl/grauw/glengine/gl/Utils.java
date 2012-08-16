package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class Utils {
	
	public static IntBuffer ONE_INT_BUFFER = createIntBuffer(1);
	
	/**
	 * A one-liner integer get call that reuses the buffer.
	 * Note: may only be called on the OpenGL thread.
	 */
	public static int glGetInteger(int parameter) {
		glGetIntegerv(parameter, ONE_INT_BUFFER);
		return ONE_INT_BUFFER.get(0);
	}
	
	/**
	 * A one-liner program integer get call that reuses the buffer.
	 * Note: may only be called on the OpenGL thread.
	 */
	public static int glGetProgramInteger(int handle, int parameter) {
		glGetProgramiv(handle, parameter, ONE_INT_BUFFER);
		return ONE_INT_BUFFER.get(0);
	}
	
	/**
	 * A one-liner shader integer get call that reuses the buffer.
	 * Note: may only be called on the OpenGL thread.
	 */
	public static int glGetShaderInteger(int handle, int parameter) {
		glGetShaderiv(handle, parameter, ONE_INT_BUFFER);
		return ONE_INT_BUFFER.get(0);
	}
	
	/**
	 * Allocate a native byte buffer suitable for GL.
	 */
	public static ByteBuffer createByteBuffer(int size) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(size);
		buffer.order(ByteOrder.nativeOrder());
		return buffer;
	}
	
	/**
	 * Allocate and initialize a native byte buffer suitable for GL.
	 */
	public static ByteBuffer createByteBuffer(byte[] content) {
		ByteBuffer buffer = createByteBuffer(content.length);
		buffer.put(content);
		buffer.position(0);
		return buffer;
	}
	
	/**
	 * Allocate and initialize a native byte buffer suitable for GL.
	 */
	public static ByteBuffer createByteBuffer(int[] content) {
		byte[] byteContent = new byte[content.length];
		for (int i = 0; i < content.length; i++)
			byteContent[i] = (byte) content[i];
		return createByteBuffer(byteContent);
	}
	
	/**
	 * Allocate a native short buffer suitable for GL.
	 */
	public static ShortBuffer createShortBuffer(int size) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(size * (Short.SIZE / Byte.SIZE));
		byteBuffer.order(ByteOrder.nativeOrder());
		ShortBuffer buffer = byteBuffer.asShortBuffer();
		return buffer;
	}
	
	/**
	 * Allocate and initialize a native short buffer suitable for GL.
	 */
	public static ShortBuffer createShortBuffer(short[] content) {
		ShortBuffer buffer = createShortBuffer(content.length);
		buffer.put(content);
		buffer.position(0);
		return buffer;
	}
	
	/**
	 * Allocate and initialize a native short buffer suitable for GL.
	 */
	public static ShortBuffer createShortBuffer(int[] content) {
		short[] shortContent = new short[content.length];
		for (int i = 0; i < content.length; i++)
			shortContent[i] = (short) content[i];
		return createShortBuffer(shortContent);
	}

	/**
	 * Allocate a native int buffer suitable for GL.
	 */
	public static IntBuffer createIntBuffer(int size) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(size * (Integer.SIZE / Byte.SIZE));
		byteBuffer.order(ByteOrder.nativeOrder());
		IntBuffer buffer = byteBuffer.asIntBuffer();
		return buffer;
	}
	
	/**
	 * Allocate and initialize a native int buffer suitable for GL.
	 */
	public static IntBuffer createIntBuffer(int[] content) {
		IntBuffer buffer = createIntBuffer(content.length);
		buffer.put(content);
		buffer.position(0);
		return buffer;
	}
	
	/**
	 * Allocate a native float buffer suitable for GL.
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(size * (Float.SIZE / Byte.SIZE));
		byteBuffer.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = byteBuffer.asFloatBuffer();
		return buffer;
	}
	
	/**
	 * Allocate and initialize a native float buffer suitable for GL.
	 */
	public static FloatBuffer createFloatBuffer(float[] content) {
		FloatBuffer buffer = createFloatBuffer(content.length);
		buffer.put(content);
		buffer.position(0);
		return buffer;
	}
	
}

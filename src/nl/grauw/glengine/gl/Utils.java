package nl.grauw.glengine.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class Utils {
	
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

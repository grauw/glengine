package nl.grauw.glengine.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class Utils {
	
	/**
	 * Utility function to simplify allocating a byte buffer suitable for GL.
	 * @param size The size of the buffer to create.
	 * @return The directly allocated byte buffer, with native byte ordering.
	 */
	public static ByteBuffer createByteBuffer(int size) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(size);
		buffer.order(ByteOrder.nativeOrder());
		return buffer;
	}
	
	/**
	 * Utility function to simplify allocating a byte buffer suitable for GL.
	 * @param content The content of the buffer to create.
	 * @return The directly allocated byte buffer, with native byte ordering.
	 */
	public static ByteBuffer createByteBuffer(byte[] content) {
		ByteBuffer buffer = createByteBuffer(content.length);
		buffer.put(content);
		buffer.position(0);
		return buffer;
	}
	
	/**
	 * Utility function to simplify allocating a byte buffer suitable for GL.
	 * The integer array provided as content is cast to bytes.
	 * @param content The content of the buffer to create.
	 * @return The directly allocated byte buffer, with native byte ordering.
	 */
	public static ByteBuffer createByteBuffer(int[] content) {
		byte[] byteContent = new byte[content.length];
		for (int i = 0; i < content.length; i++)
			byteContent[i] = (byte) content[i];
		return createByteBuffer(byteContent);
	}
	
	/**
	 * Utility function to simplify allocating a short buffer suitable for GL.
	 * @param size The size of the buffer to create.
	 * @return The directly allocated short buffer, with native byte ordering.
	 */
	public static ShortBuffer createShortBuffer(int size) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(size * (Short.SIZE / Byte.SIZE));
		byteBuffer.order(ByteOrder.nativeOrder());
		ShortBuffer buffer = byteBuffer.asShortBuffer();
		return buffer;
	}
	
	/**
	 * Utility function to simplify allocating a short buffer suitable for GL.
	 * @param content The content of the buffer to create.
	 * @return The directly allocated short buffer, with native byte ordering.
	 */
	public static ShortBuffer createShortBuffer(short[] content) {
		ShortBuffer buffer = createShortBuffer(content.length);
		buffer.put(content);
		buffer.position(0);
		return buffer;
	}
	
	/**
	 * Utility function to simplify allocating a short buffer suitable for GL.
	 * The integer array provided as content is cast to shorts.
	 * @param content The content of the buffer to create.
	 * @return The directly allocated short buffer, with native byte ordering.
	 */
	public static ShortBuffer createShortBuffer(int[] content) {
		short[] shortContent = new short[content.length];
		for (int i = 0; i < content.length; i++)
			shortContent[i] = (short) content[i];
		return createShortBuffer(shortContent);
	}
	
	/**
	 * Utility function to simplify allocating an int buffer suitable for GL.
	 * @param size The size of the buffer to create.
	 * @return The directly allocated int buffer, with native byte ordering.
	 */
	public static IntBuffer createIntBuffer(int size) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(size * (Integer.SIZE / Byte.SIZE));
		byteBuffer.order(ByteOrder.nativeOrder());
		IntBuffer buffer = byteBuffer.asIntBuffer();
		return buffer;
	}
	
	/**
	 * Utility function to simplify allocating an int buffer suitable for GL.
	 * @param content The content of the buffer to create.
	 * @return The directly allocated int buffer, with native byte ordering.
	 */
	public static IntBuffer createIntBuffer(int[] content) {
		IntBuffer buffer = createIntBuffer(content.length);
		buffer.put(content);
		buffer.position(0);
		return buffer;
	}
	
	/**
	 * Utility function to simplify allocating a float buffer suitable for GL.
	 * @param size The size of the buffer to create.
	 * @return The directly allocated float buffer, with native byte ordering.
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(size * (Float.SIZE / Byte.SIZE));
		byteBuffer.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = byteBuffer.asFloatBuffer();
		return buffer;
	}
	
	/**
	 * Utility function to simplify allocating a float buffer suitable for GL.
	 * @param content The content of the buffer to create.
	 * @return The directly allocated float buffer, with native byte ordering.
	 */
	public static FloatBuffer createFloatBuffer(float[] content) {
		FloatBuffer buffer = createFloatBuffer(content.length);
		buffer.put(content);
		buffer.position(0);
		return buffer;
	}
	
}

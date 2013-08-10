package nl.grauw.glengine;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import nl.grauw.glengine.gl.GLEngineException;

public final class Utils {
	
	public static String loadTextAsset(AssetManager assets, String path) {
		try {
			InputStream input = assets.open(path);
			InputStreamReader reader = new InputStreamReader(input, "UTF-8");
			StringBuilder text = new StringBuilder();
			
			int length;
			char[] buffer = new char[1024];
			while ((length = reader.read(buffer)) != -1)
				text.append(buffer, 0, length);
			
			return text.toString();
		} catch (IOException e) {
			throw new GLEngineException("Error reading file.", e);
		}
	}
	
	public static Bitmap loadBitmapAsset(AssetManager assets, String path) {
		try {
			return BitmapFactory.decodeStream(assets.open(path));
		} catch (IOException e) {
			throw new GLEngineException("Error reading file", e);
		}
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
	 * Allocate and initialize a native byte buffer suitable for GL.
	 */
	public static ByteBuffer createByteBuffer(short[] content) {
		ByteBuffer buffer = createByteBuffer(content.length * (Short.SIZE / Byte.SIZE));
		buffer.asShortBuffer().put(content);
		return buffer;
	}
	
	/**
	 * Allocate and initialize a native byte buffer suitable for GL.
	 */
	public static ByteBuffer createByteBuffer(float[] content) {
		ByteBuffer buffer = createByteBuffer(content.length * (Float.SIZE / Byte.SIZE));
		buffer.asFloatBuffer().put(content);
		return buffer;
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

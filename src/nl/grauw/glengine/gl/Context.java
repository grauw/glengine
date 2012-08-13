package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import nl.grauw.glengine.model.Color;

public class Context {
	
	private Program currentProgram = null;
	
	public void releaseShaderCompiler() {
		glReleaseShaderCompiler();
	}
	
	public void setClearColor(Color color) {
		glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	public void setClearColor(float red, float green, float blue, float alpha) {
		glClearColor(red, green, blue, alpha);
	}

	public void setClearDepth(float depth) {
		glClearDepthf(depth);
	}
	
	public void setClearStencil(int stencil) {
		glClearStencil(stencil);
	}
	
	public void clear(boolean colorBuffer, boolean depthBuffer, boolean stencilBuffer) {
		glClear(
			(colorBuffer ? GL_COLOR_BUFFER_BIT : 0) |
			(depthBuffer ? GL_DEPTH_BUFFER_BIT : 0) |
			(stencilBuffer ? GL_STENCIL_BUFFER_BIT : 0)
		);
	}
	
	public void setViewport(int x, int y, int width, int height) {
		glViewport(x, y, width, height);
	}
	
	public void useProgram(Program program) {
		glUseProgram(program.handle);
		currentProgram = program;
	}
	
	public Program getCurrentProgram() {
		return currentProgram;
	}
	
	public void setVertexAttribPointer(int index, int size, int stride, FloatBuffer buffer) {
		glVertexAttribPointer(index, size, GL_FLOAT, false, stride, buffer);
	}
	
	public void setVertexAttribPointer(int index, int size, int stride, boolean normalized, ByteBuffer buffer) {
		glVertexAttribPointer(index, size, GL_BYTE, normalized, stride, buffer);
	}
	
	public void setVertexAttribPointer(int index, int size, int stride, boolean normalized, ShortBuffer buffer) {
		glVertexAttribPointer(index, size, GL_SHORT, normalized, stride, buffer);
	}
	
	public void enableVertexAttribArray(int index) {
		glEnableVertexAttribArray(index);
	}
	
	public void disableVertexAttribArray(int index) {
		glDisableVertexAttribArray(index);
	}
	
	public void drawArrays(PrimitiveType type, int first, int count) {
		glDrawArrays(type.type, first, count);
	}
	
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
	
	public enum PrimitiveType {
		POINTS(GL_POINTS),
		LINE_STRIP(GL_LINE_STRIP),
		LINE_LOOP(GL_LINE_LOOP),
		LINES(GL_LINES),
		TRIANGLE_STRIP(GL_TRIANGLE_STRIP),
		TRIANGLE_FAN(GL_TRIANGLE_FAN),
		TRIANGLES(GL_TRIANGLES);
		
		int type;
		private PrimitiveType(int type) {
			this.type = type;
		}
	}
	
}

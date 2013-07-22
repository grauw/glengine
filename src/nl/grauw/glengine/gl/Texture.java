package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import android.graphics.Bitmap;

public abstract class Texture {
	
	protected final Context context;
	protected final int type;
	final int handle;
	
	protected Texture(Context context, int type, int handle) {
		this.context = context;
		this.type = type;
		this.handle = handle;
	}
	
	protected Texture(Context context, int type) {
		this(context, type, Utils.glGenTexture());
	}
	
	public void delete() {
		if (handle == 0)
			throw new GLEngineException("Can not delete default texture.");
		glDeleteTextures(1, new int[] { handle }, 0);
		context.getActiveTextureUnit().signalDeletion(this);
	}
	
	protected abstract void checkIsBound();
	
	public void setMinificationFilter(MinificationFilter filter) {
		checkIsBound();
		glTexParameteri(type, GL_TEXTURE_MIN_FILTER, filter.id);
	}
	
	public void setMagnificationFilter(MagnificationFilter filter) {
		checkIsBound();
		glTexParameteri(type, GL_TEXTURE_MAG_FILTER, filter.id);
	}
	
	public void setWrapModeS(WrapMode wrapMode) {
		checkIsBound();
		glTexParameteri(type, GL_TEXTURE_WRAP_S, wrapMode.id);
	}
	
	public void setWrapModeT(WrapMode wrapMode) {
		checkIsBound();
		glTexParameteri(type, GL_TEXTURE_WRAP_T, wrapMode.id);
	}
	
	protected void setImage(int target, int level, int width, int height,
			PixelFormat format, PixelDataType dataType, Buffer pixels) {
		if (pixels != null && pixels.limit() - pixels.position() < width * height * dataType.getBytesPerPixel(format))
			throw new GLEngineException("Pixel buffer too small.");
		checkIsBound();
		glTexImage2D(target, level, format.id, width, height, 0, format.id, dataType.id, pixels);
	}
	
	protected void setImage(int target, int level, Bitmap bitmap) {
		int byteCount = bitmap.getWidth() * bitmap.getHeight() * (bitmap.getConfig() == Bitmap.Config.ARGB_8888 ? 4 : 2);
		ByteBuffer textureBuffer = Utils.createByteBuffer(byteCount);
		bitmap.copyPixelsToBuffer(textureBuffer);
		textureBuffer.position(0);
		
		setImage(
			target,
			level,
			bitmap.getWidth(),
			bitmap.getHeight(),
			bitmap.getConfig() == Bitmap.Config.RGB_565 ? PixelFormat.RGB : PixelFormat.RGBA,
			bitmap.getConfig() == Bitmap.Config.RGB_565 ? PixelDataType.UNSIGNED_SHORT_5_6_5 : PixelDataType.UNSIGNED_BYTE,
			textureBuffer
		);
	}
	
	protected void setSubImage(int target, int level, int xOffset, int yOffset, int width, int height,
			PixelFormat format, PixelDataType dataType, Buffer pixels) {
		if (pixels != null && pixels.limit() - pixels.position() < width * height * dataType.getBytesPerPixel(format))
			throw new GLEngineException("Pixel buffer too small.");
		checkIsBound();
		glTexSubImage2D(target, level, xOffset, yOffset, width, height, format.id, dataType.id, pixels);
	}
	
	protected void copyImage(int target, int level, int x, int y, int width, int height, PixelFormat format) {
		checkIsBound();
		glCopyTexImage2D(target, level, format.id, x, y, width, height, 0);
	}
	
	protected void copySubImage(int target, int level, int xOffset, int yOffset, int x, int y, int width, int height) {
		checkIsBound();
		glCopyTexSubImage2D(target, level, xOffset, yOffset, x, y, width, height);
	}
	
	public void generateMipmaps() {
		glGenerateMipmap(type);
	}
	
	public enum MinificationFilter {
		NEAREST(GL_NEAREST),
		LINEAR(GL_LINEAR),
		NEAREST_MIPMAP_NEAREST(GL_NEAREST_MIPMAP_NEAREST),
		NEAREST_MIPMAP_LINEAR(GL_NEAREST_MIPMAP_NEAREST),
		LINEAR_MIPMAP_NEAREST(GL_LINEAR_MIPMAP_NEAREST),
		LINEAR_MIPMAP_LINEAR(GL_LINEAR_MIPMAP_LINEAR);
		
		final int id;
		private MinificationFilter(final int id) {
			this.id = id;
		}
	}
	
	public enum MagnificationFilter {
		NEAREST(GL_NEAREST),
		LINEAR(GL_LINEAR);
		
		final int id;
		private MagnificationFilter(final int id) {
			this.id = id;
		}
	}
	
	public enum WrapMode {
		REPEAT(GL_REPEAT),
		CLAMP_TO_EDGE(GL_CLAMP_TO_EDGE),
		MIRRORED_REPEAT(GL_MIRRORED_REPEAT);
		
		final int id;
		private WrapMode(final int id) {
			this.id = id;
		}
	}
	
}

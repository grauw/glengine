package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

import java.nio.Buffer;

import android.graphics.Bitmap;

public class Texture2D extends Texture {
	
	public Texture2D(Context context) {
		super(context, GL_TEXTURE_2D);
	}
	
	Texture2D(Context context, int handle) {
		super(context, GL_TEXTURE_2D, handle);
	}
	
	@Override
	public void checkIsBound() {
		if (context.getActiveTextureUnit().getBoundTexture2D() != this)
			throw new Error("Texture not bound.");
	}
	
	public void setImage(int level, int width, int height, PixelFormat format, PixelDataType dataType, Buffer pixels) {
		super.setImage(GL_TEXTURE_2D, level, width, height, format, dataType, pixels);
	}
	
	public void setImage(int level, Bitmap bitmap) {
		super.setImage(GL_TEXTURE_2D, level, bitmap);
	}
	
	public void setSubImage(int level, int xOffset, int yOffset, int width, int height,
			PixelFormat format, PixelDataType dataType, Buffer pixels) {
		super.setSubImage(GL_TEXTURE_2D, level, xOffset, yOffset, width, height, format, dataType, pixels);
	}
	
	public void copyImage(int level, int x, int y, int width, int height, PixelFormat format) {
		super.copyImage(GL_TEXTURE_2D, level, x, y, width, height, format);
	}
	
	public void copySubImage(int level, int xOffset, int yOffset, int x, int y, int width, int height) {
		super.copySubImage(GL_TEXTURE_2D, level, xOffset, yOffset, x, y, width, height);
	}
	
}

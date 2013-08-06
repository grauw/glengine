package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

import java.nio.Buffer;

import android.graphics.Bitmap;

public class TextureCubeMap extends Texture {
	
	public TextureCubeMap(GLContext context) {
		super(context, GL_TEXTURE_CUBE_MAP);
	}
	
	TextureCubeMap(GLContext context, int handle) {
		super(context, GL_TEXTURE_CUBE_MAP, handle);
	}
	
	@Override
	public void checkIsBound() {
		if (context.getActiveTextureUnit().getBoundTextureCubeMap() != this)
			throw new Error("Texture not bound.");
	}
	
	public void setImage(CubeFace target, int level, int width, int height,
			PixelFormat format, PixelDataType dataType, Buffer pixels) {
		super.setImage(target.id, level, width, height, format, dataType, pixels);
	}
	
	public void setImage(CubeFace target, int level, Bitmap bitmap) {
		super.setImage(target.id, level, bitmap);
	}
	
	public void setSubImage(CubeFace target, int level, int xOffset, int yOffset, int width, int height,
			PixelFormat format, PixelDataType dataType, Buffer pixels) {
		super.setSubImage(target.id, level, xOffset, yOffset, width, height, format, dataType, pixels);
	}
	
	public void copyImage(CubeFace target, int level, int x, int y, int width, int height, PixelFormat format) {
		super.copyImage(target.id, level, x, y, width, height, format);
	}
	
	public void copySubImage(CubeFace target, int level, int xOffset, int yOffset, int x, int y, int width, int height) {
		super.copySubImage(target.id, level, xOffset, yOffset, x, y, width, height);
	}
	
	public enum CubeFace {
		POSITIVE_X(GL_TEXTURE_CUBE_MAP_POSITIVE_X),
		NEGATIVE_X(GL_TEXTURE_CUBE_MAP_NEGATIVE_X),
		POSITIVE_Y(GL_TEXTURE_CUBE_MAP_POSITIVE_Y),
		NEGATIVE_Y(GL_TEXTURE_CUBE_MAP_NEGATIVE_Y),
		POSITIVE_Z(GL_TEXTURE_CUBE_MAP_POSITIVE_Z),
		NEGATIVE_Z(GL_TEXTURE_CUBE_MAP_NEGATIVE_Z);
		
		private final int id;
		private CubeFace(int id) {
			this.id = id;
		}
	}
	
}

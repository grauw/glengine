package nl.grauw.glengine.scene;

import java.io.IOException;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import nl.grauw.glengine.gl.GLContext;
import nl.grauw.glengine.gl.GLEngineException;
import nl.grauw.glengine.gl.Texture;
import nl.grauw.glengine.gl.Texture2D;
import nl.grauw.glengine.gl.Texture.MagnificationFilter;
import nl.grauw.glengine.gl.Texture.MinificationFilter;

public abstract class TextureLoader {
	
	private final AssetManager assets;
	
	public TextureLoader(AssetManager assets) {
		this.assets = assets;
	}
	
	public abstract Texture getTexture();
	
	public abstract void initialize(GLContext gl);
	
	public Bitmap loadBitmapAsset(String path) {
		try {
			return BitmapFactory.decodeStream(assets.open(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static class TextureLoader2D extends TextureLoader {
		
		private final String path;
		private final boolean generateMipmaps;
		
		private Texture2D texture;
		private Bitmap textureBitmap;
		
		public TextureLoader2D(AssetManager assets, String path, boolean generateMipmaps) {
			super(assets);
			this.path = path;
			this.generateMipmaps = generateMipmaps;
		}
		
		public TextureLoader2D(AssetManager assets, String path) {
			this(assets, path, true);
		}
		
		@Override
		public Texture2D getTexture() {
			if (texture == null)
				throw new GLEngineException("Texture not available before initialization.");
			return texture;
		}
		
		@Override
		public void initialize(GLContext gl) {
			texture = new Texture2D(gl);
			load();
			initializeTexture(gl);
		}
		
		private void load() {
			textureBitmap = loadBitmapAsset(path);
		}
		
		private void initializeTexture(GLContext gl) {
			gl.bindTexture(0, texture);
			
			texture.setImage(0, textureBitmap);
			textureBitmap = null;
			
			if (generateMipmaps)
				texture.generateMipmaps();
			
			texture.setMinificationFilter(generateMipmaps ? MinificationFilter.LINEAR_MIPMAP_LINEAR : MinificationFilter.LINEAR);
			texture.setMagnificationFilter(MagnificationFilter.LINEAR);
		}
		
	}
	
}

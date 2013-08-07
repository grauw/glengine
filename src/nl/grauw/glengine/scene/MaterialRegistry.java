package nl.grauw.glengine.scene;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import nl.grauw.glengine.gl.GLContext;
import nl.grauw.glengine.gl.GLEngineException;

public class MaterialRegistry {
	
	private final AssetManager assets;
	private final ArrayList<Material> materials = new ArrayList<Material>();
	
	public MaterialRegistry(AssetManager assets) {
		this.assets = assets;
	}
	
	public <M extends Material> M register(M material) {
		materials.add(material);
		return material;
	}
	
	public void initialize(GLContext gl) {
		for (Material material : materials)
			material.initialize(gl);
		
		gl.releaseShaderCompiler();
	}
	
	public void update(int elapsed) {
		for (Material material : materials)
			material.update(elapsed);
	}
	
	public String loadTextAsset(String path) {
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
			throw new GLEngineException("Error reading file", e);
		}
	}
	
	public Bitmap loadBitmapAsset(String path) {
		try {
			return BitmapFactory.decodeStream(assets.open(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}

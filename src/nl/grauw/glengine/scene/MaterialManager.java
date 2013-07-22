package nl.grauw.glengine.scene;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import nl.grauw.glengine.gl.GLEngineException;

import android.content.res.AssetManager;

public class MaterialManager {
	
	AssetManager assets;
	
	public MaterialManager(AssetManager assets) {
		this.assets = assets;
	}
	
	public Material createMaterial(File vertexSource, File fragmentSource) {
		return new Material(
			loadTextAsset(vertexSource),
			loadTextAsset(fragmentSource)
		);
	}
	
	private String loadTextAsset(File file) {
		try {
			InputStream input = assets.open(file.getPath());
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
	
}

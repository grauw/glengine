package nl.grauw.glengine.scene;

import java.util.ArrayList;

import android.content.res.AssetManager;

import nl.grauw.glengine.Utils;
import nl.grauw.glengine.gl.GLContext;

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
		return Utils.loadTextAsset(assets, path);
	}
	
}

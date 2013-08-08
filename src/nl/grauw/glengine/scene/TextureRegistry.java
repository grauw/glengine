package nl.grauw.glengine.scene;

import java.util.ArrayList;

import nl.grauw.glengine.gl.GLContext;

public class TextureRegistry {
	
	private final ArrayList<TextureLoader> textures = new ArrayList<TextureLoader>();
	
	public <T extends TextureLoader> T register(T texture) {
		textures.add(texture);
		return texture;
	}
	
	public void initialize(GLContext gl) {
		for (TextureLoader texture : textures)
			texture.initialize(gl);
	}
	
}

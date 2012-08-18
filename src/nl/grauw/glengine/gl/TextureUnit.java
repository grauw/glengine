package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public class TextureUnit {
	
	private final Context context;
	private final int index;
	
	private Texture2D defaultTexture2D;
	private TextureCubeMap defaultTextureCubeMap;
	private Texture2D boundTexture2D;
	private TextureCubeMap boundTextureCubeMap;
	
	TextureUnit(Context context, int index) {
		this.context = context;
		this.index = index;
		
		defaultTexture2D = new Texture2D(context, 0);
		defaultTextureCubeMap = new TextureCubeMap(context, 0);
		boundTexture2D = defaultTexture2D;
		boundTextureCubeMap = defaultTextureCubeMap;
	}
	
	public int getIndex() {
		return index;
	}
	
	public Texture2D getDefaultTexture2D() {
		return defaultTexture2D;
	}
	
	public TextureCubeMap getDefaultTextureCubeMap() {
		return defaultTextureCubeMap;
	}
	
	public Texture2D getBoundTexture2D() {
		return boundTexture2D;
	}
	
	public TextureCubeMap getBoundTextureCubeMap() {
		return boundTextureCubeMap;
	}
	
	public void bind(Texture2D texture) {
		if (context.getActiveTextureUnit() != this)
			throw new GLEngineException("Texture unit not active.");
		
		glBindTexture(texture.type, texture.handle);
		boundTexture2D = texture;
	}
	
	public void bind(TextureCubeMap texture) {
		if (context.getActiveTextureUnit() != this)
			throw new GLEngineException("Texture unit not active.");
		
		glBindTexture(texture.type, texture.handle);
		boundTextureCubeMap = texture;
	}
	
	void signalDeletion(Texture texture) {
		if (texture == boundTexture2D)
			boundTexture2D = defaultTexture2D;
		else if (texture == boundTextureCubeMap)
			boundTextureCubeMap = defaultTextureCubeMap;
	}
	
}

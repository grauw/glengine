package nl.grauw.glengine.testactivity;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;

public class TestSurfaceView extends GLSurfaceView {
	
	private TestRenderer renderer;
	
	public TestSurfaceView(Context context) {
		super(context);
		
		Bitmap texture = getTextureBitmap();
		
		renderer = new TestRenderer(texture);
		
		setEGLContextClientVersion(2);
		
		setRenderer(renderer);
	}
	
	public Bitmap getTextureBitmap() {
		AssetManager assets = getContext().getAssets();
		try {
			return BitmapFactory.decodeStream(assets.open("texture.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}

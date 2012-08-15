package nl.grauw.hellotriangle;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GameSurfaceView extends GLSurfaceView {
	
	GameRenderer renderer;
	
	public GameSurfaceView(Context context) {
		super(context);
		
		renderer = new GameRenderer();
		
		setEGLContextClientVersion(2);
		
		setRenderer(renderer);
	}
	
}

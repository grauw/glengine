package nl.grauw.glengine.testactivity;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class TestSurfaceView extends GLSurfaceView {
	
	TestRenderer renderer;
	
	public TestSurfaceView(Context context) {
		super(context);
		
		renderer = new TestRenderer();
		
		setEGLContextClientVersion(2);
		
		setRenderer(renderer);
	}
	
}

package nl.grauw.hellotriangle;

import javax.microedition.khronos.egl.EGLConfig;

import nl.grauw.glengine.GLEngineRenderer;
import nl.grauw.glengine.gl.Context;

public class GameRenderer extends GLEngineRenderer {
	
	@Override
	public void onDrawFrame(Context gl) {
		gl.clear(true, false, false);
	}

	@Override
	public void onSurfaceChanged(Context gl, int width, int height) {
		gl.setViewport(0, 0, width, height);
	}

	@Override
	public void onSurfaceCreated(Context gl, EGLConfig config) {
		gl.setClearColor(1, 0, 0, 1);
	}

}

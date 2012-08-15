package nl.grauw.glengine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import nl.grauw.glengine.gl.Context;

import android.opengl.GLSurfaceView.Renderer;

public abstract class GLEngineRenderer implements Renderer {

	private Context gl;
	
	@Override
	public void onDrawFrame(GL10 gl10) {
		if (gl == null)
			throw new RuntimeException("GLEngine context not available.");
		onDrawFrame(gl);
	}
	
	abstract public void onDrawFrame(Context gl);
	
	@Override
	public void onSurfaceChanged(GL10 gl10, int width, int height) {
		if (gl == null)
			throw new RuntimeException("GLEngine context not available.");
		onSurfaceChanged(gl, width, height);
	}
	
	abstract public void onSurfaceChanged(Context gl, int width, int height);
	
	@Override
	public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
		gl = new Context();
		CapabilitiesLogger.log(gl);
		onSurfaceCreated(gl, config);
	}
	
	abstract public void onSurfaceCreated(Context gl, EGLConfig config);
	
}

package nl.grauw.glengine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import nl.grauw.glengine.gl.GLContext;
import nl.grauw.glengine.gl.GLEngineException;

import android.opengl.GLSurfaceView.Renderer;

public abstract class GLEngineRenderer implements Renderer {

	private GLContext context;
	
	@Override
	public void onDrawFrame(GL10 gl) {
		if (context == null)
			throw new GLEngineException("GLEngine context not available.");
		onDrawFrame(context);
		
		context.checkError();
	}
	
	abstract public void onDrawFrame(GLContext context);
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if (context == null)
			throw new GLEngineException("GLEngine context not available.");
		onSurfaceChanged(context, width, height);
		
		context.checkError();
	}
	
	abstract public void onSurfaceChanged(GLContext context, int width, int height);
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		context = new GLContext();
		CapabilitiesLogger.log(context);
		onSurfaceCreated(context, config);
		
		context.checkError();
	}
	
	abstract public void onSurfaceCreated(GLContext context, EGLConfig config);
	
}

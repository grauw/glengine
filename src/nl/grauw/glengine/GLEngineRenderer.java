package nl.grauw.glengine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import nl.grauw.glengine.gl.GLContext;
import nl.grauw.glengine.gl.GLEngineException;

import android.opengl.GLSurfaceView.Renderer;
import android.os.SystemClock;

public abstract class GLEngineRenderer implements Renderer {
	
	private GLContext context;
	
	private long lastUpdateTime = 0;
	
	abstract public void onDrawFrame(GLContext context, int elapsed);
	
	abstract public void onSurfaceChanged(GLContext context, int width, int height);
	
	abstract public void onSurfaceCreated(GLContext context, EGLConfig config);
	
	@Override
	public final void onDrawFrame(GL10 gl) {
		if (context == null)
			throw new GLEngineException("GLEngine context not available.");
		
		long time = SystemClock.uptimeMillis();
		int elapsed = lastUpdateTime == 0 ? 0 : (int) (time - lastUpdateTime);
		lastUpdateTime = time;
		
		onDrawFrame(context, elapsed);
		
		context.checkError();
	}
	
	@Override
	public final void onSurfaceChanged(GL10 gl, int width, int height) {
		if (context == null)
			throw new GLEngineException("GLEngine context not available.");
		
		onSurfaceChanged(context, width, height);
		
		context.checkError();
	}
	
	@Override
	public final void onSurfaceCreated(GL10 gl, EGLConfig config) {
		context = new GLContext();
		CapabilitiesLogger.log(context);
		
		onSurfaceCreated(context, config);
		
		context.checkError();
	}
	
}

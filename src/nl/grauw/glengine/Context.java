package nl.grauw.glengine;

import static android.opengl.GLES20.*;

public class Context {
	
	public static Context currentContext;
	
	public Context() {
		if (currentContext != null)
			throw new Error("A context already exists.");
		currentContext = this;
	}
	
	public void releaseShaderCompiler() {
		glReleaseShaderCompiler();
	}
	
}

package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public abstract class Shader {
	
	final int handle;
	
	private Shader(int type) {
		handle = glCreateShader(type);
		
		if (handle == 0)
			throw new GLEngineException("Failed to create shader.");
	}
	
	protected void delete() {
		glDeleteShader(handle);
	}
	
	public void load(String source) {
		setSource(source);
		compile();
	}
	
	public void setSource(String source) {
		glShaderSource(handle, source);
	}
	
	public void compile() {
		glCompileShader(handle);
		
		if (!getCompileStatus()) {
			throw new GLEngineException("Failed to compile shader.\n%s", getInfoLog());
		}
	}
	
	// XXX: rename to isCompiled()?
	public boolean getCompileStatus() {
		return GLUtils.glGetShaderInteger(handle, GL_COMPILE_STATUS) == GL_TRUE;
	}
	
	public String getInfoLog() {
		return glGetShaderInfoLog(handle);
	}
	
	public final static class VertexShader extends Shader {
		public VertexShader() {
			super(GL_VERTEX_SHADER);
		}
	}
	
	public final static class FragmentShader extends Shader {
		public FragmentShader() {
			super(GL_FRAGMENT_SHADER);
		}
	}
	
}

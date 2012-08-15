package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public abstract class Shader {
	
	int handle;
	
	private Shader(int type) {
		handle = glCreateShader(type);
		
		if (handle == 0)
			throw new Error("Failed to create shader.");
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
			throw new RuntimeException("Failed to compile shader.\n" + getInfoLog());
		}
	}
	
	// XXX: rename to isCompiled()?
	public boolean getCompileStatus() {
		return getShaderiv(GL_COMPILE_STATUS) == GL_TRUE;
	}
	
	public String getInfoLog() {
		return glGetShaderInfoLog(handle);
	}
	
	private int getShaderiv(int parameter) {
		int[] params = new int[1];
		glGetShaderiv(handle, parameter, params, 0);
		return params[0];
	}
	
	public static class VertexShader extends Shader {
		public VertexShader() {
			super(GL_VERTEX_SHADER);
		}
	}
	
	public static class FragmentShader extends Shader {
		public FragmentShader() {
			super(GL_FRAGMENT_SHADER);
		}
	}
	
}

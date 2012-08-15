package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

import nl.grauw.glengine.gl.Shader.FragmentShader;
import nl.grauw.glengine.gl.Shader.VertexShader;

public class Program {
	
	int handle;
	private VertexShader vertexShader = null;
	private FragmentShader fragmentShader = null;
	
	public Program() {
		handle = glCreateProgram();
		
		if (handle == 0)
			throw new Error("Failed to create program.");
	}
	
	protected void delete() {
		glDeleteProgram(handle);
	}
	
	public VertexShader getVertexShader() {
		return vertexShader;
	}
	
	public FragmentShader getFragmentShader() {
		return fragmentShader;
	}
	
	public void attachShader(VertexShader shader) {
		if (vertexShader != null)
			throw new RuntimeException("A vertex shader was already attached.");
		
		glAttachShader(handle, shader.handle);
		vertexShader = shader;
	}
	
	public void attachShader(FragmentShader shader) {
		if (fragmentShader != null)
			throw new RuntimeException("A fragment shader was already attached.");
		
		glAttachShader(handle, shader.handle);
		fragmentShader = shader;
	}
	
	public void detachShader(VertexShader shader) {
		if (vertexShader != shader)
			throw new RuntimeException("Shader is not attached to this program.");
		
		glDetachShader(handle, shader.handle);
		vertexShader = null;
	}
	
	public void detachShader(FragmentShader shader) {
		if (fragmentShader != shader)
			throw new RuntimeException("Shader is not attached to this program.");
		
		glDetachShader(handle, shader.handle);
		fragmentShader = null;
	}
	
	public void bindAttribLocation(int index, String name) {
		// TODO: if (isMatrix ? index >= getMaxVertexAttributes() - 3 : getMaxVertexAttributes()) throw ...
		glBindAttribLocation(handle, index, name);
	}
	
	public void link() {
		glLinkProgram(handle);
		
		if (!getLinkStatus()) {
			throw new RuntimeException("Failed to link program.\n" + getInfoLog());
		}
	}
	
	// XXX: rename to isLinked()?
	public boolean getLinkStatus() {
		return getProgramiv(GL_LINK_STATUS) == GL_TRUE;
	}
	
	private String getInfoLog() {
		return glGetProgramInfoLog(handle);
	}

	public void validate() {
		glValidateProgram(handle);
	}
	
	public boolean getValidateStatus() {
		return getProgramiv(GL_VALIDATE_STATUS) == GL_TRUE;
	}
	
	private int getProgramiv(int parameter) {
		int[] params = new int[1];
		glGetProgramiv(handle, parameter, params, 0);
		return params[0];
	}
	
}

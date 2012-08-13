package nl.grauw.glengine;

import static android.opengl.GLES20.*;

import nl.grauw.glengine.Shader.FragmentShader;
import nl.grauw.glengine.Shader.VertexShader;

public class Program {
	
	private int handle;
	private VertexShader vertexShader = null;
	private FragmentShader fragmentShader = null;
	
	public Program() {
		handle = glCreateProgram();
		
		if (handle == 0)
			throw new Error("Failed to create program.");
	}
	
	@Override
	protected void finalize() throws Throwable {
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
		glBindAttribLocation(handle, index, name);
	}
	
}

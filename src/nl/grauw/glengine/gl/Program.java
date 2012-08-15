package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;
import android.util.Log;

import nl.grauw.glengine.gl.Shader.FragmentShader;
import nl.grauw.glengine.gl.Shader.VertexShader;

public class Program {
	
	int handle;
	private VertexShader vertexShader = null;
	private FragmentShader fragmentShader = null;
	private Variable[] activeAttributes = {};
	private Variable[] activeUniforms = {};
	
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
		
		updateActiveAttributes();
		updateActiveUniforms();
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
	
	public Variable[] getActiveAttributes() {
		return activeAttributes.clone();
	}
	
	public Variable getAttribute(String name) {
		for (Variable attribute : activeAttributes)
			if (name.equals(attribute.getName()))
				return attribute;
		return null;
	}
	
	public void updateActiveAttributes() {
		int count = getProgramiv(GL_ACTIVE_ATTRIBUTES);
		int maxNameLength = getProgramiv(GL_ACTIVE_ATTRIBUTE_MAX_LENGTH);
		int[] nameLengthBuffer = new int[1];
		int[] sizeBuffer = new int[1];
		int[] typeBuffer = new int[1];
		byte[] nameBuffer = new byte[maxNameLength];
		
		activeAttributes = new Variable[count];
		for (int index = 0; index < count; index++) {
			glGetActiveAttrib(handle, index, maxNameLength, nameLengthBuffer, 0, sizeBuffer, 0, typeBuffer, 0, nameBuffer, 0);
			String name = new String(nameBuffer, 0, nameLengthBuffer[0]);
			int location = glGetAttribLocation(handle, name);
			activeAttributes[index] = new Variable(location, name, sizeBuffer[0], typeBuffer[0]);
		}
	}
	
	public Variable[] getActiveUniforms() {
		return activeUniforms.clone();
	}
	
	public Variable getUniform(String name) {
		for (Variable uniform : activeUniforms)
			if (name.equals(uniform.getName()))
				return uniform;
		return null;
	}
	
	public void updateActiveUniforms() {
		int count = getProgramiv(GL_ACTIVE_UNIFORMS);
		int maxNameLength = getProgramiv(GL_ACTIVE_UNIFORM_MAX_LENGTH);
		int[] nameLengthBuffer = new int[1];
		int[] sizeBuffer = new int[1];
		int[] typeBuffer = new int[1];
		byte[] nameBuffer = new byte[maxNameLength];
		
		activeUniforms = new Variable[count];
		for (int index = 0; index < count; index++) {
			glGetActiveUniform(handle, index, maxNameLength, nameLengthBuffer, 0, sizeBuffer, 0, typeBuffer, 0, nameBuffer, 0);
			String name = new String(nameBuffer, 0, nameLengthBuffer[0]);
			int location = glGetUniformLocation(handle, name);
			activeUniforms[index] = new Variable(location, name, sizeBuffer[0], typeBuffer[0]);
		}
	}
	
	private int getProgramiv(int parameter) {
		int[] params = new int[1];
		glGetProgramiv(handle, parameter, params, 0);
		return params[0];
	}
	
}

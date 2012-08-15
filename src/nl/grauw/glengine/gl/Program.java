package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import nl.grauw.glengine.gl.Shader.FragmentShader;
import nl.grauw.glengine.gl.Shader.VertexShader;
import nl.grauw.glengine.gl.Variable.VariableType;

public class Program {
	
	private Context gl;
	int handle;
	
	private VertexShader vertexShader = null;
	private FragmentShader fragmentShader = null;
	private Map<String, Variable> activeAttributes = Collections.emptyMap();
	private Map<String, Variable> activeUniforms = Collections.emptyMap();
	
	public Program(Context gl) {
		this.gl = gl;
		handle = glCreateProgram();
		
		if (handle == 0)
			throw new RuntimeException("Failed to create program.");
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
		
		for (Variable v : activeAttributes.values())
			Log.d("GGG", "Attribute: " + v.getName() + ", size: " + v.getSize() + ", type: " + v.getType() + ", location: " + v.getLocation());
		for (Variable v : activeUniforms.values())
			Log.d("GGG", "Uniform: " + v.getName() + ", size: " + v.getSize() + ", type: " + v.getType() + ", location: " + v.getLocation());
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
	
	public Map<String, Variable> getActiveAttributes() {
		return Collections.unmodifiableMap(activeAttributes);
	}
	
	public Variable getAttribute(String name) {
		return activeAttributes.get(name);
	}
	
	public void updateActiveAttributes() {
		int count = getProgramiv(GL_ACTIVE_ATTRIBUTES);
		int maxNameLength = getProgramiv(GL_ACTIVE_ATTRIBUTE_MAX_LENGTH);
		int[] nameLengthBuffer = new int[1];
		int[] sizeBuffer = new int[1];
		int[] typeBuffer = new int[1];
		byte[] nameBuffer = new byte[maxNameLength];
		
		activeAttributes = new HashMap<String, Variable>(count);
		for (int index = 0; index < count; index++) {
			glGetActiveAttrib(handle, index, maxNameLength, nameLengthBuffer, 0, sizeBuffer, 0, typeBuffer, 0, nameBuffer, 0);
			String name = new String(nameBuffer, 0, nameLengthBuffer[0]);
			int location = glGetAttribLocation(handle, name);
			activeAttributes.put(name, new Variable(location, name, sizeBuffer[0], VariableType.getById(typeBuffer[0])));
		}
	}
	
	public Map<String, Variable> getActiveUniforms() {
		return Collections.unmodifiableMap(activeUniforms);
	}
	
	public Variable getUniform(String name) {
		return activeUniforms.get(name);
	}
	
	public void updateActiveUniforms() {
		int count = getProgramiv(GL_ACTIVE_UNIFORMS);
		int maxNameLength = getProgramiv(GL_ACTIVE_UNIFORM_MAX_LENGTH);
		int[] nameLengthBuffer = new int[1];
		int[] sizeBuffer = new int[1];
		int[] typeBuffer = new int[1];
		byte[] nameBuffer = new byte[maxNameLength];
		
		activeUniforms = new HashMap<String, Variable>(count);
		for (int index = 0; index < count; index++) {
			glGetActiveUniform(handle, index, maxNameLength, nameLengthBuffer, 0, sizeBuffer, 0, typeBuffer, 0, nameBuffer, 0);
			String name = new String(nameBuffer, 0, nameLengthBuffer[0]);
			int location = glGetUniformLocation(handle, name);
			activeUniforms.put(name, new Variable(location, name, sizeBuffer[0], VariableType.getById(typeBuffer[0])));
		}
	}
	
	public void setUniformValue(int location, float x) {
		checkCurrentProgram();
		glUniform1f(location, x);
	}
	
	public void setUniformValue(int location, float x, float y) {
		checkCurrentProgram();
		glUniform2f(location, x, y);
	}
	
	public void setUniformValue(int location, float x, float y, float z) {
		checkCurrentProgram();
		glUniform3f(location, x, y, z);
	}
	
	public void setUniformValue(int location, float x, float y, float z, float w) {
		checkCurrentProgram();
		glUniform4f(location, x, y, z, w);
	}
	
	private void checkCurrentProgram() {
		if (gl.getCurrentProgram() != this)
			throw new RuntimeException("Must be current program to set uniform variable.");
	}
	
	private int getProgramiv(int parameter) {
		int[] params = new int[1];
		glGetProgramiv(handle, parameter, params, 0);
		return params[0];
	}
	
}

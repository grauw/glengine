package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import nl.grauw.glengine.gl.Shader.FragmentShader;
import nl.grauw.glengine.gl.Shader.VertexShader;
import nl.grauw.glengine.gl.Variable.Attribute;
import nl.grauw.glengine.gl.Variable.Uniform;
import nl.grauw.glengine.gl.Variable.VariableType;
import nl.grauw.glengine.model.Matrix;

public class Program {
	
	private final GLContext context;
	final int handle;
	
	private VertexShader vertexShader = null;
	private FragmentShader fragmentShader = null;
	private Map<String, Attribute> activeAttributes = Collections.emptyMap();
	private Map<String, Uniform> activeUniforms = Collections.emptyMap();
	
	public Program(GLContext context) {
		this.context = context;
		handle = glCreateProgram();
		
		if (handle == 0)
			throw new GLEngineException("Failed to create program.");
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
			throw new GLEngineException("A vertex shader was already attached.");
		
		glAttachShader(handle, shader.handle);
		vertexShader = shader;
	}
	
	public void attachShader(FragmentShader shader) {
		if (fragmentShader != null)
			throw new GLEngineException("A fragment shader was already attached.");
		
		glAttachShader(handle, shader.handle);
		fragmentShader = shader;
	}
	
	public void detachShader(VertexShader shader) {
		if (vertexShader != shader)
			throw new GLEngineException("Shader is not attached to this program.");
		
		glDetachShader(handle, shader.handle);
		vertexShader = null;
	}
	
	public void detachShader(FragmentShader shader) {
		if (fragmentShader != shader)
			throw new GLEngineException("Shader is not attached to this program.");
		
		glDetachShader(handle, shader.handle);
		fragmentShader = null;
	}
	
	public void bindAttribLocation(int index, String name) {
		glBindAttribLocation(handle, index, name);
	}
	
	public void link() {
		glLinkProgram(handle);
		
		if (!getLinkStatus()) {
			throw new GLEngineException("Failed to link program.\n%s", getInfoLog());
		}
		
		updateActiveAttributes();
		updateActiveUniforms();
	}
	
	// XXX: rename to isLinked()?
	public boolean getLinkStatus() {
		return Utils.glGetProgramInteger(handle, GL_LINK_STATUS) == GL_TRUE;
	}
	
	public String getInfoLog() {
		return glGetProgramInfoLog(handle);
	}
	
	public void validate() {
		glValidateProgram(handle);
	}
	
	public boolean getValidateStatus() {
		return Utils.glGetProgramInteger(handle, GL_VALIDATE_STATUS) == GL_TRUE;
	}
	
	public Map<String, Attribute> getActiveAttributes() {
		return Collections.unmodifiableMap(activeAttributes);
	}
	
	public Attribute getAttribute(String name) {
		return activeAttributes.get(name);
	}
	
	private void updateActiveAttributes() {
		int count = Utils.glGetProgramInteger(handle, GL_ACTIVE_ATTRIBUTES);
		int maxNameLength = Utils.glGetProgramInteger(handle, GL_ACTIVE_ATTRIBUTE_MAX_LENGTH);
		int[] nameLengthBuffer = new int[1];
		int[] sizeBuffer = new int[1];
		int[] typeBuffer = new int[1];
		byte[] nameBuffer = new byte[maxNameLength];
		
		activeAttributes = new HashMap<String, Attribute>(count);
		for (int index = 0; index < count; index++) {
			glGetActiveAttrib(handle, index, maxNameLength, nameLengthBuffer, 0, sizeBuffer, 0, typeBuffer, 0, nameBuffer, 0);
			String name = new String(nameBuffer, 0, nameLengthBuffer[0]);
			if (sizeBuffer[0] > 1 && name.endsWith("[0]"))
				name = name.substring(0, name.length() - 3);
			int location = glGetAttribLocation(handle, name);
			activeAttributes.put(name, new Attribute(location, name, sizeBuffer[0], VariableType.getById(typeBuffer[0])));
		}
	}
	
	public Map<String, Uniform> getActiveUniforms() {
		return Collections.unmodifiableMap(activeUniforms);
	}
	
	public Uniform getUniform(String name) {
		return activeUniforms.get(name);
	}
	
	private void updateActiveUniforms() {
		int count = Utils.glGetProgramInteger(handle, GL_ACTIVE_UNIFORMS);
		int maxNameLength = Utils.glGetProgramInteger(handle, GL_ACTIVE_UNIFORM_MAX_LENGTH);
		int[] nameLengthBuffer = new int[1];
		int[] sizeBuffer = new int[1];
		int[] typeBuffer = new int[1];
		byte[] nameBuffer = new byte[maxNameLength];
		
		activeUniforms = new HashMap<String, Uniform>(count);
		for (int index = 0; index < count; index++) {
			glGetActiveUniform(handle, index, maxNameLength, nameLengthBuffer, 0, sizeBuffer, 0, typeBuffer, 0, nameBuffer, 0);
			String name = new String(nameBuffer, 0, nameLengthBuffer[0]);
			if (sizeBuffer[0] > 1 && name.endsWith("[0]"))
				name = name.substring(0, name.length() - 3);
			int location = glGetUniformLocation(handle, name);
			activeUniforms.put(name, new Uniform(location, name, sizeBuffer[0], VariableType.getById(typeBuffer[0])));
		}
	}
	
	public void setUniformValue(String uniformName, float x) {
		setUniformValue(getUniform(uniformName), x);
	}
	
	public void setUniformValue(String uniformName, float x, float y) {
		setUniformValue(getUniform(uniformName), x, y);
	}
	
	public void setUniformValue(String uniformName, float x, float y, float z) {
		setUniformValue(getUniform(uniformName), x, y, z);
	}
	
	public void setUniformValue(String uniformName, float x, float y, float z, float w) {
		setUniformValue(getUniform(uniformName), x, y, z, w);
	}
	
	public void setUniformValue(Uniform uniform, float x) {
		checkCurrentProgram();
		glUniform1f(uniform.getLocation(), x);
	}
	
	public void setUniformValue(Uniform uniform, float x, float y) {
		checkCurrentProgram();
		glUniform2f(uniform.getLocation(), x, y);
	}
	
	public void setUniformValue(Uniform uniform, float x, float y, float z) {
		checkCurrentProgram();
		glUniform3f(uniform.getLocation(), x, y, z);
	}
	
	public void setUniformValue(Uniform uniform, float x, float y, float z, float w) {
		checkCurrentProgram();
		glUniform4f(uniform.getLocation(), x, y, z, w);
	}
	
	public void setUniformValue(String uniformName, Matrix matrix) {
		setUniformValue(getUniform(uniformName), matrix);
	}
	
	public void setUniformValue(Uniform uniform, Matrix matrix) {
		checkCurrentProgram();
		float[] elements = matrix.getElements();
		if (matrix.getColumns() == 4 && matrix.getRows() == 4)
			glUniformMatrix4fv(uniform.getLocation(), 1, false, elements, 0);
		else if (matrix.getColumns() == 3 && matrix.getRows() == 3)
			glUniformMatrix3fv(uniform.getLocation(), 1, false, elements, 0);
		else if (matrix.getColumns() == 2 && matrix.getRows() == 2)
			glUniformMatrix2fv(uniform.getLocation(), 1, false, elements, 0);
		else if (elements.length == 4)
			glUniform4f(uniform.getLocation(), elements[0], elements[1], elements[2], elements[3]);
		else if (elements.length == 3)
			glUniform3f(uniform.getLocation(), elements[0], elements[1], elements[2]);
		else if (elements.length == 2)
			glUniform2f(uniform.getLocation(), elements[0], elements[1]);
		else if (elements.length == 1)
			glUniform1f(uniform.getLocation(), elements[0]);
		else
			throw new GLEngineException("Unsupported matrix dimensions.");
	}
	
	public void setUniformValue(String uniformName, TextureUnit unit) {
		setUniformValue(getUniform(uniformName), unit);
	}
	
	public void setUniformValue(Uniform uniform, TextureUnit unit) {
		checkCurrentProgram();
		glUniform1i(uniform.getLocation(), unit.getIndex());
	}
	
	private void checkCurrentProgram() {
		if (context.getCurrentProgram() != this)
			throw new GLEngineException("Must be current program to set uniform variable.");
	}
	
}

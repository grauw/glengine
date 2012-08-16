package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import nl.grauw.glengine.gl.GLEngineException.OpenGLException;
import nl.grauw.glengine.gl.Variable.Attribute;
import nl.grauw.glengine.model.Size;

public class Context {
	
	private Program currentProgram = null;
	private final ColorBuffer colorBuffer = new ColorBuffer();
	private final DepthBuffer depthBuffer = new DepthBuffer();
	private final StencilBuffer stencilBuffer = new StencilBuffer();
	
	private final String vendor;
	private final String renderer;
	private final String version;
	private final String shadingLanguageVersion;
	private final String[] extensions;
	private final int maxCombinedTextureImageUnits;
	private final int maxCubeMapTextureSize;
	private final int maxFragmentUniformVectors;
	private final int maxRenderBufferSize;
	private final int maxTextureImageUnits;
	private final int maxTextureSize;
	private final int maxVaryingVectors;
	private final int maxVertexAttribs;
	private final int maxVertexTextureImageUnits;
	private final int maxVertexUniformVectors;
	private final Size maxViewportDims;
	private final int[] compressedTextureFormats;
	private final int[] shaderBinaryFormats;
	
	public Context() {
		vendor = glGetString(GL_VENDOR);
		renderer = glGetString(GL_RENDERER);
		version = glGetString(GL_VERSION);
		shadingLanguageVersion = glGetString(GL_SHADING_LANGUAGE_VERSION);
		extensions = glGetString(GL_EXTENSIONS).split(" ");
		
		maxCombinedTextureImageUnits = Utils.glGetInteger(GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS);
		maxCubeMapTextureSize = Utils.glGetInteger(GL_MAX_CUBE_MAP_TEXTURE_SIZE);
		maxFragmentUniformVectors = Utils.glGetInteger(GL_MAX_FRAGMENT_UNIFORM_VECTORS);
		maxRenderBufferSize = Utils.glGetInteger(GL_MAX_RENDERBUFFER_SIZE);
		maxTextureImageUnits = Utils.glGetInteger(GL_MAX_TEXTURE_IMAGE_UNITS);
		maxTextureSize = Utils.glGetInteger(GL_MAX_TEXTURE_SIZE);
		maxVaryingVectors = Utils.glGetInteger(GL_MAX_VARYING_VECTORS);
		maxVertexAttribs = Utils.glGetInteger(GL_MAX_VERTEX_ATTRIBS);
		maxVertexTextureImageUnits = Utils.glGetInteger(GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS);
		maxVertexUniformVectors = Utils.glGetInteger(GL_MAX_VERTEX_UNIFORM_VECTORS);
		
		int[] maxViewportDimsArray = new int[2];
		glGetIntegerv(GL_MAX_VIEWPORT_DIMS, maxViewportDimsArray, 0);
		maxViewportDims = new Size(maxViewportDimsArray[0], maxViewportDimsArray[1]);
		
		compressedTextureFormats = new int[Utils.glGetInteger(GL_NUM_COMPRESSED_TEXTURE_FORMATS)];
		glGetIntegerv(GL_COMPRESSED_TEXTURE_FORMATS, compressedTextureFormats, 0);
		
		shaderBinaryFormats = new int[Utils.glGetInteger(GL_NUM_SHADER_BINARY_FORMATS)];
		glGetIntegerv(GL_SHADER_BINARY_FORMATS, shaderBinaryFormats, 0);
	}
	
	public String getVendor() {
		return vendor;
	}
	
	public String getRenderer() {
		return renderer;
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getShadingLanguageVersion() {
		return shadingLanguageVersion;
	}
	
	public String[] getExtensions() {
		return extensions.clone();
	}
	
	public int getMaxCombinedTextureImageUnits() {
		return maxCombinedTextureImageUnits;
	}
	
	public int getMaxCubeMapTextureSize() {
		return maxCubeMapTextureSize;
	}
	
	public int getMaxFragmentUniformVectors() {
		return maxFragmentUniformVectors;
	}
	
	public int getMaxRenderBufferSize() {
		return maxRenderBufferSize;
	}
	
	public int getMaxTextureImageUnits() {
		return maxTextureImageUnits;
	}
	
	public int getMaxTextureSize() {
		return maxTextureSize;
	}
	
	public int getMaxVaryingVectors() {
		return maxVaryingVectors;
	}
	
	public int getMaxVertexAttribs() {
		return maxVertexAttribs;
	}
	
	public int getMaxVertexTextureImageUnits() {
		return maxVertexTextureImageUnits;
	}
	
	public int getMaxVertexUniformVectors() {
		return maxVertexUniformVectors;
	}
	
	public Size getMaxViewportDims() {
		return maxViewportDims;
	}
	
	public int[] getCompressedTextureFormats() {
		return compressedTextureFormats.clone();
	}
	
	public int[] getShaderBinaryFormats() {
		return shaderBinaryFormats.clone();
	}
	
	public void releaseShaderCompiler() {
		glReleaseShaderCompiler();
	}
	
	public ColorBuffer getColorBuffer() {
		return colorBuffer;
	}
	
	public DepthBuffer getDepthBuffer() {
		return depthBuffer;
	}
	
	public StencilBuffer getStencilBuffer() {
		return stencilBuffer;
	}
	
	public void clear(boolean colorBuffer, boolean depthBuffer, boolean stencilBuffer) {
		glClear(
			(colorBuffer ? GL_COLOR_BUFFER_BIT : 0) |
			(depthBuffer ? GL_DEPTH_BUFFER_BIT : 0) |
			(stencilBuffer ? GL_STENCIL_BUFFER_BIT : 0)
		);
	}
	
	public void setViewport(int x, int y, int width, int height) {
		glViewport(x, y, width, height);
	}
	
	public void enableScissorTest() {
		glEnable(GL_SCISSOR_TEST);
	}
	
	public void disableScissorTest() {
		glDisable(GL_SCISSOR_TEST);
	}
	
	public boolean isScissorTestEnabled() {
		return glIsEnabled(GL_SCISSOR_TEST);
	}
	
	public void setScissor(int x, int y, int width, int height) {
		glScissor(x, y, width, height);
	}
	
	public void useProgram(Program program) {
		glUseProgram(program.handle);
		currentProgram = program;
	}
	
	public Program getCurrentProgram() {
		return currentProgram;
	}
	
	public void setVertexAttribPointer(String attributeName, int size, int stride, FloatBuffer buffer) {
		setVertexAttribPointer(currentProgram.getAttribute(attributeName), size, stride, buffer);
	}
	
	public void setVertexAttribPointer(String attributeName, int size, int stride, boolean normalized, ByteBuffer buffer) {
		setVertexAttribPointer(currentProgram.getAttribute(attributeName), size, stride, normalized, buffer);
	}
	
	public void setVertexAttribPointer(String attributeName, int size, int stride, boolean normalized, ShortBuffer buffer) {
		setVertexAttribPointer(currentProgram.getAttribute(attributeName), size, stride, normalized, buffer);
	}
	
	public void setVertexAttribPointer(Attribute attribute, int size, int stride, FloatBuffer buffer) {
		setVertexAttribPointer(attribute.getLocation(), size, stride, buffer);
	}
	
	public void setVertexAttribPointer(Attribute attribute, int size, int stride, boolean normalized, ByteBuffer buffer) {
		setVertexAttribPointer(attribute.getLocation(), size, stride, normalized, buffer);
	}
	
	public void setVertexAttribPointer(Attribute attribute, int size, int stride, boolean normalized, ShortBuffer buffer) {
		setVertexAttribPointer(attribute.getLocation(), size, stride, normalized, buffer);
	}
	
	public void setVertexAttribPointer(int index, int size, int stride, FloatBuffer buffer) {
		glVertexAttribPointer(index, size, GL_FLOAT, false, stride, buffer);
	}
	
	public void setVertexAttribPointer(int index, int size, int stride, boolean normalized, ByteBuffer buffer) {
		glVertexAttribPointer(index, size, GL_BYTE, normalized, stride, buffer);
	}
	
	public void setVertexAttribPointer(int index, int size, int stride, boolean normalized, ShortBuffer buffer) {
		glVertexAttribPointer(index, size, GL_SHORT, normalized, stride, buffer);
	}
	
	public void enableVertexAttribArray(String attributeName) {
		enableVertexAttribArray(currentProgram.getAttribute(attributeName));
	}
	
	public void disableVertexAttribArray(String attributeName) {
		disableVertexAttribArray(currentProgram.getAttribute(attributeName));
	}
	
	public void enableVertexAttribArray(Attribute attribute) {
		enableVertexAttribArray(attribute.getLocation());
	}
	
	public void disableVertexAttribArray(Attribute attribute) {
		disableVertexAttribArray(attribute.getLocation());
	}
	
	public void enableVertexAttribArray(int index) {
		glEnableVertexAttribArray(index);
	}
	
	public void disableVertexAttribArray(int index) {
		glDisableVertexAttribArray(index);
	}
	
	public void drawArrays(PrimitiveType type, int first, int count) {
		glDrawArrays(type.id, first, count);
	}
	
	public void checkError() {
		int flag = glGetError();
		switch (flag) {
			case GL_NO_ERROR:
				return;
			case GL_INVALID_ENUM:
				throw new OpenGLException("Invalid enum.");
			case GL_INVALID_VALUE:
				throw new OpenGLException("Invalid value.");
			case GL_INVALID_OPERATION:
				throw new OpenGLException("Invalid operation.");
			case GL_INVALID_FRAMEBUFFER_OPERATION:
				throw new OpenGLException("Invalid framebuffer operation.");
			case GL_OUT_OF_MEMORY:
				throw new OpenGLException("Invalid out of memory.");
			default:
				throw new OpenGLException("Unrecognised error %s.", flag);
		}
	}
	
	public enum PrimitiveType {
		POINTS(GL_POINTS),
		LINE_STRIP(GL_LINE_STRIP),
		LINE_LOOP(GL_LINE_LOOP),
		LINES(GL_LINES),
		TRIANGLE_STRIP(GL_TRIANGLE_STRIP),
		TRIANGLE_FAN(GL_TRIANGLE_FAN),
		TRIANGLES(GL_TRIANGLES);
		
		final int id;
		private PrimitiveType(final int id) {
			this.id = id;
		}
	}
	
}

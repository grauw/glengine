package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public final class DepthBuffer {
	
	private final int depthBits;
	
	public DepthBuffer() {
		depthBits = GLUtils.glGetInteger(GL_DEPTH_BITS);
	}
	
	public int getDepthBits() {
		return depthBits;
	}
	
	public void setClearDepth(float depth) {
		glClearDepthf(depth);
	}
	
	public void clear() {
		glClear(GL_DEPTH_BUFFER_BIT);
	}
	
	public void setRange(float near, float far) {
		glDepthRangef(near, far);
	}
	
	public void setWriteMask(boolean mask) {
		glDepthMask(mask);
	}
	
	public void enableDepthTest() {
		glEnable(GL_DEPTH_TEST);
	}
	
	public void disableDepthTest() {
		glDisable(GL_DEPTH_TEST);
	}
	
	public boolean isDepthTestEnabled() {
		return glIsEnabled(GL_DEPTH_TEST);
	}
	
	public void setFunction(CompareFunction function) {
		glDepthFunc(function.id);
	}
	
}

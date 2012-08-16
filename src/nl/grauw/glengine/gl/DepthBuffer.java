package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public class DepthBuffer {
	
	private final int depthBits;
	
	public DepthBuffer() {
		int[] params = new int[4];
		glGetIntegerv(GL_DEPTH_BITS, params, 0);
		depthBits = params[0];
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

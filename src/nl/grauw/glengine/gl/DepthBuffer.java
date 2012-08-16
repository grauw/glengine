package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public class DepthBuffer {
	
	private final int depthBits;
	private boolean mask = true;
	private float clearDepth = 1;
	
	public DepthBuffer() {
		int[] params = new int[4];
		glGetIntegerv(GL_DEPTH_BITS, params, 0);
		depthBits = params[0];
	}
	
	public int getDepthBits() {
		return depthBits;
	}
	
	public void setMask(boolean writeEnabled) {
		glDepthMask(writeEnabled);
		mask = writeEnabled;
	}
	
	public boolean getMask() {
		return mask;
	}
	
	public void setClearDepth(float depth) {
		glClearDepthf(depth);
		clearDepth = depth;
	}
	
	public float getClearDepth() {
		return clearDepth;
	}
	
	public void clear() {
		glClear(GL_DEPTH_BUFFER_BIT);
	}
	
	public void setDepthFunction(CompareFunction function) {
		glDepthFunc(function.id);
	}
	
}

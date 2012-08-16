package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public class DepthBuffer {
	
	private final int depthBits;
	private float clearDepth = 1;
	private float rangeNear = 0;
	private float rangeFar = 1;
	private boolean writeMask = true;
	private CompareFunction function = CompareFunction.LESS;
	
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
		clearDepth = depth;
	}
	
	public float getClearDepth() {
		return clearDepth;
	}
	
	public void clear() {
		glClear(GL_DEPTH_BUFFER_BIT);
	}
	
	public void setRange(float near, float far) {
		glDepthRangef(near, far);
		rangeNear = near;
		rangeFar = far;
	}
	
	public float getRangeNear() {
		return rangeNear;
	}
	
	public float getRangeFar() {
		return rangeFar;
	}
	
	public void setWriteMask(boolean mask) {
		glDepthMask(mask);
		writeMask = mask;
	}
	
	public boolean getWriteMask() {
		return writeMask;
	}
	
	public void enableDepthTest() {
		glEnable(GL_DEPTH_TEST);
	}
	
	public void disableDepthTest() {
		glDisable(GL_DEPTH_TEST);
	}
	
	public void setFunction(CompareFunction function) {
		glDepthFunc(function.id);
		this.function = function;
	}
	
	public CompareFunction getFunction() {
		return function;
	}
	
}

package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public class StencilBuffer {
	
	private final int stencilBits;
	private int frontMask = -1;
	private int backMask = -1;
	private int clearStencil = 0;
	
	public StencilBuffer() {
		int[] params = new int[4];
		glGetIntegerv(GL_STENCIL_BITS, params, 0);
		stencilBits = params[0];
	}
	
	public int getStencilBits() {
		return stencilBits;
	}
	
	public void setMask(int mask) {
		glStencilMask(mask);
		frontMask = backMask = mask;
	}
	
	public void setFrontMask(int mask) {
		glStencilMaskSeparate(GL_FRONT, mask);
		frontMask = mask;
	}
	
	public void setBackMask(int mask) {
		glStencilMaskSeparate(GL_BACK, mask);
		backMask = mask;
	}
	
	public int getFrontMask() {
		return frontMask;
	}
	
	public int getBackMask() {
		return backMask;
	}
	
	public void setClearStencil(int stencil) {
		glClearStencil(stencil);
		clearStencil = stencil;
	}
	
	public int getClearStencil() {
		return clearStencil;
	}
	
	public void clear() {
		glClear(GL_STENCIL_BUFFER_BIT);
	}
	
}

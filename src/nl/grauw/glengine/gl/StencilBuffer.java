package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public class StencilBuffer {
	
	private final int stencilBits;
	private int clearStencil = 0;
	private int frontMask = -1;
	private int backMask = -1;
	
	public StencilBuffer() {
		int[] params = new int[4];
		glGetIntegerv(GL_STENCIL_BITS, params, 0);
		stencilBits = params[0];
	}
	
	public int getStencilBits() {
		return stencilBits;
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
	
	public void setStencilFunction(CompareFunction function, int ref, int mask) {
		glStencilFunc(function.id, ref, mask);
	}
	
	public void setFrontFunction(CompareFunction function, int ref, int mask) {
		glStencilFuncSeparate(GL_FRONT, function.id, ref, mask);
	}
	
	public void setBackFunction(CompareFunction function, int ref, int mask) {
		glStencilFuncSeparate(GL_BACK, function.id, ref, mask);
	}
	
	public void setOperator(StencilOperator stencilFail, StencilOperator depthFail, StencilOperator pass) {
		glStencilOp(stencilFail.id, depthFail.id, pass.id);
	}
	
	public void setFrontOperator(StencilOperator stencilFail, StencilOperator depthFail, StencilOperator pass) {
		glStencilOpSeparate(GL_FRONT, stencilFail.id, depthFail.id, pass.id);
	}
	
	public void setBackOperator(StencilOperator stencilFail, StencilOperator depthFail, StencilOperator pass) {
		glStencilOpSeparate(GL_BACK, stencilFail.id, depthFail.id, pass.id);
	}
	
	public enum StencilOperator {
		ZERO(GL_ZERO),
		REPLACE(GL_REPLACE),
		INCREMENT(GL_INCR),
		DECREMENT(GL_DECR),
		INCREMENT_WRAP(GL_INCR_WRAP),
		DECREMENT_WRAP(GL_DECR_WRAP),
		KEEP(GL_KEEP),
		INVERT(GL_INVERT);
		
		final int id;
		private StencilOperator(final int id) {
			this.id = id;
		}
	}
	
}

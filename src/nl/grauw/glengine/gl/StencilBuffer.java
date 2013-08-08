package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public final class StencilBuffer {
	
	private final int stencilBits;
	
	public StencilBuffer() {
		stencilBits = GLUtils.glGetInteger(GL_STENCIL_BITS);
	}
	
	public int getStencilBits() {
		return stencilBits;
	}
	
	public void setClearStencil(int stencil) {
		glClearStencil(stencil);
	}
	
	public void clear() {
		glClear(GL_STENCIL_BUFFER_BIT);
	}
	
	public void setWriteMask(int mask) {
		glStencilMask(mask);
	}
	
	public void setFrontWriteMask(int mask) {
		glStencilMaskSeparate(GL_FRONT, mask);
	}
	
	public void setBackWriteMask(int mask) {
		glStencilMaskSeparate(GL_BACK, mask);
	}
	
	public void enableStencilTest() {
		glEnable(GL_STENCIL_TEST);
	}
	
	public void disableStencilTest() {
		glDisable(GL_STENCIL_TEST);
	}
	
	public boolean isStencilTestEnabled() {
		return glIsEnabled(GL_STENCIL_TEST);
	}
	
	public void setStencilFunction(CompareFunction function, int reference, int mask) {
		glStencilFunc(function.id, reference, mask);
	}
	
	public void setFrontFunction(CompareFunction function, int reference, int mask) {
		glStencilFuncSeparate(GL_FRONT, function.id, reference, mask);
	}
	
	public void setBackFunction(CompareFunction function, int reference, int mask) {
		glStencilFuncSeparate(GL_BACK, function.id, reference, mask);
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
		private StencilOperator(int id) {
			this.id = id;
		}
	}
	
}

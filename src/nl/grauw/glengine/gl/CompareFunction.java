package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public enum CompareFunction {
	EQUAL(GL_EQUAL),
	NOTEQUAL(GL_NOTEQUAL),
	LESS(GL_LESS),
	GREATER(GL_GREATER),
	LESSEQUAL(GL_LEQUAL),
	GREATEREQUAL(GL_GEQUAL),
	ALWAYS(GL_ALWAYS),
	NEVER(GL_NEVER);
	
	final int id;
	private CompareFunction(int id) {
		this.id = id;
	}
}

package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public enum PixelFormat {
	ALPHA(GL_ALPHA),
	RGB(GL_RGB),
	RGBA(GL_RGBA),
	LUMINANCE(GL_LUMINANCE),
	LUMINANCE_ALPHA(GL_LUMINANCE_ALPHA);
	
	final int id;
	private PixelFormat(final int id) {
		this.id = id;
	}
	
	public static PixelFormat getById(final int id) {
		for (PixelFormat format : values())
			if (format.id == id)
				return format;
		throw new RuntimeException("Unrecognised color read format.");
	}
}
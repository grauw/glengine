package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public enum PixelFormat {
	ALPHA(GL_ALPHA, 1),
	RGB(GL_RGB, 3),
	RGBA(GL_RGBA, 4),
	LUMINANCE(GL_LUMINANCE, 1),
	LUMINANCE_ALPHA(GL_LUMINANCE_ALPHA, 2);
	
	final int id;
	private final int channels;
	
	private PixelFormat(int id, int channels) {
		this.id = id;
		this.channels = channels;
	}
	
	public int getChannels() {
		return channels;
	}
	
	public static PixelFormat getById(final int id) {
		for (PixelFormat format : values())
			if (format.id == id)
				return format;
		throw new GLEngineException("Unrecognised color read format.");
	}
	
	public void checkDataType(PixelDataType type) {
		if ((type == PixelDataType.UNSIGNED_SHORT_5_6_5 && this != PixelFormat.RGB) ||
			((type == PixelDataType.UNSIGNED_SHORT_4_4_4_4 || type == PixelDataType.UNSIGNED_SHORT_5_5_5_1) && this != PixelFormat.RGBA))
			throw new GLEngineException("Invalid format/type combination: %s %s.", this, type);
	}
}

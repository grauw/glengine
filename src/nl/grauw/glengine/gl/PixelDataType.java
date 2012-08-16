package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

public enum PixelDataType {
	UNSIGNED_BYTE(GL_UNSIGNED_BYTE),
	UNSIGNED_SHORT_5_6_5(GL_UNSIGNED_SHORT_5_6_5),
	UNSIGNED_SHORT_4_4_4_4(GL_UNSIGNED_SHORT_4_4_4_4),
	UNSIGNED_SHORT_5_5_5_1(GL_UNSIGNED_SHORT_5_5_5_1);
	
	final int id;
	private PixelDataType(final int id) {
		this.id = id;
	}
	
	public static PixelDataType getById(final int id) {
		for (PixelDataType type : values())
			if (type.id == id)
				return type;
		throw new RuntimeException("Unrecognised color read type.");
	}
}
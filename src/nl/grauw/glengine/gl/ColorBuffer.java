package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;
import nl.grauw.glengine.model.Color;

public class ColorBuffer {
	
	private final int redBits;
	private final int greenBits;
	private final int blueBits;
	private final int alphaBits;
	Color clearColor = new Color(0, 0, 0, 0);
	private boolean redWriteMask = true;
	private boolean greenWriteMask = true;
	private boolean blueWriteMask = true;
	private boolean alphaWriteMask = true;
	
	public ColorBuffer() {
		int[] params = new int[4];
		glGetIntegerv(GL_RED_BITS, params, 0);
		redBits = params[0];
		glGetIntegerv(GL_GREEN_BITS, params, 0);
		greenBits = params[0];
		glGetIntegerv(GL_BLUE_BITS, params, 0);
		blueBits = params[0];
		glGetIntegerv(GL_ALPHA_BITS, params, 0);
		alphaBits = params[0];
	}
	
	public int getRedBits() {
		return redBits;
	}
	
	public int getGreenBits() {
		return greenBits;
	}
	
	public int getBlueBits() {
		return blueBits;
	}
	
	public int getAlphaBits() {
		return alphaBits;
	}
	
	public void setClearColor(float red, float green, float blue, float alpha) {
		setClearColor(new Color(red, green, blue, alpha));
	}
	
	public void setClearColor(Color color) {
		glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		clearColor = color;
	}
	
	public Color getClearColor() {
		return clearColor;
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public void setWriteMask(boolean red, boolean green, boolean blue, boolean alpha) {
		glColorMask(red, green, blue, alpha);
		redWriteMask = red;
		greenWriteMask = green;
		blueWriteMask = blue;
		alphaWriteMask = alpha;
	}
	
	public boolean getRedWriteMask() {
		return redWriteMask;
	}
	
	public boolean getGreenWriteMask() {
		return greenWriteMask;
	}
	
	public boolean getBlueWriteMask() {
		return blueWriteMask;
	}
	
	public boolean getAlphaWriteMask() {
		return alphaWriteMask;
	}
	
	public void enableBlend() {
		glEnable(GL_BLEND);
	}
	
	public void disableBlend() {
		glDisable(GL_BLEND);
	}
	
	public void enableDither() {
		glEnable(GL_DITHER);
	}
	
	public void disableDither() {
		glDisable(GL_DITHER);
	}
	
}

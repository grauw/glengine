package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

import java.nio.Buffer;

import nl.grauw.glengine.model.Color;

public class ColorBuffer {
	
	private final int redBits;
	private final int greenBits;
	private final int blueBits;
	private final int alphaBits;
	
	public ColorBuffer() {
		redBits = Utils.glGetInteger(GL_RED_BITS);
		greenBits = Utils.glGetInteger(GL_GREEN_BITS);
		blueBits = Utils.glGetInteger(GL_BLUE_BITS);
		alphaBits = Utils.glGetInteger(GL_ALPHA_BITS);
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
	
	public void setClearColor(Color color) {
		setClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	public void setClearColor(float red, float green, float blue, float alpha) {
		glClearColor(red, green, blue, alpha);
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public void readPixels(int x, int y, int width, int height, ColorReadFormat format, ColorReadType type, Buffer pixels) {
		glReadPixels(x, y, width, height, format.id, type.id, pixels);
	}
	
	public ColorReadFormat getImplementationColorReadFormat() {
		return ColorReadFormat.getById(Utils.glGetInteger(GL_IMPLEMENTATION_COLOR_READ_FORMAT));
	}
	
	public ColorReadType getImplementationColorReadType() {
		return ColorReadType.getById(Utils.glGetInteger(GL_IMPLEMENTATION_COLOR_READ_TYPE));
	}
	
	public void setWriteMask(boolean red, boolean green, boolean blue, boolean alpha) {
		glColorMask(red, green, blue, alpha);
	}
	
	public void enableBlend() {
		glEnable(GL_BLEND);
	}
	
	public void disableBlend() {
		glDisable(GL_BLEND);
	}
	
	public boolean isBlendEnabled() {
		return glIsEnabled(GL_BLEND);
	}
	
	public void setBlendFunction(BlendCoefficient srcFactor, BlendCoefficient dstFactor) {
		if (dstFactor == BlendCoefficient.SRC_ALPHA_SATURATE)
			throw new RuntimeException("Unsupported destination blending factor: " + dstFactor);
		glBlendFunc(srcFactor.id, dstFactor.id);
	}
	
	public void setBlendFunction(BlendCoefficient srcRGB, BlendCoefficient dstRGB, BlendCoefficient srcAlpha, BlendCoefficient dstAlpha) {
		if (dstRGB == BlendCoefficient.SRC_ALPHA_SATURATE || dstAlpha == BlendCoefficient.SRC_ALPHA_SATURATE)
			throw new RuntimeException("Unsupported destination blending factor: " + BlendCoefficient.SRC_ALPHA_SATURATE);
		glBlendFuncSeparate(srcRGB.id, dstRGB.id, srcAlpha.id, dstAlpha.id);
	}
	
	public void setBlendEquation(BlendEquation mode) {
		glBlendEquation(mode.id);
	}
	
	public void setBlendEquation(BlendEquation modeRGB, BlendEquation modeAlpha) {
		glBlendEquationSeparate(modeRGB.id, modeAlpha.id);
	}
	
	public void setBlendColor(Color color) {
		setBlendColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	public void setBlendColor(float red, float green, float blue, float alpha) {
		glBlendColor(red, green, blue, alpha);
	}
	
	public void enableDither() {
		glEnable(GL_DITHER);
	}
	
	public void disableDither() {
		glDisable(GL_DITHER);
	}
	
	public boolean isDitherEnabled() {
		return glIsEnabled(GL_DITHER);
	}
	
	public void enableSampleAlphaToCoverage() {
		glEnable(GL_SAMPLE_ALPHA_TO_COVERAGE);
	}
	
	public void disableSampleAlphaToCoverage() {
		glDisable(GL_SAMPLE_ALPHA_TO_COVERAGE);
	}
	
	public boolean isSampleAlphaToCoverageEnabled() {
		return glIsEnabled(GL_SAMPLE_ALPHA_TO_COVERAGE);
	}
	
	public void enableSampleCoverage() {
		glEnable(GL_SAMPLE_COVERAGE);
	}
	
	public void disableSampleCoverage() {
		glDisable(GL_SAMPLE_COVERAGE);
	}
	
	public boolean isSampleCoverageEnabled() {
		return glIsEnabled(GL_SAMPLE_COVERAGE);
	}
	
	public void setSampleCoverage(float value, boolean invert) {
		glSampleCoverage(value, invert);
	}
	
	public enum ColorReadFormat {
		ALPHA(GL_ALPHA),
		RGB(GL_RGB),
		RGBA(GL_RGBA);
		
		final int id;
		private ColorReadFormat(final int id) {
			this.id = id;
		}
		
		public static ColorReadFormat getById(final int id) {
			for (ColorReadFormat format : values())
				if (format.id == id)
					return format;
			throw new RuntimeException("Unrecognised color read format.");
		}
	}
	
	public enum ColorReadType {
		UNSIGNED_BYTE(GL_UNSIGNED_BYTE),
		UNSIGNED_SHORT_5_6_5(GL_UNSIGNED_SHORT_5_6_5),
		UNSIGNED_SHORT_4_4_4_4(GL_UNSIGNED_SHORT_4_4_4_4),
		UNSIGNED_SHORT_5_5_5_1(GL_UNSIGNED_SHORT_5_5_5_1);
		
		final int id;
		private ColorReadType(final int id) {
			this.id = id;
		}
		
		public static ColorReadType getById(final int id) {
			for (ColorReadType type : values())
				if (type.id == id)
					return type;
			throw new RuntimeException("Unrecognised color read type.");
		}
	}
	
	public enum BlendCoefficient {
		ZERO(GL_ZERO),
		ONE(GL_ONE),
		SRC_COLOR(GL_SRC_COLOR),
		ONE_MINUS_SRC_COLOR(GL_ONE_MINUS_SRC_COLOR),
		SRC_ALPHA(GL_SRC_ALPHA),
		ONE_MINUS_SRC_ALPHA(GL_ONE_MINUS_SRC_ALPHA),
		DST_COLOR(GL_DST_COLOR),
		ONE_MINUS_DST_COLOR(GL_ONE_MINUS_DST_COLOR),
		DST_ALPHA(GL_DST_ALPHA),
		ONE_MINUS_DST_ALPHA(GL_ONE_MINUS_DST_ALPHA),
		CONSTANT_COLOR(GL_CONSTANT_COLOR),
		ONE_MINUS_CONSTANT_COLOR(GL_ONE_MINUS_CONSTANT_COLOR),
		CONSTANT_ALPHA(GL_CONSTANT_ALPHA),
		ONE_MINUS_CONSTANT_ALPHA(GL_ONE_MINUS_CONSTANT_ALPHA),
		SRC_ALPHA_SATURATE(GL_SRC_ALPHA_SATURATE);
		
		final int id;
		private BlendCoefficient(final int id) {
			this.id = id;
		}
	}
	
	public enum BlendEquation {
		ADD(GL_FUNC_ADD),
		SUBTRACT(GL_FUNC_SUBTRACT),
		REVERSE_SUBTRACT(GL_FUNC_REVERSE_SUBTRACT);
		
		final int id;
		private BlendEquation(final int id) {
			this.id = id;
		}
	}
	
}

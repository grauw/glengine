package nl.grauw.glengine;

import java.util.Arrays;

import nl.grauw.glengine.gl.GLContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CapabilitiesLogger {
	
	JSONObject capabilities;
	
	public CapabilitiesLogger(GLContext context) throws JSONException {
		capabilities = new JSONObject();
		capabilities.put("vendor", context.getVendor());
		capabilities.put("renderer", context.getRenderer());
		capabilities.put("version", context.getVersion());
		capabilities.put("shadingLanguageVersion", context.getShadingLanguageVersion());
		capabilities.put("extensions", new JSONArray(Arrays.asList(context.getExtensions())));
		capabilities.put("maxCombinedTextureImageUnits", context.getMaxCombinedTextureImageUnits());
		capabilities.put("maxCubeMapTextureSize", context.getMaxCubeMapTextureSize());
		capabilities.put("maxFragmentUniformVectors", context.getMaxFragmentUniformVectors());
		capabilities.put("maxRenderBufferSize", context.getMaxRenderBufferSize());
		capabilities.put("maxTextureImageUnits", context.getMaxTextureImageUnits());
		capabilities.put("maxTextureSize", context.getMaxTextureSize());
		capabilities.put("maxVaryingVectors", context.getMaxVaryingVectors());
		capabilities.put("maxVertexAttribs", context.getMaxVertexAttribs());
		capabilities.put("maxVertexTextureImageUnits", context.getMaxVertexTextureImageUnits());
		capabilities.put("maxVertexUniformVectors", context.getMaxVertexUniformVectors());
		JSONObject maxViewportDims = new JSONObject();
		maxViewportDims.put("width", context.getMaxViewportDims().getWidth());
		maxViewportDims.put("height", context.getMaxViewportDims().getHeight());
		capabilities.put("maxViewportDims", maxViewportDims);
		JSONArray compressedTextureFormats = new JSONArray();
		for (int format : context.getCompressedTextureFormats())
			compressedTextureFormats.put(format);
		capabilities.put("compressedTextureFormats", compressedTextureFormats);
		JSONArray shaderBinaryFormats = new JSONArray();
		for (int format : context.getShaderBinaryFormats())
			shaderBinaryFormats.put(format);
		capabilities.put("shaderBinaryFormats", shaderBinaryFormats);
	}
	
	public JSONObject getCapabilitiesJSON()
	{
		return capabilities;
	}
	
	public void log() throws JSONException {
		Log.i("GLEngine", capabilities.toString(4));
	}
	
	public static void log(GLContext context) {
		try {
			new CapabilitiesLogger(context).log();
		} catch (JSONException e) {
			Log.e("GLEngine", e.getMessage(), e);
		}
	}
	
}

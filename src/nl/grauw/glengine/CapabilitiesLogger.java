package nl.grauw.glengine;

import java.util.Arrays;

import nl.grauw.glengine.gl.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CapabilitiesLogger {
	
	JSONObject capabilities;
	
	public CapabilitiesLogger(Context gl) throws JSONException {
		capabilities = new JSONObject();
		capabilities.put("vendor", gl.getVendor());
		capabilities.put("renderer", gl.getRenderer());
		capabilities.put("version", gl.getVersion());
		capabilities.put("shadingLanguageVersion", gl.getShadingLanguageVersion());
		capabilities.put("extensions", new JSONArray(Arrays.asList(gl.getExtensions())));
		capabilities.put("maxCombinedTextureImageUnits", gl.getMaxCombinedTextureImageUnits());
		capabilities.put("maxCubeMapTextureSize", gl.getMaxCubeMapTextureSize());
		capabilities.put("maxFragmentUniformVectors", gl.getMaxFragmentUniformVectors());
		capabilities.put("maxRenderBufferSize", gl.getMaxRenderBufferSize());
		capabilities.put("maxTextureImageUnits", gl.getMaxTextureImageUnits());
		capabilities.put("maxTextureSize", gl.getMaxTextureSize());
		capabilities.put("maxVaryingVectors", gl.getMaxVaryingVectors());
		capabilities.put("maxVertexAttribs", gl.getMaxVertexAttribs());
		capabilities.put("maxVertexTextureImageUnits", gl.getMaxVertexTextureImageUnits());
		capabilities.put("maxVertexUniformVectors", gl.getMaxVertexUniformVectors());
		JSONObject maxViewportDimsObject = new JSONObject();
		maxViewportDimsObject.put("width", gl.getMaxViewportDims().getWidth());
		maxViewportDimsObject.put("height", gl.getMaxViewportDims().getHeight());
		capabilities.put("maxViewportDims", maxViewportDimsObject);
		capabilities.put("numCompressedTextureFormats", gl.getNumCompressedTextureFormats());
		capabilities.put("numShaderBinaryFormats", gl.getNumShaderBinaryFormats());
	}
	
	public JSONObject getCapabilitiesJSON()
	{
		return capabilities;
	}
	
	public void log() throws JSONException {
		Log.i("GLEngine", capabilities.toString(4));
	}
	
	public static void log(Context gl) {
		try {
			new CapabilitiesLogger(gl).log();
		} catch (JSONException e) {
			Log.e("GLEngine", e.getMessage(), e);
		}
	}
	
}

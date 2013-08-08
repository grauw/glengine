package nl.grauw.glengine;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import nl.grauw.glengine.gl.GLEngineException;

public final class Utils {
	
	public static String loadTextAsset(AssetManager assets, String path) {
		try {
			InputStream input = assets.open(path);
			InputStreamReader reader = new InputStreamReader(input, "UTF-8");
			StringBuilder text = new StringBuilder();
			
			int length;
			char[] buffer = new char[1024];
			while ((length = reader.read(buffer)) != -1)
				text.append(buffer, 0, length);
			
			return text.toString();
		} catch (IOException e) {
			throw new GLEngineException("Error reading file", e);
		}
	}
	
	public static Bitmap loadBitmapAsset(AssetManager assets, String path) {
		try {
			return BitmapFactory.decodeStream(assets.open(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}

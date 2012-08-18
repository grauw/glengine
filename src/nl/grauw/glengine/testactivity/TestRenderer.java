package nl.grauw.glengine.testactivity;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;

import android.graphics.Bitmap;

import nl.grauw.glengine.GLEngineRenderer;
import nl.grauw.glengine.gl.Context;
import nl.grauw.glengine.gl.Context.PrimitiveType;
import nl.grauw.glengine.gl.PixelDataType;
import nl.grauw.glengine.gl.PixelFormat;
import nl.grauw.glengine.gl.Program;
import nl.grauw.glengine.gl.Shader.FragmentShader;
import nl.grauw.glengine.gl.Shader.VertexShader;
import nl.grauw.glengine.gl.Texture.MagnificationFilter;
import nl.grauw.glengine.gl.Texture.MinificationFilter;
import nl.grauw.glengine.gl.Texture2D;
import nl.grauw.glengine.gl.Utils;

public class TestRenderer extends GLEngineRenderer {
	
	private Program program;
	private final Bitmap textureBitmap;
	
	public TestRenderer(Bitmap textureImage) {
		this.textureBitmap = textureImage;
	}
	
	@Override
	public void onDrawFrame(Context context) {
		context.getColorBuffer().clear();
//		context.clear(true, false, false);  // use when clearing multiple buffers
		
		FloatBuffer vertices = Utils.createFloatBuffer(new float[] {
			 0.0f,  0.5f, 0.0f,
			-0.5f, -0.5f, 0.0f,
			 0.5f, -0.5f, 0.0f
		});
		
		FloatBuffer texCoords = Utils.createFloatBuffer(new float[] {
			 0.5f, -2.0f,
			-1.0f,  2.0f,
			 2.0f,  2.0f
		});
		
		context.useProgram(program);
		context.setVertexAttribPointer("position", 3, 0, vertices);
		context.enableVertexAttribArray("position");
		context.setVertexAttribPointer("texCoord", 2, 0, texCoords);
		context.enableVertexAttribArray("texCoord");
		program.setUniformValue("color.color", 1, 0, 0, 1);
		program.setUniformValue("color.multiplier", 0.5f);
		program.setUniformValue("texture", context.getActiveTextureUnit());
		
		context.drawArrays(PrimitiveType.TRIANGLES, 0, 3);
	}

	@Override
	public void onSurfaceChanged(Context context, int width, int height) {
		context.setViewport(0, 0, width, height);
	}

	@Override
	public void onSurfaceCreated(Context context, EGLConfig config) {
		VertexShader vertexShader = new VertexShader();
		vertexShader.setSource(
			"attribute vec4 position;" +
			"attribute vec2 texCoord;" +
			"varying vec2 v_texCoord;" +
			"void main() {" +
			"    gl_Position = position;" +
			"    v_texCoord = texCoord;" +
			"}"
		);
		vertexShader.compile();
		
		FragmentShader fragmentShader = new FragmentShader();
		fragmentShader.setSource(
			"precision mediump float;" +
			"struct test {" +
			"    vec4[2] color;" +
			"    float multiplier;" +
			"};" +
			"uniform test color;" +
			"varying vec2 v_texCoord;" +
			"uniform sampler2D texture;" +
			"void main() {" +
			"    gl_FragColor = texture2D(texture, v_texCoord) * (1.0 - color.multiplier)" +
			"                 + (color.color[0] + color.color[1]) * color.multiplier;" +
			"}"
		);
		fragmentShader.compile();
		
		program = new Program(context);
		program.attachShader(vertexShader);
		program.attachShader(fragmentShader);
		program.bindAttribLocation(5, "position");
		program.link();
		
		context.releaseShaderCompiler();
		
		Texture2D texture = context.getActiveTextureUnit().getDefaultTexture2D();
		int byteCount = textureBitmap.getWidth() * textureBitmap.getHeight() * (textureBitmap.getConfig() == Bitmap.Config.ARGB_8888 ? 4 : 2);
		ByteBuffer textureBuffer = Utils.createByteBuffer(byteCount);
		textureBitmap.copyPixelsToBuffer(textureBuffer);
		textureBuffer.position(0);
		
		texture.setImage(
			0,
			textureBitmap.getWidth(),
			textureBitmap.getHeight(),
			textureBitmap.getConfig() == Bitmap.Config.RGB_565 ? PixelFormat.RGB : PixelFormat.RGBA,
			textureBitmap.getConfig() == Bitmap.Config.RGB_565 ? PixelDataType.UNSIGNED_SHORT_5_6_5 : PixelDataType.UNSIGNED_BYTE,
			textureBuffer
		);
		
		texture.generateMipmaps();
		
		texture.setMinificationFilter(MinificationFilter.LINEAR_MIPMAP_LINEAR);
		texture.setMagnificationFilter(MagnificationFilter.LINEAR);
		
		context.getColorBuffer().setClearColor(0, 0, 0.5f, 1);
	}
	
}

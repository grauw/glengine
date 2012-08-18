package nl.grauw.glengine.testactivity;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;

import nl.grauw.glengine.GLEngineRenderer;
import nl.grauw.glengine.gl.Context;
import nl.grauw.glengine.gl.Context.PrimitiveType;
import nl.grauw.glengine.gl.Program;
import nl.grauw.glengine.gl.Shader.FragmentShader;
import nl.grauw.glengine.gl.Shader.VertexShader;
import nl.grauw.glengine.gl.Utils;

public class TestRenderer extends GLEngineRenderer {
	
	Program program;
	
	@Override
	public void onDrawFrame(Context context) {
		context.getColorBuffer().clear();
//		context.clear(true, false, false);  // use when clearing multiple buffers
		
		FloatBuffer vertices = Utils.createFloatBuffer(new float[] {
			 0.0f,  0.5f, 0.0f,
			-0.5f, -0.5f, 0.0f,
			 0.5f, -0.5f, 0.0f
		});
		
		context.useProgram(program);
		context.setVertexAttribPointer("position", 3, 0, vertices);
		context.enableVertexAttribArray("position");
		program.setUniformValue("color.color", 1, 0, 0, 1);
		program.setUniformValue("color.multiplier", 1);
		
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
			"void main() {" +
			"    gl_Position = position;" +
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
			"void main() {" +
			"    gl_FragColor = (color.color[0] + color.color[1]) * color.multiplier;" +
			"}"
		);
		fragmentShader.compile();
		
		program = new Program(context);
		program.attachShader(vertexShader);
		program.attachShader(fragmentShader);
		program.bindAttribLocation(5, "position");
		program.link();
		
		context.releaseShaderCompiler();
		
		context.getColorBuffer().setClearColor(0, 0, 0.5f, 1);
	}

}

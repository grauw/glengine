package nl.grauw.hellotriangle;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;

import nl.grauw.glengine.GLEngineRenderer;
import nl.grauw.glengine.gl.Context;
import nl.grauw.glengine.gl.Context.PrimitiveType;
import nl.grauw.glengine.gl.Program;
import nl.grauw.glengine.gl.Shader.FragmentShader;
import nl.grauw.glengine.gl.Shader.VertexShader;
import nl.grauw.glengine.gl.Utils;

public class GameRenderer extends GLEngineRenderer {
	
	Program program;
	
	@Override
	public void onDrawFrame(Context gl) {
		gl.clear(true, false, false);
		
		FloatBuffer vertices = Utils.createFloatBuffer(new float[] {
			 0.0f,  0.5f, 0.0f,
			-0.5f, -0.5f, 0.0f,
			 0.5f, -0.5f, 0.0f
		});
		
		gl.useProgram(program);
		gl.setVertexAttribPointer(program.getAttribute("position").getLocation(), 3, 0, vertices);
		gl.enableVertexAttribArray(program.getAttribute("position").getLocation());
		program.setUniformValue(program.getUniform("color.color"), 1, 0, 0, 1);
		program.setUniformValue(program.getUniform("color.multiplier"), 1);
		
		gl.drawArrays(PrimitiveType.TRIANGLES, 0, 3);
	}

	@Override
	public void onSurfaceChanged(Context gl, int width, int height) {
		gl.setViewport(0, 0, width, height);
	}

	@Override
	public void onSurfaceCreated(Context gl, EGLConfig config) {
		VertexShader vertexShader = new VertexShader();
		vertexShader.setSource("attribute vec4 position; void main() { gl_Position = position; }");
		vertexShader.compile();
		
		FragmentShader fragmentShader = new FragmentShader();
		fragmentShader.setSource("precision mediump float; struct test { vec4[2] color; float multiplier; }; uniform test color; void main() { gl_FragColor = (color.color[0] + color.color[1]) * color.multiplier; }");
		fragmentShader.compile();
		
		program = new Program(gl);
		program.attachShader(vertexShader);
		program.attachShader(fragmentShader);
		program.bindAttribLocation(5, "position");
		program.link();
		
		gl.setClearColor(0, 0, 0.5f, 1);
	}

}

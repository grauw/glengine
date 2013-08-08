package nl.grauw.glengine.scene;

import android.content.res.AssetManager;

import nl.grauw.glengine.Utils;
import nl.grauw.glengine.gl.GLContext;
import nl.grauw.glengine.gl.GLEngineException;
import nl.grauw.glengine.gl.Program;
import nl.grauw.glengine.gl.Shader.FragmentShader;
import nl.grauw.glengine.gl.Shader.VertexShader;
import nl.grauw.glengine.model.Matrix4x4;

public class Material {
	
	private final String vertexSource;
	private final String fragmentSource;
	
	protected Program program;
	
	public Material(String vertexSource, String fragmentSource) {
		if (vertexSource == null || fragmentSource == null)
			throw new GLEngineException("Vertex or fragment source can not be null.");
		
		this.vertexSource = vertexSource;
		this.fragmentSource = fragmentSource;
	}
	
	public Material(AssetManager assets, String vertexPath, String fragmentPath) {
		this(Utils.loadTextAsset(assets, vertexPath), Utils.loadTextAsset(assets, fragmentPath));
	}
	
	public void compile(GLContext gl) {
		VertexShader vertexShader = new VertexShader();
		vertexShader.setSource(vertexSource);
		vertexShader.compile();
		
		FragmentShader fragmentShader = new FragmentShader();
		fragmentShader.setSource(fragmentSource);
		fragmentShader.compile();
		
		program = new Program(gl);
		program.attachShader(vertexShader);
		program.attachShader(fragmentShader);
		program.link();
	}
	
	public Program getProgram() {
		return program;
	}
	
	/** Initialization callback. */
	public void initialize(GLContext gl) {
		compile(gl);
	}
	
	/** Per-frame update callback. */
	public void update(int elapsed) {
	}
	
	/** Pre-draw callback. */
	public void prepareDraw(GLContext gl, Matrix4x4 transform) {
		gl.useProgram(program);
	}
	
}

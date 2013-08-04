package nl.grauw.glengine.scene;

import nl.grauw.glengine.gl.GLContext;
import nl.grauw.glengine.gl.GLEngineException;
import nl.grauw.glengine.gl.Program;
import nl.grauw.glengine.gl.Shader.FragmentShader;
import nl.grauw.glengine.gl.Shader.VertexShader;

public class Material {
	
	private String vertexSource;
	private String fragmentSource;
	
	private Program program;
	
	public Material(String vertexSource, String fragmentSource) {
		if (vertexSource == null || fragmentSource == null)
			throw new GLEngineException("Vertex or fragment source can not be null.");
		
		this.vertexSource = vertexSource;
		this.fragmentSource = fragmentSource;
	}
	
	public void compile(GLContext context) {
		VertexShader vertexShader = new VertexShader();
		vertexShader.setSource(vertexSource);
		vertexShader.compile();
		
		FragmentShader fragmentShader = new FragmentShader();
		fragmentShader.setSource(fragmentSource);
		fragmentShader.compile();
		
		program = new Program(context);
		program.attachShader(vertexShader);
		program.attachShader(fragmentShader);
		program.link();
	}
	
	public Program getProgram() {
		return program;
	}
	
}

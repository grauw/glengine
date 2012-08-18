package nl.grauw.glengine.gl;

@SuppressWarnings("serial")
public class GLEngineException extends RuntimeException {
	
	public GLEngineException(String message, Object... args) {
		super(String.format(message, args));
	}
	
	public GLEngineException(Throwable cause, String message, Object... args) {
		super(String.format(message, args), cause);
	}
	
	public static class OpenGLException extends GLEngineException {
		
		public OpenGLException(String message, Object... args) {
			super("OpenGL error: " + message, args);
		}
		
	}
	
}

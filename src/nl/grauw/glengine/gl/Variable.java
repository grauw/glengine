package nl.grauw.glengine.gl;

import static android.opengl.GLES20.*;

/**
 * A shader variable (e.g. a vertex attribute or uniform).
 */
public class Variable {
	
	private final int location;
	private final String name;
	private final int size;
	private final VariableType type;
	
	public Variable(int location, String name, int size, VariableType type) {
		this.location = location;
		this.name = name;
		this.size = size;
		this.type = type;
	}
	
	public int getLocation() {
		return location;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSize() {
		return size;
	}
	
	public VariableType getType() {
		return type;
	}
	
	public enum VariableType {
		FLOAT(GL_FLOAT),
		FLOAT_VEC2(GL_FLOAT_VEC2),
		FLOAT_VEC3(GL_FLOAT_VEC3),
		FLOAT_VEC4(GL_FLOAT_VEC4),
		INT(GL_INT),
		INT_VEC2(GL_INT_VEC2),
		INT_VEC3(GL_INT_VEC3),
		INT_VEC4(GL_INT_VEC4),
		BOOL(GL_BOOL),
		BOOL_VEC2(GL_BOOL_VEC2),
		BOOL_VEC3(GL_BOOL_VEC3),
		BOOL_VEC4(GL_BOOL_VEC4),
		FLOAT_MAT2(GL_FLOAT_MAT2),
		FLOAT_MAT3(GL_FLOAT_MAT3),
		FLOAT_MAT4(GL_FLOAT_MAT4),
		SAMPLER_2D(GL_SAMPLER_2D),
		SAMPLER_CUBE(GL_SAMPLER_CUBE);
		
		final int id;
		private VariableType(final int id) {
			this.id = id;
		}
		
		public static VariableType getById(final int id) {
			for (VariableType type : values())
				if (type.id == id)
					return type;
			return null;
		}
	}
	
}

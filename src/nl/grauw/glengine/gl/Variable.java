package nl.grauw.glengine.gl;

/**
 * A shader variable (e.g. a vertex attribute or uniform).
 */
public class Variable {
	
	private final int location;
	private final String name;
	private final int size;
	private final int type;
	
	public Variable(int location, String name, int size, int type) {
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
	
	public int getType() {
		return type;
	}
	
}

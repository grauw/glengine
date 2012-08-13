package nl.grauw.glengine.model;

public class Vertex {
	
	private Position position;
	private Color color;
	private Vector normal;
	
	public Vertex(Position position, Color color) {
		this(position, color, Vector.ZERO);
	}
	
	public Vertex(Position position, Color color, Vector normal) {
		this.position = position;
		this.color = color;
		this.normal = normal;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public Vector getNormal() {
		return normal;
	}
	
}

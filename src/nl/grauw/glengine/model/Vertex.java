package nl.grauw.glengine.model;

public class Vertex {
	
	private final Point position;
	private final Color color;
	private final Vector normal;
	
	public Vertex(final Point position, final Color color) {
		this(position, color, Vector.ZERO);
	}
	
	public Vertex(final Point position, final Color color, final Vector normal) {
		this.position = position;
		this.color = color;
		this.normal = normal;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public Vector getNormal() {
		return normal;
	}
	
}

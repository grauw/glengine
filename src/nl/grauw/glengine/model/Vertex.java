package nl.grauw.glengine.model;

import java.nio.ByteBuffer;

import nl.grauw.glengine.model.VertexArray.VertexInfo;

public class Vertex implements nl.grauw.glengine.model.VertexArray.Vertex {
	
	private final Point position;
	private final Color color;
	private final Vector normal;
	
	public final VertexInfo vertexInfo = new VertexInfo(Point.attributeInfo, Color.attributeInfo, Vector.attributeInfo);
	
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
	
	@Override
	public void write(ByteBuffer buffer) {
		position.write(buffer);
		color.write(buffer);
		normal.write(buffer);
	}

	@Override
	public VertexInfo getVertexInfo() {
		return vertexInfo;
	}
	
}

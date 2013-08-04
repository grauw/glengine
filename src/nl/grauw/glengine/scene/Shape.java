package nl.grauw.glengine.scene;

import java.nio.FloatBuffer;

import nl.grauw.glengine.gl.Utils;
import nl.grauw.glengine.model.Color;
import nl.grauw.glengine.model.Point;
import nl.grauw.glengine.model.Vector;
import nl.grauw.glengine.model.Vertex;

public abstract class Shape implements Drawable {
	
	private FloatBuffer vertexBuffer;
	private FloatBuffer colorBuffer;
	private FloatBuffer normalBuffer;
	
	/**
	 * Return the vertices for this shape.
	 * Subclasses should implement this method to benefit from the vertex- and
	 * color-buffer conversion methods.
	 * 
	 * @return The array of vertices.
	 */
	protected abstract Vertex[] getVertices();
	
	/**
	 * Return the vertex positions in a FloatBuffer, for glVertexPointer().
	 */
	protected FloatBuffer getVertexBuffer() {
		if (vertexBuffer == null) {
			vertexBuffer = createVertexBuffer();
		}
		return vertexBuffer;
	}
	
	/**
	 * Create a FloatBuffer for the vertex positions.
	 * 
	 * Buffers passed to glVertexPointer() must be direct, i.e., they must be
	 * placed on the native heap where the garbage collector cannot move them.
	 */
	private FloatBuffer createVertexBuffer() {
		Vertex[] vertices = getVertices();
		float[] vertexPositions = new float[vertices.length * 3];
		int j = 0;
		for (Vertex vertex : vertices) {
			Point position = vertex.getPosition();
			vertexPositions[j++] = position.getX();
			vertexPositions[j++] = position.getY();
			vertexPositions[j++] = position.getZ();
		}
		return Utils.createFloatBuffer(vertexPositions);
	}
	
	/**
	 * Return the vertex colors in a FloatBuffer, for glColorPointer().
	 */
	protected FloatBuffer getColorBuffer() {
		if (colorBuffer == null) {
			colorBuffer = createColorBuffer();
		}
		return colorBuffer;
	}
	
	/**
	 * Create a FloatBuffer for the vertex colors.
	 * 
	 * Buffers passed to glColorPointer() must be direct, i.e., they must be
	 * placed on the native heap where the garbage collector cannot move them.
	 */
	private FloatBuffer createColorBuffer() {
		Vertex[] vertices = getVertices();
		float[] vertexColours = new float[vertices.length * 4];
		int j = 0;
		for (Vertex vertex : vertices) {
			Color color = vertex.getColor();
			vertexColours[j++] = color.getRed();
			vertexColours[j++] = color.getGreen();
			vertexColours[j++] = color.getBlue();
			vertexColours[j++] = color.getAlpha();
		}
		return Utils.createFloatBuffer(vertexColours);
	}

	/**
	 * Return the vertex normals in a FloatBuffer, for glNormalPointer().
	 */
	protected FloatBuffer getNormalBuffer() {
		if (normalBuffer == null) {
			normalBuffer = createNormalBuffer();
		}
		return normalBuffer;
	}
	
	/**
	 * Create a FloatBuffer for the vertex normals.
	 * 
	 * Buffers passed to glNormalPointer() must be direct, i.e., they must be
	 * placed on the native heap where the garbage collector cannot move them.
	 */
	private FloatBuffer createNormalBuffer() {
		Vertex[] vertices = getVertices();
		float[] vertexNormals = new float[vertices.length * 3];
		int j = 0;
		for (Vertex vertex : vertices) {
			Vector normal = vertex.getNormal();
			vertexNormals[j++] = normal.getX();
			vertexNormals[j++] = normal.getY();
			vertexNormals[j++] = normal.getZ();
		}
		return Utils.createFloatBuffer(vertexNormals);
	}
	
}

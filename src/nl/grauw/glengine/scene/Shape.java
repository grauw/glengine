package nl.grauw.glengine.scene;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import nl.grauw.glengine.Utils;
import nl.grauw.glengine.gl.GLContext;
import nl.grauw.glengine.gl.GLContext.PrimitiveType;
import nl.grauw.glengine.gl.GLEngineException;
import nl.grauw.glengine.model.Matrix4x4;
import nl.grauw.glengine.model.Vertex;
import nl.grauw.glengine.model.Vertex.AttributeInfo;
import nl.grauw.glengine.model.Vertex.VertexAttribute;
import nl.grauw.glengine.model.Vertex.VertexInfo;
import nl.grauw.glengine.model.VertexArray;

public abstract class Shape implements Drawable {
	
	private final PrimitiveType primitive;
	private Material material;
	
	private VertexArray vertexArray;
	private Buffer indexBuffer;
	private AttributeLink[] attributeLinks;
	
	public Shape(PrimitiveType primitive) {
		this.primitive = primitive;
	}
	
	public <M extends Material> M setMaterial(M material) {
		this.material = material;
		return material;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public void setVertices(Vertex... vertices) {
		setVertexArray(new VertexArray(vertices));
	}
	
	public void setVertexArray(VertexArray vertexArray) {
		this.vertexArray = vertexArray;
		attributeLinks = createAttributeLinks();
		indexBuffer = null;
	}
	
	protected VertexArray getVertexArray() {
		return vertexArray;
	}
	
	public void setIndices(byte... indices) {
		setIndexBuffer(Utils.createByteBuffer(indices));
	}
	
	public void setIndices(short... indices) {
		setIndexBuffer(Utils.createShortBuffer(indices));
	}
	
	public void setIndices(int... indices) {
		if (vertexArray == null)
			throw new GLEngineException("Must set vertex array before setting vertex indices.");
		
		int verticesCount = vertexArray.getLength();
		if (verticesCount < 128) {
			setIndexBuffer(Utils.createByteBuffer(indices));
		} else if (verticesCount < 32768) {
			setIndexBuffer(Utils.createShortBuffer(indices));
		} else {
			throw new RuntimeException("Too many vertices, index size exceeded.");
		}
	}
	
	public void setIndexBuffer(Buffer indexBuffer) {
		if (vertexArray == null)
			throw new GLEngineException("Must set vertex array before setting vertex indices.");
		
		this.indexBuffer = indexBuffer;
	}
	
	protected Buffer getIndexBuffer() {
		return indexBuffer;
	}
	
	public VertexInfo getVertexInfo() {
		if (vertexArray == null)
			throw new GLEngineException("Must set vertex array before getting vertex info.");
		
		return vertexArray.getVertexInfo();
	}
	
	private AttributeLink[] createAttributeLinks() {
		VertexAttribute[] vertexAttributes = getVertexInfo().getAttributes();
		AttributeLink[] attributeLinks = new AttributeLink[vertexAttributes.length];
		
		for (int i = 0; i < vertexAttributes.length; i++)
			attributeLinks[i] = new AttributeLink(vertexAttributes[i], vertexAttributes[i].getInfo().getLinkName());
		
		return attributeLinks;
	}
	
	public void linkAttributes(String... shaderAttributes) {
		if (vertexArray == null)
			throw new GLEngineException("Must set vertex array before linking attributes.");
		
		VertexAttribute[] vertexAttributes = getVertexInfo().getAttributes();
		if (shaderAttributes.length > attributeLinks.length)
			throw new GLEngineException("Too many attributes specified.");
		
		for (int i = 0; i < shaderAttributes.length; i++)
			linkAttribute(vertexAttributes[i], shaderAttributes[i]);
	}
	
	public void linkAttribute(VertexAttribute vertexAttribute, String shaderAttribute) {
		if (vertexArray == null)
			throw new GLEngineException("Must set vertex array before linking attributes.");
		
		attributeLinks[vertexAttribute.getIndex()].setShaderAttribute(shaderAttribute);
	}
	
	@Override
	public void initialize(GLContext gl) {
		
	}
	
	@Override
	public void draw(GLContext gl, Matrix4x4 transform) {
		if (vertexArray == null)
			throw new GLEngineException("Must set vertex array before drawing.");
		if (material == null)
			throw new GLEngineException("Must set material before drawing.");
		
		prepareDraw(gl, transform);
		
		if (indexBuffer instanceof ByteBuffer) {
			gl.drawElements(primitive, (ByteBuffer)indexBuffer);
		} else if (indexBuffer instanceof ShortBuffer) {
			gl.drawElements(primitive, (ShortBuffer)indexBuffer);
		} else {
			gl.drawArrays(primitive, 0, vertexArray.getLength());
		}
		
		finalizeDraw(gl, transform);
	}
	
	/**
	 * Invoked at the beginning of a draw.
	 * Primes the material and attribute arrays.
	 */
	protected void prepareDraw(GLContext gl, Matrix4x4 transform) {
		material.prepareDraw(gl, transform);
		
		for (AttributeLink attributeLink : attributeLinks)
			attributeLink.prepare(gl);
	}
	
	/**
	 * Invoked at the end of a draw.
	 * Restores the attribute arrays to the initial state.
	 */
	protected void finalizeDraw(GLContext gl, Matrix4x4 transform) {
		for (AttributeLink attributeLink : attributeLinks)
			attributeLink.finalize(gl);
	}
	
	private final class AttributeLink {
		
		private final VertexAttribute vertexAttribute;
		private String shaderAttribute;
		
		public AttributeLink(VertexAttribute vertexAttribute, String shaderAttribute) {
			this.vertexAttribute = vertexAttribute;
			this.shaderAttribute = shaderAttribute;
		}
		
		public void setShaderAttribute(String shaderAttribute) {
			this.shaderAttribute = shaderAttribute;
		}
		
		public void prepare(GLContext gl) {
			if (!gl.getCurrentProgram().hasAttribute(shaderAttribute))
				return;
			
			gl.enableVertexAttribArray(shaderAttribute);
			
			AttributeInfo attributeInfo = vertexAttribute.getInfo();
			
			ByteBuffer buffer = vertexArray.getBuffer();
			buffer.position(vertexAttribute.getOffset());
			gl.setVertexAttribPointer(shaderAttribute, attributeInfo.getComponents(), attributeInfo.getType(),
				attributeInfo.isNormalized(), getVertexInfo().getSize(), buffer);
		}
		
		public void finalize(GLContext gl) {
			if (!gl.getCurrentProgram().hasAttribute(shaderAttribute))
				return;
			
			gl.disableVertexAttribArray(shaderAttribute);
		}
		
	}
	
}

package nl.grauw.glengine.scene;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import nl.grauw.glengine.gl.GLContext;
import nl.grauw.glengine.gl.GLEngineException;
import nl.grauw.glengine.gl.Utils;
import nl.grauw.glengine.gl.GLContext.PrimitiveType;
import nl.grauw.glengine.model.Vertex;
import nl.grauw.glengine.model.Vertex.VertexAttribute;
import nl.grauw.glengine.model.Vertex.VertexInfo;
import nl.grauw.glengine.model.VertexArray;

public abstract class Shape implements Drawable {
	
	private VertexArray vertexArray;
	private Buffer indexBuffer;
	private AttributeLink[] attributeLinks;
	
	public void setVertices(Vertex... vertices) {
		setVertexArray(new VertexArray(vertices));
	}
	
	public void setVertexArray(VertexArray vertexArray) {
		this.vertexArray = vertexArray;
		attributeLinks = createAttributeLinks();
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
		this.indexBuffer = indexBuffer;
	}
	
	protected Buffer getIndexBuffer() {
		return indexBuffer;
	}
	
	public VertexInfo getVertexInfo() {
		return vertexArray.getVertexInfo();
	}
	
	private AttributeLink[] createAttributeLinks() {
		VertexAttribute[] vertexAttributes = getVertexInfo().getAttributes();
		AttributeLink[] attributeLinks = new AttributeLink[vertexAttributes.length];
		
		for (int i = 0; i < vertexAttributes.length; i++)
			attributeLinks[i] = new AttributeLink(vertexAttributes[i], vertexAttributes[i].getAttributeInfo().getLinkName());
		
		return attributeLinks;
	}
	
	public void linkAttributes(String... shaderAttributes) {
		VertexAttribute[] vertexAttributes = getVertexInfo().getAttributes();
		if (shaderAttributes.length > attributeLinks.length)
			throw new GLEngineException("Too many attributes specified.");
		
		for (int i = 0; i < shaderAttributes.length; i++)
			linkAttribute(vertexAttributes[i], shaderAttributes[i]);
	}
	
	public void linkAttribute(VertexAttribute vertexAttribute, String shaderAttribute) {
		attributeLinks[vertexAttribute.getIndex()].setShaderAttribute(shaderAttribute);
	}
	
	@Override
	public void initialize(GLContext gl) {
		
	}
	
	protected void draw(GLContext gl, PrimitiveType type) {
		setUpDraw(gl);
		
		if (indexBuffer != null) {
			gl.drawElements(type, indexBuffer);
		} else {
			gl.drawArrays(type, 0, vertexArray.getLength());
		}
		
		tearDownDraw(gl);
	}
	
	protected void setUpDraw(GLContext gl) {
		for (AttributeLink attributeLink : attributeLinks)
			attributeLink.setUp(gl);
	}
	
	protected void tearDownDraw(GLContext gl) {
		for (AttributeLink attributeLink : attributeLinks)
			attributeLink.tearDown(gl);
	}
	
	private class AttributeLink {
		
		private final VertexAttribute vertexAttribute;
		private String shaderAttribute;
		
		public AttributeLink(VertexAttribute vertexAttribute, String shaderAttribute) {
			this.vertexAttribute = vertexAttribute;
			this.shaderAttribute = shaderAttribute;
		}
		
		public void setShaderAttribute(String shaderAttribute) {
			this.shaderAttribute = shaderAttribute;
		}
		
		public void setUp(GLContext gl) {
			if (!gl.getCurrentProgram().hasAttribute(shaderAttribute))
				return;
			
			gl.enableVertexAttribArray(shaderAttribute);
			
			ByteBuffer buffer = vertexArray.getBuffer();
			buffer.position(vertexAttribute.getOffset());
			gl.setVertexAttribPointer(shaderAttribute, vertexAttribute.getAttributeInfo(), getVertexInfo().getSize(), buffer);
		}
		
		public void tearDown(GLContext gl) {
			if (!gl.getCurrentProgram().hasAttribute(shaderAttribute))
				return;
			
			gl.disableVertexAttribArray(shaderAttribute);
		}
		
	}
	
}

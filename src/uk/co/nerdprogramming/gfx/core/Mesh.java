package uk.co.nerdprogramming.gfx.core;

public class Mesh {
	private GLVertexArray vao;
	private GLVertexBuffer pos, uvs;
	private GLIndexBuffer tris;
	private int vertexCount;
	
	public static Mesh Create3D(float[] vertexPositions, float[] vertexUVS, int[] triangles) {
		return new Mesh(
				GLVertexArray.Create(),
				GLVertexBuffer.Create(0, vertexPositions, 3, 0, 0),
				GLVertexBuffer.Create(1, vertexUVS, 2, 0, 0),
				GLIndexBuffer.Create(triangles),
				triangles.length
		);
	}

	private Mesh(GLVertexArray vao, GLVertexBuffer pos,GLVertexBuffer uvs, GLIndexBuffer tris, int vertexCount) {
		this.vao = vao;
		this.pos = pos;
		this.uvs = uvs;
		this.tris = tris;
		this.vertexCount = vertexCount;
	}
	
	public void Bind() {
		vao.Bind();
	}
	
	public void Delete() {
		vao.Delete();
		pos.Delete();
		tris.Delete();
	}
	
	public int GetVertexCount() {
		return vertexCount;
	}
	
	
	
}

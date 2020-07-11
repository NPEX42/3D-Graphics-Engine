package uk.co.nerdprogramming.gfx.core;

import static org.lwjgl.opengl.GL46.*;

public class GLIndexBuffer {
	private int ID;
	
	//glBindBuffer(GL_ARRAY_BUFFER,ID);
	//glBufferData(GL_ARRAY_BUFFER,data,GL_STATIC_DRAW)
	//glVertexAttribPointer(index, size, GL_FLOAT, stride, offset)
	//glEnableAttrib(index)
	
	private GLIndexBuffer() {
		ID = glGenBuffers();
	}
	
	public void Bind(int glTarget) {
		glBindBuffer(glTarget, ID);
	}
	
	public void SetData(int[] data, int glMode, int glTarget) {
		glBufferData(glTarget,data,glMode);
	}
	
	public void Enable(int index) {
		glEnableVertexAttribArray(index);
	}
	
	public static GLIndexBuffer Create(int[] triangles) {
		GLIndexBuffer vb = new GLIndexBuffer();
		vb.Bind(GL_ELEMENT_ARRAY_BUFFER);
		vb.SetData(triangles, GL_STATIC_DRAW, GL_ELEMENT_ARRAY_BUFFER);
		return vb;
	}
	
	public void Delete() {
		glDeleteBuffers(ID);
	}
}

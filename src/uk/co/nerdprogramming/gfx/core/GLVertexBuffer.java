package uk.co.nerdprogramming.gfx.core;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL46.*;
public class GLVertexBuffer {
	private int ID;
	
	//glBindBuffer(GL_ARRAY_BUFFER,ID);
	//glBufferData(GL_ARRAY_BUFFER,data,GL_STATIC_DRAW)
	//glVertexAttribPointer(index, size, GL_FLOAT, stride, offset)
	//glEnableAttrib(index)
	
	private GLVertexBuffer() {
		ID = glGenBuffers();
	}
	
	public void Bind(int glTarget) {
		glBindBuffer(glTarget, ID);
	}
	
	public void SetData(float[] data, int glMode, int glTarget) {
		glBufferData(glTarget,data,glMode);
	}
	
	public void AllocateToVAO(int index, int size, int glType, boolean norm, int stride, int offset) {
		glVertexAttribPointer(index, size, glType, norm, stride, offset);
	}
	
	public void Enable(int index) {
		glEnableVertexAttribArray(index);
	}
	
	public static GLVertexBuffer Create(int index, float[] data, int size, int stride, int offset) {
		GLVertexBuffer vb = new GLVertexBuffer();
		vb.Bind(GL_ARRAY_BUFFER);
		vb.SetData(data, GL_STATIC_DRAW, GL_ARRAY_BUFFER);
		vb.AllocateToVAO(index, size, GL_FLOAT, false, stride, offset);
		vb.Enable(index);
		return vb;
	}
	
	public void Delete() {
		glDeleteBuffers(ID);
	}
}

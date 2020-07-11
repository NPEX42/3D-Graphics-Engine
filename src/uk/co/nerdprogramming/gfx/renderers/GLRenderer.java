package uk.co.nerdprogramming.gfx.renderers;

import java.awt.Color;

import uk.co.nerdprogramming.gfx.core.Mesh;
import uk.co.nerdprogramming.gfx.core.OGL_Utils;
import uk.co.nerdprogramming.gfx.shaders.Shader;

import static org.lwjgl.opengl.GL46.*;
/**
 * 
 * <p>An Implementation of RendererAPI which uses OpenGL 4.6</p>
 * 
 * @author George Venn (Nerdprogramming)
 * @version 1.0.0-alpha
 * @since 10/07/2020
 */
public class GLRenderer implements RendererAPI {
	private static GLRenderer self;
	private GLRenderer() {}
	
	@Override
	public void ClearColor(Color c) {
		glClearColor(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
		glClear(GL_COLOR_BUFFER_BIT);
		String error = OGL_Utils.GetErrorMessages();
		if(error != null) {System.err.print(error);}
	}

	public static GLRenderer get() {
		if(self == null) self = new GLRenderer();
		return self;
	}

	@Override
	public void Render(Mesh mesh, Shader shader) {
		mesh.Bind();
		shader.Bind();
		glDrawElements(GL_TRIANGLES, mesh.GetVertexCount(), GL_UNSIGNED_INT, 0);
		String error = OGL_Utils.GetErrorMessages();
		if(error != null) {System.err.print(error);}
	}
	
}

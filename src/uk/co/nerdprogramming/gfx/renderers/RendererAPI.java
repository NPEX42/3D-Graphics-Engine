package uk.co.nerdprogramming.gfx.renderers;

import java.awt.Color;

import uk.co.nerdprogramming.gfx.core.Mesh;
import uk.co.nerdprogramming.gfx.shaders.Shader;

public interface RendererAPI {
	public void ClearColor(Color c);
	public void Render(Mesh mesh, Shader shader);
	
}

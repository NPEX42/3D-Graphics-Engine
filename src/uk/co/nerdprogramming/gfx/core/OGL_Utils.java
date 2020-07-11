package uk.co.nerdprogramming.gfx.core;

import static org.lwjgl.opengl.GL46.*;

public class OGL_Utils {
	public static String GetErrorMessages() {
		String out = "";
		int error = 0;
		if((error = glGetError()) == GL_NO_ERROR) return null;
		while((error = glGetError()) != GL_NO_ERROR) {
			switch(error) {
			case GL_INVALID_ENUM:                  out += "INVALID ENUM\n";            break;
			case GL_INVALID_FRAMEBUFFER_OPERATION: out += "INVALID FRAME BUFFER OP\n"; break;
			case GL_INVALID_INDEX:                 out += "INVALID INDEX\n";           break;
			case GL_INVALID_OPERATION:             out += "INVALID OPERATION\n";       break;
			case GL_INVALID_VALUE:                 out += "INVALID VALUE";             break;
			}
		}
		return out;
	}

	public static boolean HasErrors() {
		String result = GetErrorMessages();
		if(result != null) {System.err.println(result);}
		return result != null;
	}

	public static void ClearErrors() {
		GetErrorMessages();
	}
	
	
}

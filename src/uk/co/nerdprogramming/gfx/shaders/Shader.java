package uk.co.nerdprogramming.gfx.shaders;
import static org.lwjgl.opengl.GL46.*;

import java.util.HashMap;

import org.joml.*;

import std.core.io.IO;
import uk.co.nerdprogramming.gfx.textures.Texture2D;
public class Shader {
	private int progID, vertID, fragID;
	private HashMap<String, Integer> uniformCache = new HashMap<String, Integer>();
	private Shader(int progID, int vertID, int fragID) {
		super();
		this.progID = progID;
		this.vertID = vertID;
		this.fragID = fragID;
	}
	
	public static Shader CompileShaders(String vertexSource, String fragmentSource) {
			
			if(vertexSource == null && fragmentSource == null) return null;
			
			int progID = glCreateProgram();
			int vertID = glCreateShader(GL_VERTEX_SHADER);
			int fragID = glCreateShader(GL_FRAGMENT_SHADER);
			
			glShaderSource(vertID, vertexSource);
			glShaderSource(fragID, fragmentSource);
			
			glCompileShader(vertID);
			glCompileShader(fragID);
			
			if(!IsShaderCompiled(vertID)) {
				System.err.println("Couldn't Compile Vertex Shader...");
				System.err.println(glGetShaderInfoLog(vertID, 1024));
			}
			
			if(!IsShaderCompiled(fragID)) {
				System.err.println("Couldn't Compile Fragment Shader...");
				System.err.println(glGetShaderInfoLog(fragID, 1024));
			}
			
			glAttachShader(progID, vertID);
			glAttachShader(progID, fragID);
			
			glLinkProgram(progID);
			if(!ValidateProgram(progID)) {
				System.err.println("Couldn't Link Shader Pipeline...");
				System.err.println(glGetProgramInfoLog(progID, 1024));
			}
			
			glUseProgram(progID);
			
			return new Shader(progID, vertID, fragID);
		}

	private static boolean IsShaderCompiled(int ID) {
		return glGetShaderi(ID, GL_COMPILE_STATUS) == GL_TRUE;
	}
	
	private static boolean ValidateProgram(int ID) {
		glValidateProgram(ID);
		return glGetProgrami(ID, GL_LINK_STATUS) == GL_TRUE;
	}
	
	public static Shader Load(String vertexFilePath, String fragmentFilePath) {
				String vs, fs;
				
				vs = IO.LoadString(vertexFilePath);
				fs = IO.LoadString(fragmentFilePath);
				if(fs != null & vs != null) {
					System.err.println("[ShaderPipeline] Loaded Shader Successfully...");
					return Shader.CompileShaders(vs, fs);
				} else {
					System.err.println("[ShaderPipeline] Searching JAR for Shaders...");
					vs = IO.LoadString(Shader.class.getResourceAsStream(vertexFilePath));
					fs = IO.LoadString(Shader.class.getResourceAsStream(fragmentFilePath));
					if(fs == null | vs == null) {
						vs = IO.LoadString(Shader.class.getClassLoader().getResourceAsStream(vertexFilePath));
						fs = IO.LoadString(Shader.class.getClassLoader().getResourceAsStream(fragmentFilePath));
					}
					if(fs != null & vs != null) {
						System.err.println("[ShaderPipeline] Located Shaders '"+vertexFilePath+"' & '"+fragmentFilePath+"'");
						 return Shader.CompileShaders(vs, fs);
					} else {
						System.err.println("[ShaderPipeline] Unable To Locate Shaders '"+vertexFilePath+"' & '"+fragmentFilePath+"', Exiting...");
						return null;
					}
				}
	}
	
	private int GetUniform(String name) {
		Bind();
		if(uniformCache.containsKey(name)) {
			//Logger.log(name+": "+uniformCache.get(name));
			return uniformCache.get(name);
		} else {
			int loc = glGetUniformLocation(progID, name);
			//if(loc == -1) System.err.println("Couldn't Load Uniform '"+name+"'");
			if(loc > -1) uniformCache.put(name, loc);
			return loc;
		}
	}
	
	public void UploadMat4(String name, Matrix4f mat) {
		Bind();
		glUniformMatrix4fv(GetUniform(name), false, mat.get(new float[16]));
	}
	
	public void UploadMat3(String name, Matrix3f mat) {
		Bind();
		glUniformMatrix3fv(GetUniform(name), false, mat.get(new float[9]));
	}
	
	public void UploadMat2(String name, Matrix2f mat) {
		Bind();
		glUniformMatrix2fv(GetUniform(name), false, mat.get(new float[4]));
	}
	
	public void UploadVec4(String name, Vector4f vec) { Bind(); glUniform4f(GetUniform(name), vec.x, vec.y, vec.z, vec.w); }
	public void UploadVec3(String name, Vector3f vec) { Bind(); glUniform3f(GetUniform(name), vec.x, vec.y, vec.z);        }
	public void UploadVec2(String name, Vector2f vec) { Bind(); glUniform2f(GetUniform(name), vec.x, vec.y);               }
	
	public void UploadTexture2D(String name, Texture2D texture, int targetUnit, int slot) {
		Bind();
		texture.Bind(targetUnit);
		glUniform1i(GetUniform(name), slot);
	} 
	
	
	public void Bind  () { glUseProgram(progID); }
	public void UnBind() { glUseProgram(0);      }
	
	public void SetAttrib(int index, String name) {
		Bind();
		glBindAttribLocation(progID, index, name);
	}
	
	public void Delete() {
		glDetachShader(progID, vertID);
		glDetachShader(progID, fragID);
		
		glDeleteShader(fragID);
		glDeleteShader(vertID);
		glDeleteShader(progID);
	}
	
	
}

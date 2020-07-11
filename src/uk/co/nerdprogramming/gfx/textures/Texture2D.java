package uk.co.nerdprogramming.gfx.textures;
import static org.lwjgl.stb.STBImage.*;

import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


import std.core.data.LLOPS;
import uk.co.nerdprogramming.gfx.core.OGL_Utils;

import static org.lwjgl.opengl.GL46.*;
public class Texture2D {
	private int width, height, ID;
	public static Texture2D missing, white;
	static {
		white = Create_NativeEndian(new int[] {0xFFFFFFFF}, 1, 1);
		int[] dataLE = {
				0x000000FF, 0xFF00FFFF,
				0xFF00FFFF, 0x000000FF
		};
		
		int[] dataBE = {
				0xFF000000, 0xFFFF00FF,
				0xFFFF00FF, 0xFF000000
		};
		if (ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN)) {
				missing = Texture2D.Create_NativeEndian(dataBE, 2, 2);
			} else {
				missing = Texture2D.Create_NativeEndian(dataLE, 2, 2);
			}
	}
	public Texture2D(int width, int height, int iD) {
		super();
		this.width = width;
		this.height = height;
		ID = iD;
		System.err.println("[OGL] Created Texture #"+ID+"("+width+"x"+height+")");
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getID() {
		return ID;
	}
	
	public static Texture2D GetWhite() {
		if(white == null) {
			white = Create_NativeEndian(new int[] {0xFFFFFFFF}, 1, 1);
		}
		return white;
	}
	
	public void Bind(int Unit) {
		glActiveTexture(Unit);
		glBindTexture(GL_TEXTURE_2D, ID);
	}
	
	public void Delete() {
		glDeleteTextures(ID);
	}
	
	public void SetTextureData(int[] data) {
		glBindTexture(GL_TEXTURE_2D, ID);
		glInvalidateTexImage(ID, 0);
		glTexImage2D(GL_TEXTURE_2D, 0, 4, width, height, 0, GL_RGBA, GL_UNSIGNED_INT_8_8_8_8, data);
	}
	
	public void SetTextureData(Color[] data) {
		glBindTexture(GL_TEXTURE_2D, ID);
		glInvalidateTexImage(ID, 0);
		glTexImage2D(GL_TEXTURE_2D, 0, 4, width, height, 0, GL_RGBA, GL_UNSIGNED_INT_8_8_8_8, LLOPS.ToNative(data));
	}
	
	//Factory Methods
	public static Texture2D Load(String filePath) {
		int ID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, ID);
//		//glEnable(GL_FILTER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		int[] w = new int[1], h = new int[1];
		int[] channels = new int[1];
		ByteBuffer texBuffer = stbi_load(filePath, w, h, channels, 4);
		if(texBuffer == null) { System.err.println("Unable To Load Texture '"+filePath+"'"); return missing;}
		OGL_Utils.ClearErrors();
		glTexImage2D(GL_TEXTURE_2D, 0, channels[0], w[0], h[0], 0, GL_RGBA, GL_BYTE, texBuffer);
		if(OGL_Utils.HasErrors()) return null;
		return new Texture2D(w[0], h[0],ID);
	}
	//x86: BGRA (LE)
	//ARM: ARGB (BE)
	public static Texture2D Create_NativeEndian(int[] RGBA, int width, int height) {
		int ID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, ID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		OGL_Utils.ClearErrors();
		glTexImage2D(GL_TEXTURE_2D, 0, 4, width, height, 0, GL_RGBA, GL_UNSIGNED_INT_8_8_8_8, RGBA);
		if(OGL_Utils.HasErrors()) return null;
		return new Texture2D(width, height,ID);
	}
	
	public static Texture2D Create(Color[] RGBA, int width, int height) {
		int ID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, ID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		OGL_Utils.ClearErrors();
		glTexImage2D(GL_TEXTURE_2D, 0, 4, width, height, 0, GL_RGBA, GL_UNSIGNED_INT_8_8_8_8, LLOPS.ToNative(RGBA));
		if(OGL_Utils.HasErrors()) return null;
		return new Texture2D(width, height,ID);
	}
	
	public static Texture2D Reserve_NativeEndian(int width, int height) {
		int ID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, ID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		OGL_Utils.ClearErrors();
		glTexImage2D(GL_TEXTURE_2D, 0, 4, width, height, 0, GL_RGBA, GL_UNSIGNED_INT_8_8_8_8, new int[width * height]);
		if(OGL_Utils.HasErrors()) return null;
		return new Texture2D(width, height,ID);
	}
	
	
	//Texture Constants
	public static final int 
	AGE_ALBEDO = GL_TEXTURE0,
	TARGET = GL_TEXTURE_2D;
	
	
	

	
}

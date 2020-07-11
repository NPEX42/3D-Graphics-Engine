package uk.co.nerdprogramming.gfx.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;

import org.lwjgl.opengl.GLUtil;

public class DisplayManager {
	private static long windowID = -1;
	public static boolean Open(int width, int height, String title, boolean debug, boolean vsync, int monitor, int share) {
		if(!glfwInit()) return false;
		windowID = glfwCreateWindow(width, height, title, monitor, share);
		if(windowID == 0) return false;
		glfwMakeContextCurrent(windowID);
		createCapabilities();
		if(debug) GLUtil.setupDebugMessageCallback();
		return true;	
	}
	
	public static boolean Open(int width, int height, String title, boolean debug, boolean vsync) {
		return Open(width, height, title, debug, vsync, 0, 0);
	}
	
	public static boolean Open(int width, int height, String title) {
		return Open(width, height, title, false, true, 0, 0);
	}
	
	public static boolean Update() {
		glfwPollEvents();
		glfwSwapBuffers(windowID);
		return !glfwWindowShouldClose(windowID);
	}
	
	public static void Close() {
		glfwDestroyWindow(windowID);
		glfwTerminate();
	}
}

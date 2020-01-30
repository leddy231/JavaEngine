import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.List;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

abstract class Window {
	public double deltaTime = 0;
	private long lastTime = 0;
	
	private long window;
	public MouseHandler mouse;
	public KeyboardHandler keyboard;
	public boolean fullscreen = false;
	public int width;
	public int height;
	public String title = "Java Engine";

    abstract void drawFrame();
	abstract void update();
	protected void initialize() {

	}

	public void run() {
		init();
		initialize();
		lastTime = System.nanoTime() / 1000000;
		loop();
		cleanup();
	}

	private void cleanup() {
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwDefaultWindowHints();
		width = vid.width();
		height = vid.height();
		glfwWindowHint(GLFW_RED_BITS, vid.redBits());
		glfwWindowHint(GLFW_GREEN_BITS, vid.greenBits());
		glfwWindowHint(GLFW_BLUE_BITS, vid.blueBits());
		glfwWindowHint(GLFW_REFRESH_RATE, vid.refreshRate());
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

		window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		mouse = new MouseHandler(window);
		keyboard = new KeyboardHandler(window);
		glfwSetCursorPosCallback (window, mouse);
		glfwSetKeyCallback(window, keyboard);

		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);

			glfwGetWindowSize(window, pWidth, pHeight);
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		}
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
		GL.createCapabilities();
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, width, height, 0.0, -1.0, 1.0);
        glMatrixMode(GL_MODELVIEW);
        glViewport(0, 0, width, height);
	}

	protected void close() {
		glfwSetWindowShouldClose(window, true);
	}

	public void enableDepth() {
		glEnable(GL_DEPTH_TEST);
	}

	public void disableDepth() {
		glDisable(GL_DEPTH_TEST);
	}

	public void enableStencil() {
		glEnable(GL_STENCIL_TEST);
	}

	private void loop() {
		GL.createCapabilities();
		glEnable(GL_BLEND);

		glDisable(GL_DEPTH);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glClearStencil(0);
		glPointSize(3.0f);
		glStencilMask(0xFF);
		while (!glfwWindowShouldClose(window)) {
			long time = System.nanoTime() / 1000000;
			deltaTime = time - lastTime;
			lastTime = time;
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
			mouse.update();
			update();
			keyboard.update();
			drawFrame();
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}
}
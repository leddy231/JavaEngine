import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.*;

public class KeyboardHandler extends GLFWKeyCallback{

    public static boolean[] keysDown = new boolean[65536];
    public static boolean[] keysPressed = new boolean[65536];
    private static Long window;

    public KeyboardHandler(Long window) {
        this.window = window;
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keysPressed[key] = action == GLFW_PRESS;
        keysDown[key] = action != GLFW_RELEASE;
    }

    public void update() {
        keysPressed = new boolean[65536];
    }


    public static boolean isKeyDown(int keycode) {
        return keysDown[keycode];
    }
}
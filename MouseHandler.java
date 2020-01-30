
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWCursorPosCallback;

// Our MouseHandler class extends the abstract class
// abstract classes should never be instantiated so here
// we create a concrete that we can instantiate
public class MouseHandler extends GLFWCursorPosCallback{

    public static Vector2 pos = new Vector2(0,0);
    public static boolean leftDown;
    public static boolean leftPressed;
    public static boolean rightDown;
    public static boolean rightPressed;
    private static Long window;

    public MouseHandler(Long window) {
        this.window = window;
    }

    @Override
    public void invoke(long window, double xpos, double ypos) {
        pos.x = xpos;
        pos.y = ypos;
    }

    public void update() {
        boolean state = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS;
        if (state) {
            if(!leftDown) {
                leftPressed = true;
            } else {
                leftPressed = false;
            }
            leftDown = true;
        } else {
            leftDown = leftPressed = false;
        }

        state = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS;
        if (state) {
            if(!rightDown) {
                rightPressed = true;
            } else {
                rightPressed = false;
            }
            rightDown = true;
        } else {
            rightDown = rightPressed = false;
        }
    }
}
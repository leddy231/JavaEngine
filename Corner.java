import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
public class Corner extends Obj{
    public double x;
    public double y;

    public Corner(Vector2 cords) {
        this(cords.x, cords.y);
    }

    public Corner(double x, double y) {
        this.x = x;
        this.y = y;
        Vector2 point1 = new Vector2(x, y);
        lightPoints = new ArrayList<>();
        lightPoints.add(point1);
    }

    @Override
    public void draw() {
        int wover2 = 500;
        float screenX = ((float) x - wover2) / wover2;
        float screenY = ((float) y - wover2) / wover2 * -1.0f;
        glBegin(GL_POINTS);
            glColor3f(1,1,1);
            glVertex2i((int)screenX, (int)screenY);
        glEnd();
    }

    public String toString() {
        return "Corner X:" + x + " Y:" + y;
    }
}
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
public class Square extends Obj{
    private Vector2 point1;
    private Vector2 point2;
    private Vector2 point3;
    private Vector2 point4;
    private Color color;
    public int x;
    public int y;
    public int w;
    public int h;

    public Square(int x, int y, int w, int h, Color color) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        point1 = new Vector2(x, y);
        point2 = new Vector2(x + w, y);
        point3 = new Vector2(x, y + h);
        point4 = new Vector2(x + w, y + h);
        lightPoints = new ArrayList<>();
        lightPoints.add(point1);
        lightPoints.add(point2);
        lightPoints.add(point3);
        lightPoints.add(point4);
        this.color = color;
    }

    public ArrayList<Vector2> lightPoints2(Vector2 lightpos) {
        ArrayList<Vector2> list = new ArrayList<>();
        //0,0
        if(lightpos.x <= x || lightpos.y < y) {
            list.add(point1);
        }
        //1,0
        if(lightpos.y <= y || lightpos.x > x + w) {
            list.add(point2);
        }
        if(lightpos.x >= x + w || lightpos.y > y + h) {
            list.add(point4);
        }
        if(lightpos.y >= y + h || lightpos.x < x) {
            list.add(point3);
        }
        return list;
    }

    private boolean between(double x, double min, double max) {
        return (min < x && x < max);
    }

    public ArrayList<Corner> intersect(Square other) {
        //vertical
        ArrayList<Corner> corners = new ArrayList<>();
        boolean otherTopBetween = between(other.y + other.h, y, y + h);
        boolean otherBotBetween = between(other.y, y, y + h);
        boolean topBetween = between(y + h, other.y, other.y + other.h);
        boolean botBetween = between(y, other.y, other.y + other.h);
        boolean rightBetween = between(x + w, other.x, other.x + other.w);
        boolean leftBetween = between(x, other.x, other.x + other.w);
        boolean otherRightBetween = between(other.x + other.w, x, x + w);
        boolean otherLeftBetween = between(other.x, x, x + w);
        if (otherTopBetween) {
            if (rightBetween) {
                corners.add(new Corner(x + w, other.y + other.h));
            }
            if (leftBetween) {
                corners.add(new Corner(x, other.y + other.h));
            }
        }
        if (otherBotBetween) {
            if (rightBetween) {
                corners.add(new Corner(x + w, other.y));
            }
            if (leftBetween) {
                corners.add(new Corner(x, other.y));
            }
        }
        if (otherRightBetween) {
            if (topBetween) {
                corners.add(new Corner(other.x + other.w, y + h));
            }
            if (botBetween) {
                corners.add(new Corner(other.x + other.w, y));
            }
        }
        if (otherLeftBetween) {
            if (topBetween) {
                corners.add(new Corner(other.x, y + h));
            }
            if (botBetween) {
                corners.add(new Corner(other.x, y));
            }
        }
        return corners;
    }

    @Override
    public void draw() {
        glBegin(GL_TRIANGLES);
            color.set_gl();
            point1.set_gl();
            point2.set_gl();
            point3.set_gl();

            point3.set_gl();
            point2.set_gl();
            point4.set_gl();
        glEnd();
    }
}
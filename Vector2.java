import static org.lwjgl.opengl.GL11.*;
import java.util.Optional;

public class Vector2 {
    public double x;
    public double y;
    public double z;
    private int windowSize = 1000;
    public boolean is3D = false;

    private double k(Vector2 point1, Vector2 point2){
        double deltaY = point1.y - point2.y;
        double deltaX = point1.x - point2.x;
        if (deltaX == 0) {
            return 0;
        }
        return deltaY / deltaX;
    }

    private Tuple<Vector2> clipLineX(Square box, Vector2 point1, Vector2 point2) {
        if (point1.equals(point2)) {
            return new Tuple<Vector2>(point1, point2);
        }
        double k = k(point1, point2);
        Vector2 min;
        Vector2 max;
        if (point1.x > point2.x) {
            max = point1;
            min = point2;
        } else {
            max = point2;
            min = point1;
        }
        if (max.x <= box.x || min.x >= box.x + box.w) {
            return Tuple.empty();
        }
        double maxX = Math.min(max.x, box.x + box.w);
        double minX = Math.max(min.x, box.x);
        double minY = min.y + (minX - min.x) * k;
        double maxY = max.y + (maxX - max.x) * k;
        Vector2 pointmin = new Vector2(minX, minY);
        Vector2 pointmax = new Vector2(maxX, maxY);
        return new Tuple<Vector2>(pointmin, pointmax);
    }

    private Tuple<Vector2> clipLineY(Square box, Vector2 point1, Vector2 point2) {
        if (point1.equals(point2)) {
            return new Tuple<Vector2>(point1, point2);
        }
        double k = k(point1, point2);
        if (k == 0) {
            k = 1;
        }
        Vector2 min;
        Vector2 max;
        if (point1.y > point2.y) {
            max = point1;
            min = point2;
        } else {
            max = point2;
            min = point1;
        }
        if (max.y <= box.y || min.y >= box.y + box.h) {
            return Tuple.empty();
        }
        double maxY = Math.min(max.y, box.y + box.h);
        double minY = Math.max(min.y, box.y);
        double minX = min.x + (minY - min.y) / k;
        double maxX = max.x + (maxY - max.y) / k;
        Vector2 pointmin = new Vector2(minX, minY);
        Vector2 pointmax = new Vector2(maxX, maxY);
        return new Tuple<Vector2>(pointmin, pointmax);
    }

    public Vector2 rayClip(Square box, Vector2 p1, Vector2 p2) {
        Tuple<Vector2> clipX = clipLineX(box, p1, p2);
        if(!clipX.isEmpty) {
            Tuple<Vector2> clipY = clipLineY(box, clipX.obj1, clipX.obj2);
            if(!clipY.isEmpty) {
                Vector2 return1 = clipY.obj1;
                Vector2 return2 = clipY.obj2;
                if (return1.distance(p1) < return2.distance(p1)) {
                    return return1;
                } else {
                    return return2;
                }
            }
        }
        return p2;
    }

    public double distance(Vector2 other) {
        double xsquared = Math.pow((x - other.x), 2);
        double ysquared = Math.pow((y - other.y), 2);
        return Math.sqrt(xsquared + ysquared);
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        is3D = true;
    }

    public boolean equals(Vector2 other) {
        return x == other.x && y == other.y;
    }

    public void set_gl() {
        int wover2 = (windowSize / 2);
        float screenX = ((float) x);
        float screenY = ((float) y);
        if (is3D) {
            glVertex3f(screenX, screenY, (float)(z * -0.0001));
        } else {
            glVertex2f(screenX, screenY);
        }
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 sub(Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }

    public Vector2 mult(double amount) {
        return new Vector2(x * amount, y * amount);
    }

    public double angle(Vector2 other) {
        other = other.sub(this);
        double angle = -Math.atan2(other.x, other.y) + Math.PI / 2;
        return angle;
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public Vector2 normalize() {
        double mag = magnitude();
        x /= mag;
        y /= mag;
        return this;
    }

    public String toString() {
        return "Vector2 X:" + x + " Y:" + y;
    }
}
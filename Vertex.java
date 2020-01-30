import java.util.*;

public class Vertex {
    public Vector3 position;
    public Vector3 normal;
    public ArrayList<Vector3> normals = new ArrayList<>();

    public Vertex(Vector3 position, Vector3 normal) {
        this.position = position;
        this.normal = normal;
    }

    public Vertex(Vector3 position, ArrayList<Vector3> normals) {
        this.position = position;
        this.normals = normals;
        setNormal();
    }

    public void setNormal() {
        normal = new Vector3(0, 0, 0);
        for (Vector3 norm : normals) {
            normal.add(norm);
        }
        normal.normalize();
    }

    void set_gl(Color color) {
        double light = Vector3.Light.dot(normal);
        light /= (Math.PI / 2);
        Color lightedColor;
        if (light < 0) {
            lightedColor = new Color(0, 0, 0);
            lightedColor.set_gl();
            position.set_gl();
            return;
        }
        //light = (light * 0.95) + 0.05;
        if (light > 1) {
            light = 1;
        }
        //lightedColor = color.light(light);
        lightedColor = new Color((int)(255 * light), 0, (int)(255 * light));
        lightedColor.set_gl();
        position.set_gl();
    }

    void set_gl() {
        position.set_gl();
    }

    public void rotX(double angle) {
        position.rotX(angle);
        normal.rotX(angle);
    }

    public void rotY(double angle) {
        position.rotY(angle);
        normal.rotY(angle);
    }

    public void rotZ(double angle) {
        position.rotZ(angle);
        normal.rotZ(angle);
    }
}
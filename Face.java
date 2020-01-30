import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

public class Face {
    public Vertex p1;
    public Vertex p2;
    public Vertex p3;
    public Vertex p4;
    public Vector3 normal;
    public Color color;
    public boolean isQuad = false;
    public boolean hasNormal = false;

    public ArrayList<Vertex> vertecies() {
        ArrayList<Vertex> vertecies = new ArrayList<>();
        vertecies.add(p1);
        vertecies.add(p2);
        vertecies.add(p3);
        if (isQuad) {
            vertecies.add(p4);
        }
        return vertecies;
    }

    public Face(Vector3 p1, Vector3 p2, Vector3 p3, Vector3 p4, Color color) {
        this(
            new Vertex(p1, new Vector3(0, 1, 0)),
            new Vertex(p2, new Vector3(0, 1, 0)),
            new Vertex(p3, new Vector3(0, 1, 0)),
            new Vertex(p4, new Vector3(0, 1, 0)),
            color);   
    }

    public Face(Vertex p1, Vertex p2, Vertex p3, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.color = color;
    }

    public Face(Vertex p1, Vertex p2, Vertex p3, Vertex p4, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.color = color;
        isQuad = true;
    }

    public void setNormal(Vector3 normal) {
        this.normal = normal;
        this.hasNormal = true;
    }

    public void draw() {
        glBegin(GL_TRIANGLES);
            //color.set_gl();
			p1.set_gl(color);
			p2.set_gl(color);
			p3.set_gl(color);
            if(isQuad) {
                p3.set_gl(color);
			    p2.set_gl(color);
                p4.set_gl(color);
            }
		glEnd();
    }
}
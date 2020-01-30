import java.util.*;
import java.io.*;
import static org.lwjgl.opengl.GL11.*;

public class Obj3D extends Obj {
    private Face[] faces;
    private Vertex[] points;
    public Vector3 pos;

    public static Obj3D fromFile(String filename) {
        ArrayList<Vertex> vertecies = new ArrayList<>();
        ArrayList<Vector3> normals = new ArrayList<>();
        ArrayList<Face> faces = new ArrayList<>();
        BufferedReader reader;
        Color color = new Color(255, 0, 255);
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            double x = 0;
            double y = 0;
            double z = 0;
            while (line != null) {
                String[] splitted = line.split(" ");
                switch (splitted[0]) {
                case "v":
                    x = Double.parseDouble(splitted[1]);
                    y = Double.parseDouble(splitted[2]);
                    z = Double.parseDouble(splitted[3]);
                    vertecies.add(new Vertex(new Vector3(x, y, z), new Vector3(0, 0, 0)));
                    break;
                case "vn":
                    x = Double.parseDouble(splitted[1]);
                    y = Double.parseDouble(splitted[2]);
                    z = Double.parseDouble(splitted[3]);
                    normals.add(new Vector3(x, y, z));
                    break;
                case "f":
                    int p1 = Integer.parseInt(splitted[1].split("/")[0]) - 1; // OBJ files are not 0 indexed -_-
                    int p2 = Integer.parseInt(splitted[2].split("/")[0]) - 1;
                    int p3 = Integer.parseInt(splitted[3].split("/")[0]) - 1;
                    int pn = Integer.parseInt(splitted[1].split("/")[2]) - 1; //normal
                    Face newFace;
                    if (splitted.length == 5) {
                        int p4 = Integer.parseInt(splitted[4].split("/")[0]) - 1;
                        newFace = new Face(vertecies.get(p2), 
                                            vertecies.get(p1), 
                                            vertecies.get(p3),
                                            vertecies.get(p4), color);
                    } else {
                        newFace = new Face(vertecies.get(p2), 
                                            vertecies.get(p1), 
                                            vertecies.get(p3), color);
                    }
                    newFace.setNormal(normals.get(pn));
                    for (Vertex vert : newFace.vertecies()) {
                        vert.normals.add(normals.get(pn));
                    }
                    faces.add(newFace);
                    break;
                }

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Vertex vert : vertecies) {
            vert.setNormal();
        }
        return new Obj3D(faces.toArray(new Face[0]));
    }

    public static Obj3D cube(int x, int y, int z, int w, int h, int d, Color color) {
        Vector3 pt0 = new Vector3(x + w, y + h, z + d); // 111
        Vector3 pt1 = new Vector3(x + w, y + h, z - d); // 110
        Vector3 pt2 = new Vector3(x - w, y + h, z - d); // 010
        Vector3 pt3 = new Vector3(x - w, y + h, z + d); // 011
        Vector3 pt4 = new Vector3(x + w, y - h, z + d); // 101
        Vector3 pt5 = new Vector3(x + w, y - h, z - d); // 100
        Vector3 pt6 = new Vector3(x - w, y - h, z - d); // 000
        Vector3 pt7 = new Vector3(x - w, y - h, z + d); // 001
        Random rand = new Random();
        Face[] newFaces = new Face[] { new Face(pt5, pt1, pt6, pt2, new Color(255, 0, 255)), // front
                new Face(pt7, pt6, pt4, pt5, new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255))), // bot
                new Face(pt5, pt1, pt4, pt0, new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255))), // right
                new Face(pt6, pt7, pt2, pt3, new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255))), // left
                new Face(pt2, pt1, pt3, pt0, new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255))), // top
                new Face(pt7, pt4, pt3, pt0, new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255))) // back
        };
        Obj3D obj = new Obj3D(newFaces);
        obj.pos = new Vector3(x, y, z);
        return obj;
    }

    public Obj3D(Face[] faces) {
        this.faces = faces;
        Set<Vertex> newPoints = new HashSet<>();
        for (Face face : faces) {
            newPoints.add(face.p1);
            newPoints.add(face.p2);
            newPoints.add(face.p3);
            if (face.isQuad) {
                newPoints.add(face.p4);
            }
        }
        points = newPoints.toArray(new Vertex[newPoints.size()]);
        pos = new Vector3(0, 0, 0);
        // light normals

    }

    public void rotCenter(Vector3 angles) {
        Vector3 oldPos = pos.dup();
        move(pos.inverse());
        rotX(angles.x);
        rotY(angles.y);
        rotZ(angles.z);
        move(oldPos);
    }

    public void rotX(double angle) {
        for (Vertex point : points) {
            point.rotX(angle);
        }
        for (Face face : faces) {
            if (face.hasNormal) {
                face.normal.rotX(angle);
            }
        }
    }

    public void rotY(double angle) {
        for (Vertex point : points) {
            point.rotY(angle);
        }
        for (Face face : faces) {
            if (face.hasNormal) {
                face.normal.rotY(angle);
            }
        }
    }

    public void rotZ(double angle) {
        for (Vertex point : points) {
            point.rotZ(angle);
        }
        for (Face face : faces) {
            if (face.hasNormal) {
                face.normal.rotZ(angle);
            }
        }
    }

    public void draw() {
        for (Face face : faces) {
            face.draw();
        }
    }

    public void move(Vector3 amount) {
        pos.add(amount);
        for (Vertex p : points) {
            p.position.add(amount);
        }
    }

    public String toString() {
        String str = "";
        for (Vertex p : points) {
            str += p.position + "\n";
        }
        return str;
    }
}

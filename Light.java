import java.util.*;
import static org.lwjgl.opengl.GL11.*;
public class Light {
    private Comparator<Vector2> angleCompare = new Comparator<>() {
        @Override
        public int compare(Vector2 lhs, Vector2 rhs) {
            double angle1 = lhs.angle(MouseHandler.pos);
            double angle2 = rhs.angle(MouseHandler.pos);
            return angle1 > angle2 ? -1 : (angle1 < angle2) ? 1 : 0;
        }
    };

    private ArrayList<Vector2> cuttedPoints;
    private ArrayList<Obj> objects;

    public void drawLightMask() {
		glStencilFunc(GL_NEVER, 1, 0xFF);
		glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);
		glLineWidth(3);
		glBegin(GL_LINE_LOOP);
			for (Vector2 point : cuttedPoints) {
				point.set_gl();
			}
		glEnd();
		glPointSize(2);
		glBegin(GL_POINTS);
			for (Vector2 point : cuttedPoints) {
				point.set_gl();
			}
		glEnd();

		glBegin(GL_TRIANGLE_FAN);
			MouseHandler.pos.set_gl();
			for (Vector2 point : cuttedPoints) {
				point.set_gl();
			}
			cuttedPoints.get(0).set_gl();
		glEnd();

		glStencilFunc(GL_EQUAL, 1, 0xFF);
        glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
	}

    public Light(ArrayList<Obj> objects) {
        this.objects = objects;
    }

    public void lightRay(Vector2 lightPoint) {
        Vector2 point = lightPoint;
		for (Obj obj : objects) {
			if (obj instanceof Square) {
				point = point.rayClip((Square)obj, MouseHandler.pos, point);
			}
        }
        
        if((point.distance(MouseHandler.pos) <= lightPoint.distance(MouseHandler.pos))) {
            cuttedPoints.add(point);
        }
    }
    
    public void createLightPoints() {
        cuttedPoints = new ArrayList<>();
		for(Obj o : objects) {
			ArrayList<Vector2> points = o.lightPoints;
			for(Vector2 point : points) {
                double angle = MouseHandler.pos.angle(point);
                double offset = 0.001;
                Vector2 p1 = new Vector2(MouseHandler.pos.x + Math.cos(angle + offset) * 3000, MouseHandler.pos.y + Math.sin(angle + offset) * 3000);
                lightRay(p1);
                Vector2 p2 = new Vector2(MouseHandler.pos.x + Math.cos(angle - offset) * 3000, MouseHandler.pos.y + Math.sin(angle - offset) * 3000);
                lightRay(p2);
            }
		}
		//sort according to angle
		Collections.sort(cuttedPoints, angleCompare);
    }
}
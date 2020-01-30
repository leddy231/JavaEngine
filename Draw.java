import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBEasyFont.*;
import java.nio.*;
import org.lwjgl.*;
public class Draw {
	public static void line(Vector2 p1, Vector2 p2, Color color) {
		line(p1, p2, color, 2);
	}

	public static void line(Vector2 p1, Vector2 p2, Color color, int lineWidth) {
		glLineWidth(lineWidth); 
		color.set_gl();
		glBegin(GL_LINES);
		p1.set_gl();
		p2.set_gl();
		glEnd();
	}

	public static void rect(Vector2 p1, Vector2 p2, Color color) {
	Vector2 p3 = new Vector2(p2.x, p1.y);
	Vector2 p4 = new Vector2(p1.x, p2.y);
	glBegin(GL_TRIANGLES);
		color.set_gl();
		p1.set_gl();
		p3.set_gl();
		p4.set_gl();

		p4.set_gl();
		p3.set_gl();
		p2.set_gl();
		glEnd();
	}

	public static void triangle(Vector2 p1, Vector2 p2, Vector2 p3, Color color) {
		glBegin(GL_TRIANGLES);
		color.set_gl();
		p1.set_gl();
		p2.set_gl();
		p3.set_gl();
		glEnd();
	}

	public static void text(String text, Vector2 position, Color color) {
		glPushMatrix();

		glTranslated(position.x, position.y, 0);
		glScalef(4f, 4f, 1f);
		ByteBuffer charBuffer = BufferUtils.createByteBuffer(text.length() * 270);
		int quads = stb_easy_font_print(0, 0, text, null, charBuffer);
		glEnableClientState(GL_VERTEX_ARRAY);
		glVertexPointer(2, GL_FLOAT, 16, charBuffer);
		color.set_gl();
		glDrawArrays(GL_QUADS, 0, quads * 4);

		glPopMatrix();
	}

	public static void textTest() {
		String text = "1";
		ByteBuffer charBuffer = BufferUtils.createByteBuffer(text.length() * 270);
		int quads = stb_easy_font_print(0, 0, text, null, charBuffer);
		int index = 0;
		int stride = 0;
		while(index < text.length() * 270) {

		}
	}
}
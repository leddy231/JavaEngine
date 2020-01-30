import static org.lwjgl.opengl.GL11.*;

public class Color {
    public static Color white = new Color(255,255,255);
    public static Color black = new Color(0,0,0);
    public static Color red = new Color(255,0,0);
    public static Color green = new Color(0,255,0);
    public static Color blue = new Color(0,0,255);
    public static Color purple = new Color(255,0,255);
    int r;
    int g;
    int b;
    int a;

    public Color(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public void set_gl() {
        glColor4f(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
    }

    public Color light(double amount) {
        int newR = (int)(r * amount);
        int newG = (int)(g * amount);
        int newB = (int)(b * amount);
        return new Color(newR, newG, newB, a);
    }
}
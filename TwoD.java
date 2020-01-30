import java.util.*;

public class TwoD extends Window {

	private ArrayList<Obj> objects;
	private Light lightGenerator;

	public void update() {
		if(keyboard.keysPressed[256]) {
            close();
        }
		lightGenerator.createLightPoints();
	}

	public void drawFrame() {
		lightGenerator.drawLightMask();
		for(Obj obj : objects) {
			if (!(obj instanceof Corner)) {
				obj.draw();	
			}
		}
		Draw.rect(
			new Vector2(0, 0),
			new Vector2(width, height),
			new Color(255, 255, 0, 30)
		);
	}

	protected void initialize() {
		enableStencil();
		Random rand = new Random();
		objects = new ArrayList<>();
		for(int i = 0; i < 20; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			int w = 100 + rand.nextInt(100);
			int h = 100 + rand.nextInt(100);
			Square s = new Square(x, y, w, h, new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
			objects.add(s);
		}
		ArrayList<Corner> cornersToAdd = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++) {
			Square square = (Square)objects.get(i);
			for(int j = i + 1; j < objects.size(); j++) {
				Square compare = (Square)objects.get(j);
				ArrayList<Corner> corners = square.intersect(compare);
				cornersToAdd.addAll(corners);
			}
		}
		objects.addAll(cornersToAdd);
		objects.add(new Corner(0, 0));
		objects.add(new Corner(0, height));
		objects.add(new Corner(width, 0));
		objects.add(new Corner(width, height));
		lightGenerator = new Light(objects);
		Draw.textTest();
	}

	public TwoD() {
		fullscreen = true;
		title = "2D Light";
	}
}
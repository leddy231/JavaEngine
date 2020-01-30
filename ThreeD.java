
public class ThreeD extends Window {

	public Obj3D[] objects;
	private Obj3D head;

	public void update() {
		if(keyboard.keysPressed[256]) {
            close();
		}
		Vector3 rot = new Vector3(0, 0.5, 0);
		head.rotCenter(rot.mult(deltaTime * 0.1));
	}

	public void drawFrame() {
		if(keyboard.keysPressed[256]) {
            close();
        }

		for (Obj obj : objects) {
			obj.draw();
		}
		Draw.text("Hello",
			new Vector2(100, 100),
			Color.white
		);
	}

	public static void main(String[] args) {
		new ThreeD().run();
	}

	protected void initialize() {
		enableDepth();
	}

	public ThreeD() {
		fullscreen = true;
		title = "3D Test";
		head = Obj3D.fromFile("monkey.obj");
		head.move(new Vector3(0, 0, 10));
		head.rotCenter(new Vector3(180, 0, 0));
		objects = new Obj3D[] { head };
	}
}
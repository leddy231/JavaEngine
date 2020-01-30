public class Main {
	public static void main(String[] args) {
		if(args.length > 0 && args[0].equals("-2")) {
			new TwoD().run();
		} else {
			new ThreeD().run();
		}
	}
}

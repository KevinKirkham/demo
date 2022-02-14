package animation;

public class Driver {

	static Model model;
	static BorderView currentView;
	
	public static void main(String[] args) {
		
		model = new Model();
		currentView = new BorderView();
		
		Shape rocket = new UFO(350, 350, 2, 1);
		
		Model.shapes.add(rocket);
		
	}
	
}

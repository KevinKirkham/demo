package bettterOrganization;

public class Driver {

	static Model model;
	static BorderView currentView;
	
	public static void main(String[] args) {
		
		model = new Model();
		currentView = new BorderView();
		
		Shape rocket = new Rocket(100, 300, 1, 4);
		
		Model.shapes.add(rocket);
		
	}
	
}

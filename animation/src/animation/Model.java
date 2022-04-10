package animation;

import java.util.ArrayList;

public class Model {
	
	static ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	public void update() {
		spawnOrDelete();
		updateShapes();
		if (Model.shapes.size() == 0) {
			System.out.println("All shapes have left the window, stopping timer...");
		}
	}
	
	private void updateShapes() {
		int size = Model.shapes.size();
		for (int i = 0; i < Model.shapes.size(); i++) {
			Model.shapes.get(i).update();
			//System.out.println("Shape index: " + i + " | " + Model.shapes.get(i).toString());
		}
		//System.out.println("Done updating\n");
	}
	
	public void spawnOrDelete() {
		for (int i = 0; i < Model.shapes.size(); i++) {
			if (Model.shapes.get(i).getX() == Model.shapes.get(i).getSpawnX() && Model.shapes.get(i).getY() == Model.shapes.get(i).getSpawnY()) {
				Shape wrapCompanion = Model.shapes.get(i).getWrapCompanion();
				Model.shapes.add(wrapCompanion);
			}
			if (Model.shapes.get(i).getX() == Model.shapes.get(i).getDelX() && Model.shapes.get(i).getY() == Model.shapes.get(i).getDelY())
				Model.shapes.remove(i);
		}
	}
	
	public void addShape(Shape shape) {
		Model.shapes.add(shape);
	}
}

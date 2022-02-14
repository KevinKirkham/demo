package animation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

// Since you'll never have more than one of each image in the animation at one time, have 
// a boolean variable to represent the presence of each type of image. Then in a render()
// method (or something similar) you send out which icons are to be drawn on the screen as
// determined by the status of these boolean variables

public class Model {
	
	public static Timer t;
	private final int DELAY = 15;
	
	static ArrayList<Shape> shapes = new ArrayList<Shape>();
	static ArrayList<String> shapeIDs = new ArrayList<String>();
	
	public Model() {
		init();
	}

	private void init() {
		this.t = new Timer(DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				spawnOrDelete();
				update();
				Driver.currentView.getAnimationPanel().paint(Driver.currentView.getAnimationPanel().getGraphics());
				//Driver.currentView.animation.paintComponents(Driver.currentView.animation.getGraphics());
				if (Model.shapes.size() == 0) {
					System.out.println("All shapes have left the window, stopping timer...");
					Model.t.stop();
				}
			}
		});
	}
	
	private void update() {
		int size = Model.shapes.size();
		for (int i = 0; i < Model.shapes.size(); i++) {
			Model.shapes.get(i).update();
			System.out.println("Shape index: " + i + " | " + Model.shapes.get(i).toString());
		}
		System.out.println("Done updating\n");
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
	
	// This method exists because I would like images to render as the user clicks the button
	// And that can be done here by calling render on the shape when the button is pressed
	public void addShape(Shape shape) {
		Model.shapes.add(shape);
		
		//shape.render(g2);
	}
}

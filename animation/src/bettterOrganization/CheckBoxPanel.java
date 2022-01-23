package bettterOrganization;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class CheckBoxPanel extends JPanel {
	
	JCheckBox rocket;
	JCheckBox ufo;
	JCheckBox bird;

	public CheckBoxPanel(JCheckBox rocket, JCheckBox ufo, JCheckBox bird) {
		this.rocket = rocket;
		this.ufo = ufo;
		this.bird = bird;
		init();
	}
	
	public void init() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(this.rocket);
		this.add(this.ufo);
		this.add(this.bird);
		
		//this.setPreferredSize(new Dimension(200, 150));
	}
	
	public static JCheckBox createRocketCheckBox() {
		JCheckBox rocket = new JCheckBox("Rocket");
		rocket.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent event) {
				if (rocket.isSelected()) {
					Model.shapes.add(new Rocket((int) ((Math.random() * 400)), (int) ((Math.random() * 400)), (int) ((Math.random() * (7-1) + 1)), (int) ((Math.random() * (7-1) + 1))));
				}
				else {
					for (int i = 0; i < Model.shapes.size(); i++) {
						if (Model.shapes.get(i).getShapeID().equals("ROC")) {
							Model.shapes.remove(Model.shapes.get(i));
							i--;
						}
					}
				}
			}
		});
		return rocket;
	}
	
	public static JCheckBox createUFOCheckBox() {
		JCheckBox ufo = new JCheckBox("U.F.O.");
		ufo.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent event) {
				if (ufo.isSelected()) {
				//	Model.shapes.add(new UFO());
				}
				else {
					for (int i = 0; i < Model.shapes.size(); i++) {
						if (Model.shapes.get(i).getShapeID().equals("UFO")) {
							Model.shapes.remove(Model.shapes.get(i));
							i--;
						}
					}
				}
				
			}
		});
		return ufo;
	}
	
	public static JCheckBox createBirdCheckBox() {
		JCheckBox bird = new JCheckBox("Bird");
		bird.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent event) {
				if (bird.isSelected()) { 
				//	Model.shapes.add(new Bird());
				}
				else {
					for (int i = 0; i < Model.shapes.size(); i++) {
						if (Model.shapes.get(i).getShapeID().equals("BRD")) {
							Model.shapes.remove(Model.shapes.get(i));
							i--;
						}
					}
				}
			}
		});
		return bird;
	}
}

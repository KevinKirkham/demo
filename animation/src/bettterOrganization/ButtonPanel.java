package bettterOrganization;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class ButtonPanel extends JPanel {
	
	Buttons buttons;
	
	public ButtonPanel(Buttons buttons) {
		this.buttons = buttons;
		init();
	}
	
	public void init() {
		setLayout(new BorderLayout());
		
		this.add(this.buttons, BorderLayout.CENTER);
		
		// set this at borderlayout center

	
	}

}

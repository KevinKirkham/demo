package animation;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UtilitiesPanel extends JPanel {
	
	CheckBoxPanel checkbox;
	ButtonPanel buttons;

	
	public UtilitiesPanel(CheckBoxPanel checkbox, ButtonPanel buttons) {
		this.checkbox = checkbox;
		this.buttons = buttons;
		init();
	}
	
	public void init() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(this.checkbox);
		this.add(this.buttons);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		
		// make a checkbox panel
		JCheckBox rocket = CheckBoxPanel.createRocketCheckBox();
		JCheckBox ufo = CheckBoxPanel.createUFOCheckBox();
		JCheckBox bird = CheckBoxPanel.createRocketCheckBox();
		
		CheckBoxPanel cbp = new CheckBoxPanel(rocket, ufo, bird);
		
		// make a buttons panel
		JButton play = Buttons.createPlayButton();
		JButton pause = Buttons.createPauseButton();
		JButton exit = Buttons.createExitButton();
		
		ButtonPanel buttons = new ButtonPanel(new Buttons(play, pause, exit));
		
		// make the utilities panel
		UtilitiesPanel utilities = new UtilitiesPanel(cbp, buttons);
		
		// then you can add the utilities panel to the frame that you have created here in this method
		frame.add(utilities);
		
		frame.pack();
	}
}

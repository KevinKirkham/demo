package animation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BorderView {
	
	JFrame frame = new JFrame();
	
	JPanel buttons;
	public AnimationPanel animation;
	JPanel checkBoxes;
	
	/**
	 * Width of the animation panel for this view.
	 */
	public final static int ANIMATION_WIDTH = 500;
	
	/**
	 * Height of the animation panel for this view.
	 */
	public final static int ANIMATION_HEIGHT = 500;
	
	public BorderView() {
		init();
	}
	
	private void init() {
//		frame.setMaximumSize(new Dimension(ANIMATION_WIDTH, ANIMATION_HEIGHT));
//		frame.setMinimumSize(new Dimension(ANIMATION_WIDTH, ANIMATION_HEIGHT));
//		frame.setPreferredSize(new Dimension(ANIMATION_WIDTH, ANIMATION_HEIGHT));
		
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		
		this.buttons = createButtonsPanel();
		this.animation = createAnimationPanel();
		this.checkBoxes = createCheckBoxesPanel();
		
		frame.add(buttons, BorderLayout.NORTH);
		frame.add(animation, BorderLayout.CENTER);
		frame.add(checkBoxes, BorderLayout.SOUTH);
		
		frame.pack();
	}
	
	private JPanel createButtonsPanel() {
		JPanel buttons = new JPanel();
		
		JButton play = createPlayButton();
		JButton pause = createPauseButton();
		JButton show = createShowButton();
		JButton exit = createExitButton();
		
		buttons.add(play);
		buttons.add(pause);
		buttons.add(show);
		buttons.add(exit);
		
		return buttons;
	}
	
	private JPanel createCheckBoxesPanel() {
		JPanel checkBoxes = new JPanel();
		
		JCheckBox rocket = createRocketCheckBox();
		JCheckBox ufo = createUFOCheckBox();
		JCheckBox bird = createBirdCheckBox();
		
		checkBoxes.add(rocket);
		checkBoxes.add(ufo);
		checkBoxes.add(bird);
		
		return checkBoxes;
	}
	
	private AnimationPanel createAnimationPanel() {
		AnimationPanel animation = new AnimationPanel();
		animation.setPreferredSize(new Dimension(ANIMATION_WIDTH, ANIMATION_HEIGHT));
		//animation.setBackground(Color.BLACK);
		animation.setDoubleBuffered(true);
		return animation;
	}
	
	private JButton createPlayButton() {
		JButton play = new JButton("Play");
		play.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent event) {
				Driver.paused = false;
			}
		});
		return play;
	}
	
	private JButton createPauseButton() {
		JButton pause = new JButton("Pause");
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Driver.paused = true;
			}
		});
		return pause;
	}
	
	private JButton createShowButton() {
		JButton show = new JButton("Show");
		show.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent event) {
				Driver.currentView.getAnimationPanel().repaint();
			}
		});
		return show;
	}

	private JButton createExitButton() {
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("Closing application...");
				System.exit(0);
			}
		});
		return exit;
	}
	
	private JCheckBox createRocketCheckBox() {
		JCheckBox rocket = new JCheckBox("Rocket");
		rocket.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent event) {
				if (rocket.isSelected()) {
					Model.shapes.add(new Rocket((int) ((Math.random() * 400)), (int) ((Math.random() * 400)), (int) ((Math.random() * (7-1) + 1)), (int) ((Math.random() * (7-1) + 1))));
				}
				else {
					for (int i = 0; i < Model.shapes.size(); i++) {
						if (Rocket.inIDRange(Model.shapes.get(i).getShapeID())) {
							Model.shapes.remove(Model.shapes.get(i));
							i--;
						}
					}
				}
			}
		});
		return rocket;
	}
	
	private JCheckBox createUFOCheckBox() {
		JCheckBox ufo = new JCheckBox("U.F.O.");
		ufo.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent event) {
				if (ufo.isSelected()) {
					//Model.shapes.add(new UFO());
				}
				else {
					for (int i = 0; i < Model.shapes.size(); i++) {
						if (UFO.inIDRange(Model.shapes.get(i).getShapeID())) {
							Model.shapes.remove(Model.shapes.get(i));
							i--;
						}
					}
				}
			}
		});
		return ufo;
	}
	
	private JCheckBox createBirdCheckBox() {
		JCheckBox bird = new JCheckBox("Bird");
		bird.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent event) {
				if (bird.isSelected()) { 
					//Model.shapes.add(new Bird());
				}
				else {
					for (int i = 0; i < Model.shapes.size(); i++) {
						if (Bird.inIDRange(Model.shapes.get(i).getShapeID())) {
							Model.shapes.remove(Model.shapes.get(i));
							i--;
						}
					}
				}
			}
		});
		return bird;
	}
	
	public JPanel getAnimationPanel() {
		return this.animation;
	}

}

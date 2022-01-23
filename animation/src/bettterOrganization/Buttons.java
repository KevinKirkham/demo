package bettterOrganization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Buttons extends JPanel {

	JButton play;
	JButton pause;
	JButton exit;
	
	public Buttons(JButton play, JButton pause, JButton exit) {
		this.play = play;
		this.pause = pause;
		this.exit = exit;
		init();
	}
	
	public void init() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	
		this.add(play, BorderLayout.CENTER);
		this.add(pause, BorderLayout.CENTER);
		this.add(exit, BorderLayout.CENTER);
		
		this.setPreferredSize(new Dimension(250, 300));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
	}
	
	public static JButton createPlayButton() {
		JButton play = new JButton("Play");
		play.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent event) {
				// button action goes here 
				Model.t.start();
			}
		});
		return play;
	}
	
	public static JButton createPauseButton() {
		JButton pause = new JButton("Pause");
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Model.t.stop();
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

	public static JButton createExitButton() {
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("Closing application...");
				System.exit(0);
			}
		});
		return exit;
	}
	
}

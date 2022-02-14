package animation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AnimationPanelBackground extends JPanel {

	Image background;
	
	public AnimationPanelBackground() {
		init();
	}
	
	public void init() {
		setPreferredSize(new Dimension(500, 500));
		setBackground();
	}
	
	public void setBackground() {
		URL url = this.getClass().getResource("/space.png");
		try {
			this.background = ImageIO.read(url);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		g.drawRect(0, 0, 10, 10);
		g.drawImage(this.background, 0, 0, null);
	}
	
	
	/*
	 * This is for debugging, can be removed
	 */
//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		frame.setVisible(true);
//		AnimationPanelBackground bg = new AnimationPanelBackground();
//		frame.add(bg);
//		frame.pack();
//	}
	
}

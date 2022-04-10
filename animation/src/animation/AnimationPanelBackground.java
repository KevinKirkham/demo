package animation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class AnimationPanelBackground extends JPanel {

	Image bgImage;
	
	public AnimationPanelBackground() {
		init();
	}
	
	public void init() {
		setPreferredSize(new Dimension(500, 500));
		setLayout(new BorderLayout());
		setVisible(true);
		setBackground();
	}
	
	public void setBackground() {
		URL url = this.getClass().getResource("/space.png");
		try {
			this.bgImage = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.bgImage, 0, 0, null);
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
//	
}

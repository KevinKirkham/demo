package animation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class AnimationPanel extends JPanel {
	
	AnimationPanelBackground background;
	
	public AnimationPanel() {
		this.background = new AnimationPanelBackground();
		
	}
	
	public void paint(Graphics g) {
		Image dbImage = createImage(getWidth(), getHeight());
		Graphics dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.background.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < Model.shapes.size(); i++) {
			Model.shapes.get(i).render(g2);
		}
		repaint();
	}
	
}

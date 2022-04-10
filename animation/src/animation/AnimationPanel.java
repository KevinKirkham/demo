package animation;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class AnimationPanel extends JPanel {
	
	AnimationPanelBackground background;
	//BufferedImage background;
	
	public AnimationPanel() {
		this.background = new AnimationPanelBackground();
	}
	
	public void paint(Graphics g) {
		Image dbImage = createImage(getWidth(), getHeight());		// Create an image of dimensions equaling those of the screen
		Graphics dbg = dbImage.getGraphics();						// Return the graphics object of that image
		paintComponent(dbg);										// Update this JPanel to contain the image that we captured
		g.drawImage(dbImage, 0, 0, this);							// Draw that image on this JPanel at (0,0) 
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.background.paintComponent(g);
		renderShapes(g);
		repaint();
	}
	
	/**
	 * Renders each shape present in Model's shapes ArrayList on this animation panel.
	 * @param g2 The Graphics2D object of this animation panel
	 */
	public void renderShapes(Graphics g) {
		for (int i = 0; i < Model.shapes.size(); i++)
			Model.shapes.get(i).render(g);
	}
	
}

package bettterOrganization;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;

// The X and Y position of the ShapeIcon (window that hovers above the shape that allows you to see it) 
// is located at a different X, Y coordinate than the shape itself, resulting in an incomplete image of the shape being visible

public class ShapeIcon implements Icon {
	
	private int width = BorderView.ANIMATION_WIDTH;
	private int height = BorderView.ANIMATION_HEIGHT;
	private MoveableShape shape;
	
	public ShapeIcon(MoveableShape shape) {
		this.shape = shape;
	}

	public int getIconWidth() {
		return this.width;
	}

	public int getIconHeight() {
		return this.height;
	}
	
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		shape.render(g2);		
	}
	
}

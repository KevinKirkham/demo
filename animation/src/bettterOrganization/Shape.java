package bettterOrganization;

import java.awt.Graphics2D;

public abstract class Shape {
	
	protected Animation animation;
	protected Path path;
	protected int x, y;
	protected int height, width;
	protected String shapedID;
	
	public Shape(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Updates the x and y coordinates of the shape according to the path that it is to follow.
	 */
	protected abstract void update();

	/**
	 * Renders the shape on the animation window.
	 * @param g Graphics object of the component in which the shape is to be drawn.
	 */
	protected void render(Graphics2D g) {
		g.drawImage(this.animation.getSprites()[this.animation.getSpriteCounter()], x, y, null);
	}
	
	/**
	 * Returns the wrap companion for this shape. Object type depends on the object type of the shape.
	 * @return The wrap companion for this shape. 
	 */
	protected abstract Shape getWrapCompanion();
	
	protected void setPath(Path path) {
		this.path = path;
	}
	
	protected void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Animation getAnimation() {
		return animation;
	}

	public Path getPath() {
		return path;
	}
	
	public int getSpawnX() {
		return this.path.getSpawnX();
	}
	
	public int getSpawnY() {
		return this.path.getSpawnY();
	}
	
	public int getDelX() {
		return this.path.getEndX();
	}
	
	public int getDelY() {
		return this.path.getEndY();
	}
	
	public abstract String getShapeID();
	
}

package animation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Bird extends Shape {
	
	public static String ID = "BRD";
	private String shapeID;
	
	public Bird(int x, int y, int deltaX, int deltaY) {
		super(x, y);
		this.height = 100;
		this.width = 100;
		this.path = new Path(x, y, deltaX, deltaY, height, width);
		this.animation = new Animation(20, 10, "/bird_sprite_sheet.png", this.height, this.width);
		this.shapeID = Bird.ID;
		expandSprites();
	}
	
	public Bird(int x, int y, Path path, Animation animation, int height, int width) {
		super(x, y);
		this.setPath(path);
		this.setAnimation(animation);
		this.height = height;
		this.width = width;
		this.shapeID = Rocket.ID;
	}
	
	public String toString() {
		return "X: " + this.x + " | Y: " + this.y + " | Slope: " + this.path.getSlope() 
			+ " | Left Int.: " + this.path.getLeftIntercept() + " | Right Int.: " + this.path.getRightIntercept()
			+ " | Spawn: (" + this.getSpawnX() + ", " + this.getSpawnY() + ") | Del: (" + this.getDelX()
			+ ", " + this.getDelY() + ") | Start: (" + this.getStartX() + ", " + this.getStartY() + ")";
	}
	
	/**
	 * Sets this Bird's current X and Y location to the next coordinate present in the points array. 
	 */
	public void update() {
		this.animation.advance();
		int[] nextPoint = this.path.advance();
		this.x = nextPoint[0];
		this.y = nextPoint[1];
	}
	
	public void render(Graphics2D g) {
		super.render(g);
	}
	
	/**
	 * Unique to the Bird, I employ a different method of filling the sprites array. The second half of the array
	 * (from index 10 to 17, inclusive) are filled with copies of the previous elements, specifically from element 
	 * 8 to element 1, inclusive, moving backwards. 
	 * Copies of images is not ideal, but given that pointers to other elements in the array cannot be used in Java, 
	 * this is better than having copies of the sprites we need doubles of stored in the spritesheet itself.
	 */
	private void expandSprites() {
		BufferedImage[] expand = new BufferedImage[18];
		
		int i = 0;
		for (; i < this.animation.getSprites().length; i++)	// Copy the current sprites into new array
			expand[i] = this.animation.getSprites()[i];
		
		for (int back = 2; i < expand.length; i++, back += 2)	// Fill remaining elements with copies of previous elements
			expand[i] = expand[i - back];
		
		this.animation.setSprites(expand);
	}
	
	/**
	 * Returns a wrap companion shape for the Bird. The start, spawn, and end coordinates are gathered into arrays and 
	 * new Path and Animation objects are made for the new Bird. 
	 */
	public Bird getWrapCompanion() {
		System.out.println("Spawning wrap companion for shape " + this.shapeID + "  at: " + path.getStartX() + ", " + path.getStartY());
		
		int[] start = {path.getStartX(), path.getStartY()};
		int[] spawn = {path.getSpawnX(), path.getSpawnY()};
		int[] end = {path.getEndX(), path.getEndY()};
		
		Path wrapPath = new Path(path.getDeltaX(), path.getDeltaY(), path.getSlope(), start, spawn,
				end, path.getPoints(), path.getLeftIntercept(), path.getRightIntercept(), height, width);
		Animation wrapAnimation = new Animation(animation.getDelay(), animation.getSprites(), animation.getTimer());
		
		return new Bird(path.getStartX(), path.getStartY(), wrapPath, wrapAnimation, height, width);
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
	
	public int getDeltaY() {
		return this.path.getDeltaY();
	}

	public int getDeltaX() {
		return this.path.getDeltaX();
	}
	
	public double getSlope() {
		return this.path.getSlope();
	}
	
	public double getLeftInt() {
		return this.path.getLeftIntercept();
	}
	
	public double getRightInt() {
		return this.path.getRightIntercept();
	}
	
	public boolean isWrap() {
		return false;
	}

	public String getShapeID() {
		return this.shapeID;
	}

	public void setShapeID(String shapeID) {
		this.shapeID = shapeID;
	}
	
	public int getStartX() {
		return this.path.getStartX();
	}
	
	public int getStartY() {
		return this.path.getStartY();
	}

	public boolean isWrapCompanion() {
		return false;
	}

}

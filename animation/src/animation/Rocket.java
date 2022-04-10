package animation;

import java.awt.Graphics2D;

/**
 * Inheriting from the Shape class, Rocket is a manifestation of a Shape.
 * @author Kevin Kirkham
 *
 */
public class Rocket extends Shape {

	public static String ID = "ROC";
	private String shapeID;
	
	private boolean reverse = false;
	
	/**
	 * Called when creating a Rocket from scratch as opposed to being called by an existing Rocket object.
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param deltaX Shape's delta X value
	 * @param deltaY Shape's delta Y value
	 */
	public Rocket(int x, int y, int deltaX, int deltaY) {
		super(x, y);
		this.height = 100;
		this.width = 100;
		this.path = new Path(x, y, deltaX, deltaY, height, width);
		this.animation = new Animation(150, 4, "/rocket_sprite_sheet.png", this.height, this.width);
		this.shapeID = Rocket.ID;
	}
	
	/**
	 * Called for the construction of an existing shape's wrap companion - not to create a Rocket object
	 * from scratch. This constructor accepts an Animation object and a Path object that were created 
	 * for the Rocket object that called this constructor.
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param path Path object belonging to the Rocket object that calls this constructor
	 * @param animation Animation object belonging to the Rocket object that calls this constructor
	 * @param height Height of the Rocket object
	 * @param width Width of the Rocket object
	 */
	public Rocket(int x, int y, Path path, Animation animation, int height, int width) {
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
	
	public void update() {
		this.animation.advance();
		checkReverse();	// This is how I am testing reverse direction for a shape right now, definitely going to restructure this
		int[] nextPoint;
		if (!reverse) 
			nextPoint = this.path.advance();
		else 
			nextPoint = this.path.reverse();
		
		this.x = nextPoint[0];
		this.y = nextPoint[1];
		return;
	}
	
	private void checkReverse() {
		
		// If we are in reverse and the next point to be made the current point is the end of the reverse section then negate reverse
		if (reverse) {
			if (this.path.getPoints()[this.path.getPointCounter() - 1][0] == this.path.getRevPathEnd()[0] && 
			this.path.getPoints()[this.path.getPointCounter() - 1][1] == this.path.getRevPathEnd()[1])
				this.reverse = !this.reverse;
		}
		
		// If we aren't in reverse and the next point to be made the current point is the start of the reverse section then negate the reverse
		else 
			if (this.path.getPoints()[this.path.getPointCounter() + 1][0] == this.path.getRevPathStart()[0] && 
			this.path.getPoints()[this.path.getPointCounter() + 1][1] == this.path.getRevPathStart()[1])
				this.reverse = !this.reverse;
	}
	
	public void render(Graphics2D g) {
		super.render(g);
	}
	
	public Rocket getWrapCompanion() {
		System.out.println("Spawning wrap companion for shape " + this.shapeID + "  at: " + path.getStartX() + ", " + path.getStartY());
		
		int[] start = {path.getStartX(), path.getStartY()};
		int[] spawn = {path.getSpawnX(), path.getSpawnY()};
		int[] end = {path.getEndX(), path.getEndY()};
		
		Path wrapPath = new Path(path.getDeltaX(), path.getDeltaY(), path.getSlope(), start, spawn,
				end, path.getPoints(), path.getLeftIntercept(), path.getRightIntercept(), height, width);
		
		Animation wrapAnimation = new Animation(animation.getDelay(), animation.getSprites(), animation.getTimer());
		
		return new Rocket(path.getStartX(), path.getStartY(), wrapPath, wrapAnimation, height, width);
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

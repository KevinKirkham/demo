package animation;

import java.awt.Graphics2D;

/**
 * Inheriting from the Shape class, Rocket is a manifestation of a Shape.
 * @author Kevin Kirkham
 *
 */
public class Rocket extends Shape {

	/**
	 * Prefix unique to Rocket objects, first 12 bits of a Rocket's shape ID. This does not change among Rocket objects.
	 */
	public static final int PREFIX = 0x00100000;
	
	/**
	 * Last 20 bits of a Rocket's shape ID, unique to each instantiation of a Rocket. Gets incremented by one with each new 
	 * Rocket object created.
	 */
	private static int uniqueID = 0x00000000;
	
	private long timer;
	private int delay;
	
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
		this.path = new Path(50, x, y, deltaX, deltaY, height, width);
		this.animation = new Animation(150, 4, "/rocket_sprite_sheet.png", this.height, this.width);
		this.shapeID = Rocket.PREFIX + Rocket.uniqueID;
		Rocket.uniqueID = super.incrementUniqueID(Rocket.uniqueID);		// Increment Rocket class' uniqueID for the next Rocket object
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
	public Rocket(int shapeID, int x, int y, Path path, Animation animation, int height, int width) {
		super(x, y);
		this.setPath(path);
		this.setAnimation(animation);
		this.height = height;
		this.width = width;
		this.shapeID = shapeID;
		
	}
	
	public String toString() {
		return "X: " + this.x + " | Y: " + this.y + " | Slope: " + this.path.getSlope() 
			+ " | Left Int.: " + this.path.getLeftIntercept() + " | Right Int.: " + this.path.getRightIntercept()
			+ " | Spawn: (" + this.getSpawnX() + ", " + this.getSpawnY() + ") | Del: (" + this.getDelX()
			+ ", " + this.getDelY() + ") | Start: (" + this.getStartX() + ", " + this.getStartY() + ")";
	}
	
	public void update() {
		this.animation.advance();
		int[] nextPoint = this.path.advance();
		this.x = nextPoint[0];
		this.y = nextPoint[1];
	}
	
//	private void checkReverse() {
//		
//		// If we are in reverse and the next point to be made the current point is the end of the reverse section then negate reverse
//		if (reverse) {
//			if (this.path.getPoints()[this.path.getPointCounter() - 1][0] == this.path.getRevPathEnd()[0] && 
//			this.path.getPoints()[this.path.getPointCounter() - 1][1] == this.path.getRevPathEnd()[1])
//				this.reverse = !this.reverse;
//		}
//		
//		// If we aren't in reverse and the next point to be made the current point is the start of the reverse section then negate the reverse
//		else 
//			if (this.path.getPoints()[this.path.getPointCounter() + 1][0] == this.path.getRevPathStart()[0] && 
//			this.path.getPoints()[this.path.getPointCounter() + 1][1] == this.path.getRevPathStart()[1])
//				this.reverse = !this.reverse;
//	}
	
	public void render(Graphics2D g) {
		super.render(g);
	}
	
	public Rocket getWrapCompanion() {
		System.out.println("Spawning wrap companion for shape " + String.format("0x%08X", this.shapeID) + "  at: " + path.getStartX() + ", " + path.getStartY());
		
		int[] start = {path.getStartX(), path.getStartY()};
		int[] spawn = {path.getSpawnX(), path.getSpawnY()};
		int[] end = {path.getEndX(), path.getEndY()};
		
		Path wrapPath = new Path(path.getDelay(), path.getTimer(), path.getDeltaX(), path.getDeltaY(), path.getSlope(), start, spawn,
				end, path.getPoints(), path.getLeftIntercept(), path.getRightIntercept(), height, width, path.isReverse());
		
		Animation wrapAnimation = new Animation(animation.getDelay(), animation.getSprites(), animation.getTimer());
		
		this.setWrapped(true);
		
		return new Rocket(this.shapeID, path.getStartX(), path.getStartY(), wrapPath, wrapAnimation, height, width);
	}
	
	/**
	 * Returns true if the provided ID is within the range of IDs dedicated to Rocket objects.
	 * @param id Shape ID to test
	 * @return true if id is a Rocket's ID, else false
	 */
	public static boolean inIDRange(int id) {
		if (id >= 0x00100000 && id <= 0x001FFFFF)
			return true;
		return false;
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

	public int getShapeID() {
		return this.shapeID;
	}

	public void setShapeID(int shapeID) {
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

package animation;

import java.awt.Graphics2D;

public class UFO extends Shape {
	
	/**
	 * Prefix unique to UFO objects, first 12 bits of a Bird's shape ID. This does not change among UFO objects.
	 */
	public static final int PREFIX = 0x00300000;
	
	/**
	 * Last 20 bits of a UFO's shape ID, unique to each instantiation of a UFO. Gets incremented by one with each new 
	 * UFO object created.
	 */
	public static int uniqueID = 0x00000000;
	
	/**
	 * Constructor to be used when created a UFO from scratch as opposed to an existing UFO's wrap companion.
	 * @param x The X-coordinate of the UFO's initial spawn point
	 * @param y The Y-coordinate of the UFO's initial spawn point
	 * @param deltaX The delta X value of the UFO
	 * @param deltaY The delta Y value of the UFO
	 */
	public UFO(int x, int y, int deltaX, int deltaY) {
		super(x, y);
		this.height = 70;
		this.width = 100;
		this.path = new Path(20, x, y, deltaX, deltaY, height, width);
		this.animation = new Animation(150, 6, "/UFO_sprite_sheet.png", this.height, this.width);
		this.shapeID = UFO.PREFIX + UFO.uniqueID;
		UFO.uniqueID = super.incrementUniqueID(UFO.uniqueID);		// Increment UFO class' uniqueID for the next UFO object
	}
	
	/**
	 * The constructor that is to be used when creating a wrap companion UFO for an existing UFO.
	 * @param shapeID The existing UFO's shapeID
	 * @param x	the X-coordinate at which the wrap companion is to spawn
	 * @param y the Y-coordinate at which the wrap companion is to spawn
	 * @param path The Path object of the existing UFO
	 * @param animation	The Animation of the existing UFO
	 * @param height The height of the existing UFO
	 * @param width	The width of the existing UFO
	 */
	public UFO(int shapeID, int x, int y, Path path, Animation animation, int height, int width) {
		super(x, y);
		this.setPath(path);
		//this.path.calibrateCounter();
		this.setAnimation(animation);
		this.height = height;
		this.width = width;
		this.shapeID = shapeID;
	}
	
	public String toString() {
		return "X: " + this.x + " | Y: " + this.y + " | Point: " + path.getPointCounter() + " | Slope: " + this.path.getSlope() 
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

	public void render(Graphics2D g) {
		super.render(g);
	}
	
	public UFO getWrapCompanion() {
		System.out.println("Spawning wrap companion for shape " + String.format("0x%08X", this.shapeID) + "  at: " + path.getStartX() + ", " + path.getStartY());
		
		int[] start = {path.getStartX(), path.getStartY()};
		int[] spawn = {path.getSpawnX(), path.getSpawnY()};
		int[] end = {path.getEndX(), path.getEndY()};
		
//		Path wrapPath = new Path(path.getStartX(), path.getStartY(), path.getDeltaX(), path.getDeltaY(), path.getSlope(), start, spawn,
//				end, path.getPoints(), path.getLeftIntercept(), path.getRightIntercept(), height, width);
		
		//Path(int x, int y, int deltaX, int deltaY, double slope, int[] start, int[] spawn, int[] end, int[][] points, double leftIntercept, double rightIntercept, int shapeHeight, int shapeWidth)
		Path wrapPath = new Path(path.getDelay(), path.getTimer(), getDeltaX(), path.getDeltaY(), path.getSlope(), start, spawn, end,
				path.getPoints(), path.getLeftIntercept(), path.getRightIntercept(), path.getShapeHeight(), path.getShapeWidth(), path.isReverse());
		
		Animation wrapAnimation = new Animation(animation.getDelay(), animation.getSprites(), animation.getTimer());
		
		this.setWrapped(true);
		
		return new UFO(this.shapeID, start[0], start[1], wrapPath, wrapAnimation, height, width);
	}
	
	/**
	 * Returns true if the provided ID is within the range of IDs dedicated to UFO objects.
	 * @param id Shape ID to test
	 * @return true if id is a UFO's ID, else false
	 */
	public static boolean inIDRange(int id) {
		if (id >= 0x00300000 && id <= 0x003FFFFF)
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

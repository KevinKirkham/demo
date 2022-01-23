package bettterOrganization;

import java.awt.Graphics2D;

public class Rocket extends Shape {

	public static String ID = "ROC";
	private String shapeID;
	
	public Rocket(int x, int y, int deltaX, int deltaY) {
		super(x, y);
		this.height = 100;
		this.width = 100;
		this.path = new Path(x, y, deltaX, deltaY, height, width);
		this.animation = new Animation(100, 4, "/rocket_sprite_sheet.png", this.height, this.width);
		this.shapeID = Rocket.ID;
	}
	
	public Rocket(int x, int y, Path path, Animation animation, int height, int width) {
		super(x, y);
		this.setPath(path);
		this.path.calibrateCounter();
		this.setAnimation(animation);
		this.height = height;
		this.width = width;
		this.shapeID = Rocket.ID;
	}
	
	public String toString() {
		return "X: " + this.x + " | Y: " + this.y + " | Slope: " + this.path.getSlope() 
			+ " | Left Int.: " + this.path.getLeftIntercept() + " | Right Int.: " + this.path.getRightIntercept()
			+ " | SpawnX: " + this.getSpawnX() + " | SpawnY: " + this.getSpawnY() + " | DelX: " + this.getDelX()
			+ " | DelY: " + this.getDelY();
	}
	
	public void update() {
		int[] nextPoint = this.path.advance();
		this.x = nextPoint[0];
		this.y = nextPoint[1];
	}
	
	public void render(Graphics2D g) {
		super.render(g);
	}
	
	public Rocket getWrapCompanion() {
		System.out.println("Spawning wrap companion for shape " + this.shapeID + "  at: " + x + ", " + y);
		
		int[] start = {path.getStartX(), path.getStartY()};
		int[] spawn = {path.getSpawnX(), path.getSpawnY()};
		int[] end = {path.getEndX(), path.getEndY()};
		
		Path wrapPath = new Path(path.getStartX(), path.getStartY(), path.getDeltaX(), path.getDeltaY(), path.getSlope(), start, spawn, end, path.getPoints(), path.getLeftIntercept(), path.getRightIntercept(), height, width);
		Animation wrapAnimation = new Animation(animation.getDelay(), animation.getSprites());
		
		return new Rocket(path.getStartX(), path.getStartY(), wrapPath, wrapAnimation, height, width);
	}
	
	/**
	 * Returns true if the object is out of the view of the animation window.
	 */
	public boolean outOfView() {
		if (this.x + MoveableShape.ICON_WIDTH < 0 || this.x > BorderView.ANIMATION_WIDTH 
				|| this.y + MoveableShape.ICON_HEIGHT < 0 || this.y > BorderView.ANIMATION_HEIGHT) {
			return true;
		}
		return false;
	}
	
	public boolean isVisible() {
		if (!outOfView()) return true;
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

	public String getShapeID() {
		return this.shapeID;
	}

	public void setShapeID(String shapeID) {
		this.shapeID = shapeID;
	}

	public boolean isWrapCompanion() {
		return false;
	}

}

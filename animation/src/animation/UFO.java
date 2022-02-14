package animation;

import java.awt.Graphics2D;

public class UFO extends Shape {
	
	public static String ID = "UFO";
	private String shapeID;
	
	public UFO(int x, int y, int deltaX, int deltaY) {
		super(x, y);
		this.height = 100;
		this.width = 100;
		this.path = new Path(x, y, deltaX, deltaY, height, width);
		this.animation = new Animation(100, 6, "/UFO_sprite_sheet.png", this.height, this.width);
		this.shapeID = UFO.ID;
	}
	
	public UFO(int x, int y, Path path, Animation animation, int height, int width) {
		super(x, y);
		this.setPath(path);
		this.path.calibrateCounter();
		this.setAnimation(animation);
		this.height = height;
		this.width = width;
		this.shapeID = UFO.ID;
	}
	
	public String toString() {
		return "X: " + this.x + " | Y: " + this.y + " | Slope: " + this.path.getSlope() 
		+ " | Left Int.: " + this.path.getLeftIntercept() + " | Right Int.: " + this.path.getRightIntercept()
		+ " | Spawn: (" + this.getSpawnX() + ", " + this.getSpawnY() + ") | Del: (" + this.getDelX()
		+ ", " + this.getDelY() + ") | Start: (" + this.getStartX() + ", " + this.getStartY() + ")";
	}
	
	public void incrementID() {
		int id = Integer.parseInt(UFO.ID.substring(3));
		UFO.ID = "UFO-" + ++id;
	}
	
	public void update() {
		int[] nextPoint = this.path.advance();
		this.x = nextPoint[0];
		this.y = nextPoint[1];
	}

	public void render(Graphics2D g) {
		super.render(g);
	}
	
	public UFO getWrapCompanion() {
		System.out.println("Spawning wrap companion for shape " + this.shapeID + "  at: " + path.getStartX() + ", " + path.getStartY());
		
		int[] start = {path.getStartX(), path.getStartY()};
		int[] spawn = {path.getSpawnX(), path.getSpawnY()};
		int[] end = {path.getEndX(), path.getEndY()};
		
		Path wrapPath = new Path(path.getStartX(), path.getStartY(), path.getDeltaX(), path.getDeltaY(), path.getSlope(), start, spawn,
				end, path.getPoints(), path.getLeftIntercept(), path.getRightIntercept(), height, width);
		Animation wrapAnimation = new Animation(animation.getDelay(), animation.getSprites());
		
		return new UFO(path.getStartX(), path.getStartY(), wrapPath, wrapAnimation, height, width);
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

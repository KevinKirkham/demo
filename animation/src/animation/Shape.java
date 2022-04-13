package animation;

import java.awt.Graphics;

/**
 * The Shape acts as the parent from which each of the specific kinds of Shapes inherit. 
 * 
 * The ID system works just like subnetting a network to have IP address ranges: each shape has a unique 32-bit ID which is
 * comprised of two portions, similar to the network and host portions of an IP address. The first 12 bits is the shape's
 * prefix, which is unique to each type of shape. The remaining 20 bits are used for unique instantiations of that type of
 * shape. Doing it this way allows for the creation of exactly 2^12 (4,096) different kinds of shapes, and of those kinds of
 * shapes there can be exactly 2^20 (1,048,576) instantiations. So using this system we can ID exactly 4,096 * 1,048,576
 * (4,294,967,296) unique shapes. A hard limit on the amount of shapes that can be created would let us know if there is some
 * bug that is repeatedly spawning shapes.
 * 
 * @author Kevin Kirkham
 *
 */
public abstract class Shape {
	
	/**
	 * The Animation for the shape.
	 */
	protected Animation animation;
	
	/**
	 * The path that the shape will be traverse.
	 */
	protected Path path;
	
	/**
	 * The X and Y coordinates at which the shape currently sits.
	 */
	protected int x, y;
	
	/**
	 * The height and width of the shape.
	 */
	protected int height, width;
	
	/**
	 * The ID of the shape, used for identifying it for removal from the rendering list.
	 * It is comprised of a prefix, which comes from the kind of shape that the instantiation is, and a unique
	 * portion which is unique to each instantiation of that shape.
	 */
	protected int shapeID;
	
	/**
	 * Indicates if this shape has spawned a wrap companion for itself or not.
	 */
	protected boolean wrapped = false;
	
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
	protected void render(Graphics g) {
		g.drawImage(this.animation.getSprites()[this.animation.getSpriteCounter()], x, y, null);
	}
	
	/**
	 * Returns the wrap companion for this shape. Object type depends on the object type of the shape.
	 * @return The wrap companion for this shape. 
	 */
	protected abstract Shape getWrapCompanion();
	
	/**
	 * This manipulates the Path object of this shape so that it may wrap when traversed in reverse. This Shape's Path's
	 * reverse method is called if this method is called when the reverse boolean of the Path is false, and the dereverse()
	 * method is called if the reverse boolean is true.
	 */
	public void reverse() {
		if (!this.path.isReverse()) 
			this.path.reverse();
		else
			this.path.dereverse();
	}
	
	/**
	 * Increments the uniqueID variable given that it is less than 0x000FFFFF, the maximum number of instantiations we can
	 * have for a given shape. An Exception is thrown if this condition is not met, halting execution of the program. 
	 */
	protected int incrementUniqueID(int uniqueID) {
		try {
			if (uniqueID < 0x000FFFFF) uniqueID++;
			else throw new Exception("Failed to create Rocket - max number of Rockets already created!");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return uniqueID;
	}
	
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
	
	public abstract int getShapeID();

	public boolean isReverse() {
		return this.path.isReverse();
	}

	public void setReverse(boolean reverse) {
		 this.path.setReverse(reverse);
	}

	public boolean isWrapped() {
		return wrapped;
	}

	public void setWrapped(boolean wrapped) {
		this.wrapped = wrapped;
	}
	
	public int getPathDelay() {
		return this.path.getDelay();
	}
	
	public void setPathDelay(int delay) {
		if (delay >= 1 && delay <= 10000)
			this.path.setDelay(delay);
		else
			System.out.println("Delay not within range");
	}
	
	public int getAnimationDelay() {
		return this.animation.getDelay();
	}
	
	public void setAnimationDelay(int delay) {
		this.animation.setDelay(delay);
	}
	
}

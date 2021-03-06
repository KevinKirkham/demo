package animation;

/**
 * The purpose of this class is to define and maintain data and behaviors pertaining to the path that a shape\
 * takes as it moves across the animation window, including significant points along the path as well as 
 * algorithms that are needed to populate the path with graph-friendly points.
 * @author Kevin Kirkham
 *
 */
public class Path {

	/**
	 * The X and Y coordinates that the Shape is currently sitting at on this path.
	 */
	private int x, y;
	
	/**
	 * The amount of displacement in the the X direction and the amount of displacement in the Y direction present in 
	 * the path, given that it is linear.
	 */
	private int deltaX, deltaY;
	
	/**
	 * The slope of the linear path, defined by (y2 - y1) / (x2 - x1)
	 */
	private double slope;
	
	/**
	 * The starting point of the path, the first location at which the shape is drawn in the animation window.
	 */
	private int[] start = new int[2];
	
	/**
	 * The point in this path at which the wrap companion for the shape is triggered to be spawned. The wrap companion
	 * does not spawn at this location, however.
	 */
	private int[] spawn = new int[2];	// Coordinates that when passed over will trigger the spawn of the wrap companion
	
	/**
	 * The last point in this path at which the shape will be displayed in the animation window.
	 */
	private int[] end = new int[2];
	
	/**
	 * The coordinate points that make up the path the shape is to traverse. The shape will be displayed at each one of 
	 * the points stored here in its course along the path.
	 */
	private int[][] points;
	
	/**
	 * Starting point for the section of the path that will be traversed in reverse
	 */
	private int[] revPathStart = new int[2];
	
	/**
	 * Ending point for the section of the path that will be traversed in reverse
	 */
	private int[] revPathEnd = new int[2];
	
	/**
	 * A counter variable that indicates the exact element in the points array that holds that coordinates that the shape is to be printed at.
	 */
	private int pointCounter;
	
	/**
	 * The point at which the path crosses the left side of the screen and the point at which the path crosses the right side of the screen.
	 */
	private double leftIntercept, rightIntercept;
	
	/**
	 * The height of the shape. Used in the calculation of the start, end, and spawn points.
	 */
	private int shapeHeight;
	
	/**
	 * The width of the shape. Used in the calculation of the start, end, and spawn points.
	 */
	private int shapeWidth;
	
	/**
	 * Determines the direction in which the shapes traverses the points. If true, the points are traversed from start to end. Else, they
	 * are traversed from end to start. 
	 */
	private boolean reverse;
	
	/**
	 * 
	 */
	private boolean stopped;
	
	private int delay;
	private long timer;
	
	
	public Path(int delay, int x, int y, int deltaX, int deltaY, int shapeHeight, int shapeWidth) {
		this.x = x;
		this.y = y;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.shapeHeight = shapeHeight;
		this.shapeWidth = shapeWidth;

		this.timer = System.currentTimeMillis();
		this.delay = delay;
		
		this.slope = calculateSlope();
		this.leftIntercept = calculateLeftIntercept();
		this.rightIntercept = calculateRightIntercept();
		calculatePath();

		calibrateCounter();
		
		setReversePoints();
		
		// debugging, can be removed
		//printPath();
	}
	
	/**
	 * To be used for the "passing-on" of a path object to a shape's wrap companion shape.
	 * @param deltaX
	 * @param deltaY
	 * @param slope
	 * @param start
	 * @param spawn
	 * @param end
	 * @param points
	 * @param leftIntercept
	 * @param rightIntercept
	 * @param shapeHeight
	 * @param shapeWidth
	 */
	public Path(int delay, long timer, int deltaX, int deltaY, double slope, int[] start, int[] spawn, 
			int[] end, int[][] points, double leftIntercept, double rightIntercept, int shapeHeight, int shapeWidth, boolean reverse) {
		
		this.delay = delay;
		this.timer = timer;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.slope = slope;
		this.start = start;
		this.spawn = spawn;
		this.end = end;
		this.points = points;
		this.x = start[0];
		this.y = start[1];
		this.leftIntercept = leftIntercept;
		this.rightIntercept = rightIntercept;
		this.shapeHeight = shapeHeight;
		this.shapeWidth = shapeWidth;
		
		if ((this.reverse = reverse))
			this.pointCounter = this.points.length - 1;
		else
			this.pointCounter = 0;
		
		//printPath();	// debugging
	}
	
	/**
	 * Calculates the slope of the path.
	 * @return the path's slope as a double.
	 */
	public double calculateSlope() {
		if (this.deltaX != 0) 
			return (double) this.deltaY / this.deltaX;
		else 
			return 0;
	}
	
	/**
	 * Sets the current active point of this Path object to the row in the points[][]
	 * array indicated by the pointCounter variable.
	 * @return this path's active point
	 */
	public int[] advance() {
		
		// Only change the pointCounter if the delay window has passed
		long now = System.currentTimeMillis();
		if (now - this.timer > this.delay) {
			this.timer = now;	// Update the timer to indicate the last time it was successfully used
			
			int[] currentPoint = points[pointCounter];
			if (!this.reverse)
				this.pointCounter++;
			else
				this.pointCounter--;
			return currentPoint;	
		}
		return points[pointCounter];	// If delay window hasn't passed, return the point that pointCounter is currently pointing to, no change

		
	}
	
	/**
	 * Manipulates the points in the points array to allow the existing points to be traversed in reverse without calculated a set of new points for the reverse path.
	 */
	public void reverse() {
		this.relocateSpawn();
		this.swapStartEnd();
		this.reverse = true;
	}
	
	/**
	 * Undos the manipulation that the reverse() method did, restoring the path back to its original state. It does this by swapping the start and end points and then
	 * calculating the spawn coordinate. 
	 */
	public void dereverse() {
		this.relocateSpawn();
		this.swapStartEnd();
		this.reverse = false;
	}
	
	/**
	 * Swaps the values of the start and end coordinates. It accomplishes this by first creating a temporary 2D array in which the value of start is copied. Then start,
	 * after having a copy of its contents stored into the temp variable, gets overwritten with the contents of end. Lastly, end is overwritten with the contents of the
	 * temporary variable, which holds the original contents of start.
	 */
	private void swapStartEnd() {
		int[] temp = this.start;
		this.start = this.end;
		this.end = temp;
	}

	/**
	 * This relocates the point in the path that triggers the spawn of a wrap companion shape. This is performed by finding how many points away from the beginning of the
	 * path the spawn point is, and then overwriting spawn with the point that is that same distance away from the other end of the path. So if a path consists of 600 
	 * points and the spawn is point 450, then this method will relocate the spawn to point 150 (calculated by points.length - distance, where distance is how far away spawn
	 * is from the start of the path).
	 */
	private void relocateSpawn() {
		//int ogSpawn[] = spawn;
		for (int distance = 0; distance < this.points.length; distance++)
			if (this.points[distance][0] == this.spawn[0] && this.points[distance][1] == this.spawn[1]) {
				//System.out.println("Spawn found at index " + distance);
				//System.out.println("Repositioning to " + (this.points.length - (1 + distance)));
				this.spawn = this.points[this.points.length - ++distance];
			}
		//System.out.print("Spawn (" + spawn[0] + ", " + spawn[1] + ") changed to (" + spawn[0] + ", " + spawn[1] + ")");
	}
	
	/**
	 * Sets the point counter equal to the path's currently active point. Used for when a shape (wrap companion)
	 * receives a Path object from another shape but is to be displayed at a different location than the inherited 
	 * path's active point.
	 * 
	 * Calculated by first determining which column (X or Y) the approximatePath algorithm incremented by 1 when 
	 * computing the points that would make up the shape's path, and then subtracting the value that the shape has 
	 * in the corresponding column from the amount of points that are present in the path.
	 */
	public void calibrateCounter() {

		// This mostly does the job but is terribly inefficient
		for (int i = 0; i < points.length - 1; i++)
			if ((points[i][0] >= x-1 && points[i][0] <= x+1) && (points[i][1] >= y-1 && points[i][1] <= y+1))
				this.pointCounter = i;
			
		
		// This seems to be a working alternative but isn't very tidy
		// Doesn't work when you put a shape at (350, 350) with deltaX = -1 and deltaY = 6
//		if (deltaX > 0) {
//			if (this.points[points.length - 1][0] == BorderView.ANIMATION_WIDTH)
//				this.pointCounter = this.points.length - (BorderView.ANIMATION_WIDTH - this.x);
//			else 
//				this.pointCounter = this.points.length - (BorderView.ANIMATION_HEIGHT - this.y);	
//		}
//		else {
//			if (this.points[points.length - 1][0] == BorderView.ANIMATION_WIDTH)
//				this.pointCounter = BorderView.ANIMATION_WIDTH - this.x;
//			else 
//				this.pointCounter = BorderView.ANIMATION_HEIGHT - this.y;
//		}
	}
	
	/**
	 * Prints the points of this Path formatted in two numbered columns of X and Y.
	 * Mostly used for debugging.
	 */
	public void printPath() {
		
		for (int i = 0; i < this.points.length; i++)
			System.out.println(i + ": " + points[i][0] + " " + points[i][1]);
		
		System.out.println("Counter: " + this.pointCounter);
		System.out.println("end: (" + this.end[0] + ", " + this.end[1] + ")");
		System.out.println("start: (" + this.start[0] + ", " + this.start[1] + ")");
		System.out.println("spawn: (" + this.spawn[0] + ", " + this.spawn[1] + ")");
	}
	
	/**
	 * Calculates the left intercept of the path.
	 * @return left intercept of the path as a double.
	 */
	public double calculateLeftIntercept() {
		return (int) Math.round((-slope * x) + y);
	}

	/**
	 * Calculates the right intercept of the path.
	 * @return right intercept of the path as a double.
	 */
	public double calculateRightIntercept() {
		return (int) Math.round((slope * BorderView.ANIMATION_WIDTH) + leftIntercept);
	}
	
	/**
	 * Populates the points array with the points that the shape is to be rendered on. approximatePath() is
	 * called to find the points present between the start and end points, and the path array consists of the 
	 * start point, the approximated path, and the end point. The amount of points on the path will be different
	 * according to the slope of the path and how much of it gets traversed by the shape on screen.
	 */
	private void calculatePath() {
		calculateStart();	// Calculate the two end points
		calculateEnd();
		
		int[][] between = approximatePath(start[0], start[1], end[0], end[1]); // Points between start and end
		points = new int[between.length + 2][2]; 

		points[0] = start;	// Starting point is the first point in the shape's path
		
		// Fill the points array, except for the first and last points, with what approximatePath() returned
		for (int i = 0; i < between.length; i++) {
			points[i+1][0] = between[i][0];
			points[i+1][1] = between[i][1];
		}
		
		points[points.length - 1] = end;	// End point in the last point in the shape's path
	}
 	
	/**
	 * Returns the point at which this shape's wrap companion should be spawned. This is determined by first
	 * finding out the direction in which the shape is traveling (left or right), then by finding out the location
	 * of the intercept on the other side of the screen. This intercept will be in one of three places, and
	 * depending on which of these three regions it is in determines which coordinate can be fixed relative to a
	 * border of the screen.
	 * 
	 * 	For shapes moving to the left, if right intercept is:
	 * 		Below -shapeHeight -- Y fixed to -shapeHeight
	 * 		Between -shapeHeight and (screenHeight - shapeHeight), inclusive -- X fixed to screenWidth
	 * 		Beyond screenHeight - shapeHeight -- Y fixed to screenHeight
	 * 
	 * 	For shapes moving to the right, if left intercept is:
	 * 		Below -shapeHeight -- Y fixed to -shapeHeight
	 * 		Between -shapeHeight and (screenHeight - shapeHeight) -- X fixed to -shapeWidth
	 * 		Beyond screenHeight - shapeHeight -- Y fixed to screenHeight
	 * 
	 * 	For shapes that don't move left or right, deltaY is observed to see which direction it is then traveling
	 *  (up or down).
	 *  	
	 * @return an array containing two values: the X value and the Y value of the starting location.
	 */
	public void calculateStart() {
		if (deltaX > 0) {
			
			// Starting from top, fix the Y, calculate the X
			if (leftIntercept <= -this.shapeHeight / 2) {
				this.start[1] = -this.shapeHeight;
				this.start[0] = (int) Math.round(((start[1] - leftIntercept) / slope));	// x = (y - b) / m
			}
			
			// Starting from left, fix the X, calculate the Y
			else if (leftIntercept > -this.shapeHeight / 2 && leftIntercept < BorderView.ANIMATION_HEIGHT - (this.shapeHeight / 2)) {
				this.start[0] = -this.shapeWidth;
				this.start[1] = (int) Math.round((slope * start[0]) + leftIntercept);	// y = mx + b
			}
			
			// Starting from the bottom, fix the Y, calculate the X
			else if (leftIntercept >= BorderView.ANIMATION_HEIGHT - (this.shapeHeight / 2)) {
				
				this.start[1] = BorderView.ANIMATION_HEIGHT;
				this.start[0] = (int) Math.round((start[1] - leftIntercept) / slope);	// x = (y - b) / m
			}
		}
		else if (deltaX < 0) {
			if (rightIntercept < -this.shapeHeight / 2) {
				this.start[1] = -this.shapeHeight;
				this.start[0] = (int) Math.round((start[1] - leftIntercept) / slope);	// x = (y - b) / m
			}
			else if (rightIntercept >= -this.shapeHeight / 2 && rightIntercept <= BorderView.ANIMATION_HEIGHT) {
				this.start[0] = BorderView.ANIMATION_WIDTH;
				this.start[1] = (int) Math.round((slope * start[0]) + leftIntercept);	// y = mx + b
			}
			else {
				this.start[1] = BorderView.ANIMATION_HEIGHT;
				this.start[0] = (int) Math.round((start[1] - leftIntercept) / slope);	// x = (y - b) / m
			}
		}
		else if (deltaX == 0) {
			
			this.start[0] = this.x;	// X doesn't change from current X
			
			if (deltaY > 0)		
				this.start[1] = -this.shapeHeight;	// Going down
			else if (deltaY < 0)
				this.start[1] = BorderView.ANIMATION_HEIGHT;	// Going up
		}
	}

	/**
	 * Calculates two things (for the sake of efficiency): 
	 * 	
	 * 		The point in the path at which this shape will be deleted. Derived by finding the first point along
	 * 		the path at which the shape is no longer visible through the animation window
	 * 
	 * 		The point that the shape will be at when its wrap companion is to be spawned. Derived by finding the
	 * 		first point in the path at which one of the shape's borders is touching one of the screen's borders.
	 *  
	 * @return an array containing the X value and the Y value of the ending coordinate.
	 */
	public void calculateEnd() {
		if (deltaX > 0) {
			if (rightIntercept < -this.shapeHeight) {
				this.end[1] = -this.shapeHeight;
				this.end[0] = (int) ((this.end[1] - leftIntercept) / slope);	// x = (y - b) / m
				this.spawn[1] = 0;
				this.spawn[0] = (int) Math.round((this.spawn[1] - leftIntercept) / slope);	// // x = (y - b) / m
			}
			else if (rightIntercept >= -this.shapeHeight && rightIntercept <= BorderView.ANIMATION_HEIGHT) {
				this.end[0] = BorderView.ANIMATION_WIDTH;
				this.end[1] = (int) ((slope * this.end[0]) + leftIntercept);	// y = mx + b
				this.spawn[0] = BorderView.ANIMATION_WIDTH - this.shapeWidth;
				this.spawn[1] = (int) Math.round((slope * this.spawn[0]) + leftIntercept);	// y = mx + b
			}
			else {
				this.end[1] = BorderView.ANIMATION_HEIGHT;
				this.end[0] = (int) ((this.end[1] - leftIntercept) / slope);	// x = (y - b) / m
				this.spawn[1] = BorderView.ANIMATION_HEIGHT - this.shapeHeight;
				this.spawn[0] = (int) Math.round((this.spawn[1] - leftIntercept) / slope);	// x = (y - b) / m
			}
		}
		if (deltaX < 0) {
			if (leftIntercept < -this.shapeHeight) {
				this.end[1] = -this.shapeHeight;
				this.end[0] = (int) ((this.end[1] - leftIntercept) / slope);	// x = (y - b) / m
				this.spawn[1] = 0;
				this.spawn[0] = (int) Math.round((this.spawn[1] - leftIntercept) / slope);	// x = (y - b) / m
			}
			else if (leftIntercept >= -this.shapeHeight && leftIntercept <= BorderView.ANIMATION_HEIGHT - this.shapeHeight) {
				this.end[0] = -this.shapeHeight;
				this.end[1] = (int) ((slope * this.end[0]) + leftIntercept);	// y = mx + b
				this.spawn[0] = 0;
				this.spawn[1] = (int) Math.round((slope * this.spawn[0]) + leftIntercept);	// y = mx + b
			}
			else {
				this.end[1] = BorderView.ANIMATION_HEIGHT;
				this.end[0] = (int) ((this.end[1] - leftIntercept) / slope);	// x = (y - b) / m
				this.spawn[1] = BorderView.ANIMATION_HEIGHT - this.shapeHeight;
				this.spawn[0] = (int) Math.round((this.spawn[1] - leftIntercept) / slope);	// x = (y - b) / m	
			}
		}
		if (deltaX == 0) {
			this.end[0] = this.x;		// X-coordinate doesn't change 
			this.spawn[0] = this.x;
			
			if (deltaY > 0) {
				this.end[1] = BorderView.ANIMATION_HEIGHT;	// Where top of shape touches bottom of screen
				this.spawn[1] = BorderView.ANIMATION_HEIGHT - this.shapeHeight;
			}
			else if (deltaY < 0) {
				this.end[1] = -this.shapeHeight;	// Where shape is no longer visible
				this.spawn[1] = 0;
			}
		}
	}
	
	/**
	 * All we have to do to recalibrate the existing path to be traversed in reverse is just redefine the start, end, and spawn points. Start and end can be swapped,
	 * and spawn will have to be calculated again. 
	 */
	private void setReversePoints() {
		// Get the point just before the spawn point and assign it revPathStart
		int i = this.points.length - 1;
		for (; i > 0; i--) {
			if (this.points[i][0] == this.spawn[0] && this.points[i][1] == this.spawn[1]) {
				this.revPathStart[0] = this.points[--i][0];
				this.revPathStart[1] = this.points[i][1];
			}
		}
		
		// Get the point that is 200 points back from the end of the points list to be the ending point for the reverse section
		this.revPathEnd[0] = this.points[200][0];
		this.revPathEnd[1] = this.points[200][1];
	}
	
	/**
	 * Returns a 2D array of the approximated points that are closest to the path described by drawing a line 
	 * between the coordinates for two points provided (x1, y1, x2, y2). 
	 * 
	 * This is done by first imagining a rectangle created by using the two points as opposing corners then 
	 * observing the longer of the two sides. The longer side will be incrementally traversed by one unit 
	 * increments starting from the first point given to derive the coordinates for that side. On the shorter
	 * side, incrementally the fraction created by dividing the shorter side by the longer side will be added to
	 * the starting coordinate (X or Y, whichever axis has the shorter side). This number is then rounded (to 
	 * obtain a decimal integer or "graph-friendly" point) and used as the second coordinate for the pair to get
	 * an approximate location. This happens exactly longerSide - 1 times, and the the array created is returned.
	 * 
	 * @param x1 X-coordinate of the first point.
	 * @param y1 Y-coordinate of the first point.
	 * @param x2 X-coordinate of the second point.
	 * @param y2 Y-coordinate of the second point.
	 * @return A 2D array containing the points approximated to be between the two given points.
	 */
	public int[][] approximatePath(int x1, int y1, int x2, int y2) {
		int greatest = 0;
		double shortest = 0.0;
		int longerSide = 0;
		int shorterSide = 0;
	
		if (Math.abs(x2 - x1) > Math.abs(y2 - y1)) {
			greatest = x1;
			shortest = y1;
			longerSide = x2 - x1;
			shorterSide = y2 - y1;
		}
		else {
			greatest = y1;
			shortest = x1;
			longerSide = y2 - y1;
			shorterSide = x2 - x1;
		}

		// 2D array with enough space for a coordinate for each point between the two given points
		int[][] points  = new int[Math.abs(longerSide) - 1][2];
		
		for (int i = 0; i < Math.abs(longerSide) - 1; i++) {
			greatest += longerSide / Math.abs(longerSide);	// +1 or -1 depending on sign of longerSide
			shortest += (double) shorterSide / (double) Math.abs(longerSide);	// Exact location of coordinate along the shorter axis

			int round = (int) Math.round(shortest);	// approximate location of coordinate along the shorter axis
			
			if (Math.abs(x2 - x1) > Math.abs(y2 - y1)) {
				points[i][0] = greatest;	// X coordinate
				points[i][1] = round;	// Y coordinate
			}
			else {
				points[i][0] = round;	// X coordinate
				points[i][1] = greatest;	// Y coordinate
			}
		}
		return points;
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

	public int getDeltaX() {
		return deltaX;
	}

	public void setDeltaX(int deltaX) {
		this.deltaX = deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}

	public void setDeltaY(int deltaY) {
		this.deltaY = deltaY;
	}

	public double getSlope() {
		return slope;
	}

	public void setSlope(double slope) {
		this.slope = slope;
	}

	public int[][] getPoints() {
		return points;
	}

	public void setPoints(int[][] points) {
		this.points = points;
	}

	public double getLeftIntercept() {
		return leftIntercept;
	}

	public void setLeftIntercept(double leftIntercept) {
		this.leftIntercept = leftIntercept;
	}

	public double getRightIntercept() {
		return rightIntercept;
	}

	public void setRightIntercept(double rightIntercept) {
		this.rightIntercept = rightIntercept;
	}

	public int getShapeHeight() {
		return shapeHeight;
	}

	public void setShapeHeight(int shapeHeight) {
		this.shapeHeight = shapeHeight;
	}

	public int getShapeWidth() {
		return shapeWidth;
	}

	public void setShapeWidth(int shapeWidth) {
		this.shapeWidth = shapeWidth;
	}
	
	public int getStartX() {
		return this.start[0];
	}
	
	public int getStartY() {
		return this.start[1];
	}
	
	public int getSpawnX() {
		return this.spawn[0];
	}
	
	public int getSpawnY() {
		return this.spawn[1];
	}
	
	public int getEndX() {
		return this.end[0];
	}
	
	public int getEndY() {
		return this.end[1];
	}

	public int getPointCounter() {
		return pointCounter;
	}

	public void setPointCounter(int pointCounter) {
		this.pointCounter = pointCounter;
	}

	public int[] getRevPathStart() {
		return revPathStart;
	}

	public void setRevPathStart(int[] revPathStart) {
		this.revPathStart = revPathStart;
	}

	public int[] getRevPathEnd() {
		return revPathEnd;
	}

	public void setRevPathEnd(int[] revPathEnd) {
		this.revPathEnd = revPathEnd;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public long getTimer() {
		return timer;
	}

	public void setTimer(long timer) {
		this.timer = timer;
	}
	
}
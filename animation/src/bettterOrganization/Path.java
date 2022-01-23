package bettterOrganization;

public class Path {

	private int x, y;
	private int deltaX, deltaY;
	private double slope;
	private int[] start = new int[2];	// Start coordinates
	private int[] spawn = new int[2];	// Coordinates that when passed over will trigger the spawn of the wrap companion
	private int[] end = new int[2];	// End coordinates
	private int[][] points;
	private int pointCounter;
	private double leftIntercept, rightIntercept;
	private int shapeHeight;
	private int shapeWidth;
	
	public Path(int x, int y, int deltaX, int deltaY, int shapeHeight, int shapeWidth) {
		this.x = x;
		this.y = y;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.shapeHeight = shapeHeight;
		this.shapeWidth = shapeWidth;
		
		this.slope = calculateSlope();
		this.leftIntercept = calculateLeftIntercept();
		this.rightIntercept = calculateRightIntercept();
		calculatePath();
		
		calibrateCounter();
	}
	
	public Path(int x, int y, int deltaX, int deltaY, double slope, int[] start, int[] spawn, int[] end, int[][] points, double leftIntercept, double rightIntercept, int shapeHeight, int shapeWidth) {
		this.x = x;
		this.y = y;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.slope = slope;
		this.start = start;
		this.spawn = spawn;
		this.end = end;
		this.points = points;
		this.leftIntercept = leftIntercept;
		this.rightIntercept = rightIntercept;
		this.shapeHeight = shapeHeight;
		this.shapeWidth = shapeWidth;
		this.pointCounter = 0;
	}
	
	public double calculateSlope() {
		if (this.deltaX != 0) 
			return (double) this.deltaY / this.deltaX;
		else 
			return 0;
	}
	
	public int[] advance() {
		int[] currentPoint = new int[2];
		currentPoint[0] = points[pointCounter][0];
		currentPoint[1] = points[pointCounter][1];
		this.pointCounter++;
		return currentPoint;
	}
	
	public void calibrateCounter() {
		for (int i = 0; i < this.points.length; i++) {
			if (this.points[i][0] == this.x && this.points[i][1] == this.y)
				this.pointCounter = i;
		}
	}
	
	public double calculateLeftIntercept() {
		return leftIntercept = (-slope * x) + y;
	}
	
	public double calculateRightIntercept() {
		return (slope * BorderView.ANIMATION_WIDTH) + leftIntercept;
	}
	
	/**
	 * Populates the points array with the points that the shape is to rendered on.
	 */
	private void calculatePath() {
		calculateStartPoint();	// Calculate the two end points
		calculateEndPoint();
		
		int[][] between = approximatePath(start[0], start[1], end[0], end[1]);
		points = new int[between.length + 2][2];

		points[0] = start;
		
		for (int i = 0; i < between.length; i++) {
			points[i+1][0] = between[i][0];
			points[i+1][1] = between[i][1];
		}
		
		points[points.length - 1] = end;
	}
 	
	/**
	 * Calculate the starting point of the path, the point at which this shape's wrap companion should be spawned
	 * @return an array containing two values: the X value and the Y value of the starting location.
	 */
	public void calculateStartPoint() {
		if (deltaX > 0) {
			if (leftIntercept < -this.shapeHeight) {
				this.start[1] = -this.shapeHeight;
				this.start[0] = (int) Math.round(((start[1] - leftIntercept) / slope));
			}
			else if (leftIntercept >= -this.shapeHeight && leftIntercept <= BorderView.ANIMATION_HEIGHT - this.shapeHeight) {
				this.start[0] = -this.shapeWidth;
				this.start[1] = (int) Math.round((slope * start[0]) + leftIntercept);
			}
			else {
				this.start[1] = BorderView.ANIMATION_HEIGHT;
				this.start[0] = (int) Math.round((start[1] - leftIntercept) / slope);
			}
		}
		else if (deltaX < 0) {
			if (rightIntercept < -this.shapeHeight) {
				this.start[1] = -this.shapeHeight;
				this.start[0] = (int) Math.round((start[1] - leftIntercept) / slope);
			}
			else if (rightIntercept >= -this.shapeHeight && rightIntercept <= BorderView.ANIMATION_HEIGHT) {
				this.start[0] = BorderView.ANIMATION_WIDTH;
				this.start[1] = (int) Math.round((slope * start[0]) + leftIntercept);
			}
			else {
				this.start[1] = BorderView.ANIMATION_HEIGHT;
				this.start[0] = (int) Math.round((start[1] - leftIntercept) / slope);
			}
		}
		else if (deltaX == 0) {
			// Means the path is a vertical line or a horizontal line
			// How should we calculate the starting point then?
		}
	}

	/**
	 * Calculate the end point of the path, the point at which a wrap companion will be spawned and the this shape deleted.
	 * @return an array containing two values: the X value and the Y value of the ending location.
	 */
	public void calculateEndPoint() {
		if (deltaX > 0) {
			if (rightIntercept < -this.shapeHeight) {
				this.end[1] = -this.shapeHeight;
				this.end[0] = (int) ((this.end[1] - leftIntercept) / slope);
				this.spawn[1] = 0;
				this.spawn[0] = (int) Math.round((this.spawn[1] - leftIntercept) / slope);
			}
			else if (rightIntercept >= -this.shapeHeight && rightIntercept <= BorderView.ANIMATION_HEIGHT) {
				this.end[0] = BorderView.ANIMATION_WIDTH;
				this.end[1] = (int) ((slope * this.end[0]) + leftIntercept);
				this.spawn[0] = BorderView.ANIMATION_WIDTH - this.shapeWidth;
				this.spawn[1] = (int) Math.round((slope * this.spawn[0]) + leftIntercept);
			}
			else {
				this.end[1] = BorderView.ANIMATION_HEIGHT;
				this.end[0] = (int) ((this.end[1] - leftIntercept) / slope);
				this.spawn[1] = BorderView.ANIMATION_HEIGHT - this.shapeHeight;
				this.spawn[0] = (int) Math.round((this.spawn[1] - leftIntercept) / slope);
			}
		}
		if (deltaX < 0) {
			if (leftIntercept < -this.shapeHeight) {
				this.end[1] = -this.shapeHeight;
				this.end[0] = (int) ((this.end[1] - leftIntercept) / slope);
				this.spawn[1] = 0;
				this.spawn[0] = (int) Math.round((this.spawn[1] - leftIntercept) / slope);
			}
			else if (leftIntercept >= -this.shapeHeight && leftIntercept <= BorderView.ANIMATION_HEIGHT - this.shapeHeight) {
				this.end[0] = -this.shapeHeight;
				this.end[1] = (int) ((slope * this.end[0]) + leftIntercept);
				this.spawn[0] = 0;
				this.spawn[1] = (int) ((slope * this.spawn[0]) + leftIntercept);
			}
			else {
				this.end[1] = BorderView.ANIMATION_HEIGHT;
				this.end[0] = (int) ((this.end[1] - leftIntercept) / slope);
				this.spawn[1] = BorderView.ANIMATION_HEIGHT - this.shapeHeight;
				this.spawn[0] = (int) ((this.spawn[1] - leftIntercept) / slope);
			}
		}
		if (deltaX == 0) {
			
		}
	}
	
	/**
	 * Returns a 2D array of the approximated points that are closest to the path described by drawing a line 
	 * between the coordinates for two points provided (x1, y1, x2, y2). 
	 * This is done by first imagining a rectangle 
	 * created by using the two points as opposing corners then observing the longer of the two sides. The longer 
	 * side will be incrementally traversed by one unit increments starting from the first point given to derive the
	 * coordinates for that side. On the shorter side, incrementally the fraction created by dividing the shorter side 
	 * by the longer side will be added to the starting coordinate (X or Y, whichever axis has the shorter side). This
	 * number is then rounded and used as the second coordinate for the pair to get an approximate location. This happens
	 * exactly longerSide - 1 times, and the the array created is returned.
	 * 
	 * @param x1 X coordinate of the first point.
	 * @param y1 Y coordinate of the first point.
	 * @param x2 X coordinate of the second point.
	 * @param y2 Y coordinate of the second point.
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
	
}
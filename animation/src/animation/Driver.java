package animation;

public class Driver {

	static Model model;
	static BorderView currentView;
	static int updatesPerSecond = 60;
	static boolean paused = true;
	
	public static void main(String[] args) {
		
		// Note: checkReverse() isn't commented out for the Rocket
		
		Driver.model = new Model();
		Driver.currentView = new BorderView();
		Driver driver = new Driver();
		
		Shape bird = new Bird(150, 300, 1, -2);
		Shape ufo = new UFO(200, 350, 2, 3 );
		Shape rocket = new Rocket(150, 339, 2, -1);
		
 		//Driver.model.addShape(bird);
		Driver.model.addShape(ufo);
		//Driver.model.addShape(rocket);
		
		ufo.setAnimationDelay(90);
		ufo.setPathDelay(10);
		
		driver.run();
	}
	
	/**
	 * This method contains the loop that determines the update cycle of the program. 
	 * 
	 * A time value called now is marked down at the beginning of the loop's iteration. This is then compared to the previous value of now, 
	 * stored in lastTime, which first is initialized to the time at which the first line of the method is executed. Knowing that the value now 
	 * will be a larger value than the value of lastTime, the difference between the two times will be positive. This difference indicates how much
	 * time has passed since the last time the loop was executed. The value of lastUpdate is set to now so that the next iteration of the loop has 
	 * an accurate record of when the loop was last updated. A value (nsBetweenUpdates) is defined as how many nanoseconds should pass before a call
	 * to update should be made, and if this difference in time is equal to or larger than that value, it is time to call the update method. After
	 * each call to the update method is made, the time since the last update happened is reduced by nsBetweenUpdates.
	 * 
	 * It may happen that the amount of time that passes between calls to the update method exceeds the nsBetweenUpdates by more than double its value.
	 * It is for this reason that the calls to the update method are contained within a while loop that checks to make sure the time that has passed
	 * since the last update is greater than or equal to nsBetweenUpdates - to ensure that after this inner loop's execution the value of 
	 * sinceLastUpdate is less than nsBetweenUpdates. 
	 * 
	 * The updates variable acts as a counter of how many times the program updates per second. It works in tandem with the updateTimer, which is 
	 * first initialized to the time at which the line that declares the updateTimer is executed, in milliseconds. Milliseconds were chosen here over
	 * nanoseconds because the precision of the measurement of passed time doesn't need to be as precise for counting update cycles as it does for
	 * determining if an update should occur. Each time an update happens, the updates variable is incremented. Then, at the end of each iteration of
	 * the loop, the current time is checked again, and the difference between it and the value of updateTimer is observed. If this difference is
	 * larger than 1000, it means an entire second has passed and we can print to the console how many program updates were counted during that second.
	 * The updates variable is set to 0 and the updateTimer is also increased by 1000 to recalibrate it with the passing time so the time can be
	 * accurately tracked in future iterations of the loop.
	 */
	public void run() {
		long lastUpdate = System.nanoTime();
		long sinceLast = 0;
		int updates = 0;
		long updateTimer = System.currentTimeMillis();
		
		// Define cycle time to be 1/60 of one second, so the game will update once every 60th of a second
		// 1 second = 1,000,000,000 nanoseconds
		long nsBetweenUpdates = 1000000000 / Driver.updatesPerSecond;	
		
		while (true) {
			long now = System.nanoTime();
			sinceLast += (now - lastUpdate);
			lastUpdate = now; // set the time of the last update to now for the next iteration of the loop
			
			// If 1/60 of a second has passed, update the game
			while (sinceLast >= nsBetweenUpdates) {
				if (!paused) {
					Driver.model.update();
					updates++;
				}
				sinceLast -= nsBetweenUpdates;
			}
			
			// If more than one second has passed, print status report for that second
			if (System.currentTimeMillis() - updateTimer > 1000) {
				updateTimer += 1000;
				System.out.println("Updates: " + updates);
				updates = 0;
			}
		}
	}

}

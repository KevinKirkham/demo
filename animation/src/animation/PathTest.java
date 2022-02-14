package animation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PathTest {

	@Test
	void test() {
		
		Shape rocket = new Rocket(350, 350, 4, -1);
		
		int[][] points = rocket.path.approximatePath(0, (int) Math.round(rocket.path.getLeftIntercept()), 500, (int) Math.round(rocket.path.getRightIntercept()));
		
		for (int i = 0; i < points.length; i++)
			System.out.println(points[i][0] + " " + points[i][1]);
		
		System.out.println(rocket.toString());
		
	}

}

package rinosJavaSalesProject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JUnit_returnHighestInt {

	@BeforeEach
	void setUp() throws Exception {

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		int[] nums = {5, 8, 2, 4, 27, 3, 6, 7, 1};
		Auction a = new Auction();
		int result = a.returnHighestInt(nums);
		if(result != 27) {
			fail("The method returned " + result + " instead of 27");
		}
	}

}

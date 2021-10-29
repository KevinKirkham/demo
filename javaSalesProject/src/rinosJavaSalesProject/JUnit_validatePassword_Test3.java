package rinosJavaSalesProject;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JUnit_validatePassword_Test3 {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		String invalidPw = "25pas_sword";
		int result = CreateAccount.validatePassword(invalidPw);
		if(result == 1) {
			fail("The password should have been valid");
		} 
	}

}

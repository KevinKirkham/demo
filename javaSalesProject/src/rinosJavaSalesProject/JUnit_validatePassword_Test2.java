package rinosJavaSalesProject;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class JUnit_validatePassword_Test2 {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		String invalidPw = "pass,word";
		int result = CreateAccount.validatePassword(invalidPw);
		if(result == 0) {
			fail("The password should have been invalid due to the comma character");
		} 
	}

}

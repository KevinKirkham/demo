package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.textEditor.MutableCharacterMatrix;

class DeleteCharacterTest {

	@Test
	void deleteStart() {
		char[][] test = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
		MutableCharacterMatrix matrix = new MutableCharacterMatrix(test);
		
		matrix.deleteCharacter(1, 0);
		
		char[][] confirm = new char[][] { { 'a', 'b', 'c' }, { 'e', 'f' }, { 'g', 'h', 'i' } };
		
		if (compare2DArrays(matrix.getData(), confirm))
			assert(true);
		else
			fail("Delete start fail - the two arrays do not match");
	}
	
	@Test
	void deleteEnd() {
		char[][] test = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
		MutableCharacterMatrix matrix = new MutableCharacterMatrix(test);
		
		matrix.deleteCharacter(1, 2);
		
		char[][] confirm = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e' }, { 'g', 'h', 'i' } };
		
		if (compare2DArrays(matrix.getData(), confirm))
			assert(true);
		else
			fail("Delete end fail - the two arrays do not match");
	}
	
	@Test
	void deleteMiddle() {
		char[][] test = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
		MutableCharacterMatrix matrix = new MutableCharacterMatrix(test);
		
		matrix.deleteCharacter(1, 1);
		
		char[][] confirm = new char[][] { { 'a', 'b', 'c' }, { 'd', 'f' }, { 'g', 'h', 'i' } };
		
		if (compare2DArrays(matrix.getData(), confirm))
			assert(true);
		else
			fail("Delete middle fail - the two arrays do not match");
	}

	// Loop through each element of each row ensuring that the two matrices are the
	// same
	boolean compare2DArrays(char[][] a, char[][] b) {
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < a[i].length; j++)
				if (a[i][j] != b[i][j])
					return false;
		return true;
	}

}

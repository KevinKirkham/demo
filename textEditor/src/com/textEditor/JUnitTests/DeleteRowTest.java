package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.textEditor.MutableCharacterMatrix;

class DeleteRowTest {

	@Test
	void deleteRowStart() {
		char[][] test = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
		MutableCharacterMatrix matrix = new MutableCharacterMatrix(test);

		matrix.deleteRow(0);

		char[][] confirm = new char[][] { { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };

		if (compare2DArrays(matrix.getData(), confirm))
			assert(true);
		else
			fail("Delete row start failed - the arrays are not the same");
	}
	
	@Test
	void deleteRowEnd() {
		char[][] test = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
		MutableCharacterMatrix matrix = new MutableCharacterMatrix(test);

		matrix.deleteRow(2);

		char[][] confirm = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e', 'f' } };

		if (compare2DArrays(matrix.getData(), confirm))
			assert(true);
		else
			fail("Delete row end failed - the arrays are not the same");
	}
	
	@Test
	void deleteRowMiddle() {
		char[][] test = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
		MutableCharacterMatrix matrix = new MutableCharacterMatrix(test);

		matrix.deleteRow(1);

		char[][] confirm = new char[][] { { 'a', 'b', 'c' }, { 'g', 'h', 'i' }};

		if (compare2DArrays(matrix.getData(), confirm))
			assert(true);
		else
			fail("Delete row middle failed - the arrays are not the same");
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

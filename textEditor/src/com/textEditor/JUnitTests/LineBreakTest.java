package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.textEditor.MutableCharacterMatrix;

class LineBreakTest {

	@Test
	void breakMiddle() {
		char[][] test = new char[][] { { 'h', 'e', 'l', 'l', 'o', 'w', 'o', 'r', 'l', 'd' } };
		MutableCharacterMatrix matrix = new MutableCharacterMatrix(test);

		char[][] confirm = new char[][] { { 'h', 'e', 'l', 'l', 'o' }, { 'w', 'o', 'r', 'l', 'd' } };
		matrix.lineBreak(0, 5);

		// Printing the two matrices for a visual comparison
		System.out.println("Breaking at index 5");
		for (int i = 0; i < confirm.length; i++)
			for (int j = 0; j < confirm[i].length; j++)
				System.out.println("confirm[" + i + "][" + j + "] - " + confirm[i][j] + "\tdata[" + i + "][" + j
						+ "] - " + matrix.getData()[i][j]);

		if (compare2DArrays(matrix.getData(), confirm))
			assert (true);
		else
			fail("Break middle fail - the two arrays are not the same");
	}
	
	@Test
	void breakStart() {
		char[][] test = new char[][] { { 'h', 'e', 'l', 'l', 'o', 'w', 'o', 'r', 'l', 'd' } };
		MutableCharacterMatrix matrix = new MutableCharacterMatrix(test);

		char[][] confirm = new char[][] { {}, { 'h', 'e', 'l', 'l', 'o', 'w', 'o', 'r', 'l', 'd' } };
		matrix.lineBreak(0, 0);

		// Printing the two matrices for a visual comparison
		System.out.println("Breaking at the beginning of the matrix");
		for (int i = 0; i < confirm.length; i++)
			for (int j = 0; j < confirm[i].length; j++)
				System.out.println("confirm[" + i + "][" + j + "] - " + confirm[i][j] + "\tdata[" + i + "][" + j
						+ "] - " + matrix.getData()[i][j]);

		if (compare2DArrays(matrix.getData(), confirm))
			assert (true);
		else
			fail("Break middle fail - the two arrays are not the same");
	}
	
	@Test
	void breakEnd() {
		char[][] test = new char[][] { { 'h', 'e', 'l', 'l', 'o', 'w', 'o', 'r', 'l', 'd' } };
		MutableCharacterMatrix matrix = new MutableCharacterMatrix(test);

		char[][] confirm = new char[][] { { 'h', 'e', 'l', 'l', 'o', 'w', 'o', 'r', 'l', 'd' }, {} };
		matrix.lineBreak(0, 10);

		// Printing the two matrices for a visual comparison
		System.out.println("Breaking at the end of the matrix");
		for (int i = 0; i < confirm.length; i++)
			for (int j = 0; j < confirm[i].length; j++)
				System.out.println("confirm[" + i + "][" + j + "] - " + confirm[i][j] + "\tdata[" + i + "][" + j
						+ "] - " + matrix.getData()[i][j]);

		if (compare2DArrays(matrix.getData(), confirm))
			assert (true);
		else
			fail("Break middle fail - the two arrays are not the same");
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

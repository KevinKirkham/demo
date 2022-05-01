package com.textEditor;

public class CharacterMatrixTraversal {

	MutableCharacterMatrix data = new MutableCharacterMatrix();
	Cursor cursor;
	Screen screen;

	private int dataCol;
	private int dataRow;
	private int preferredCol;
	int firstVisibleRow = 0;
	int firstVisibleCol = 0;
	int lastVisibleRow;
	int lastVisibleCol;

	private class Cursor {

		private int width = 3;
		private int height = Font.FONT_HEIGHT;
		private long timer = System.currentTimeMillis();
		private long blinkDelay = 400;
		private boolean visible = true;

		public void blink() {
			long now = System.currentTimeMillis();
			if ((now - this.timer) > this.blinkDelay) {
				this.visible = !this.visible;
				this.timer = now;
			}
		}

		public void prolongBlink() {
			this.timer = System.currentTimeMillis();
			this.visible = true;
		}

		public void render() {
			if (!visible)
				return;
			int x = (dataCol - firstVisibleCol) * (Font.FONT_WIDTH + screen.getColSpacer());
			int y = (dataRow - firstVisibleRow) * (Font.FONT_HEIGHT + screen.getRowSpacer());
			int spriteCounter = 0;
			for (int yTrav = y; yTrav < y + height; yTrav++)
				for (int xTrav = x; xTrav < x + width; xTrav++)
					screen.pixels[(yTrav * screen.getWidth()) + xTrav] = 0x00F91D;
		}

	}

	public CharacterMatrixTraversal(char[][] chars, Screen screen) {
		this.data = new MutableCharacterMatrix(chars);
		this.cursor = new Cursor();
		this.screen = screen;
		this.lastVisibleRow = screen.getMaxVisibleRows();
		this.lastVisibleCol = screen.getMaxVisibleColumns();
	}

	public CharacterMatrixTraversal(Screen screen) {
		this.data = new MutableCharacterMatrix();
		this.cursor = new Cursor();
		this.screen = screen;
		this.lastVisibleRow = screen.getMaxVisibleRows() - 1;
		this.lastVisibleCol = screen.getMaxVisibleColumns() - 1;
		System.out.println("Active Row: " + dataRow + "\tactiveColumn: " + dataCol);
	}

	public void render() {
		int x = 0;
		int y = 0;
		for (int i = firstVisibleRow; i <= lastVisibleRow
				&& i < data.size(); i++, y += Font.FONT_HEIGHT + screen.getRowSpacer(), x = 0) {
			for (int j = firstVisibleCol; j < data.getRow(i).length
					&& j <= lastVisibleCol; j++, x += Font.FONT_WIDTH + screen.getColSpacer()) {
				renderCharacter(data.getCharacter(i, j), x, y, screen);
			}
		}

		// Render cursor according to the active column and row after all onscreen
		// characters have been rendered
		cursor.render();
	}

	public void renderCharacter(char c, int x, int y, Screen screen) {
		// System.out.println("Rendering \'" + c + "\' at (" + x + ", " + y + ")");
		int spritePointer = 0;
		for (int yTrav = y; yTrav < y + Font.FONT_HEIGHT; yTrav++) {
			// System.out.println("Rendering a row");
			for (int xTrav = x; xTrav < x + Font.FONT_WIDTH; xTrav++) {
				// System.out.println("Rendering a column");
				screen.pixels[(yTrav * screen.getWidth()) + xTrav] = FontTable.getSprite(c)[spritePointer++];
			}
		}
	}

	public void keyTyped(char c) {
		this.data.insertCharacter(dataRow, dataCol, c);
		preferredCol = ++dataCol;
		if (dataCol - firstVisibleCol >= screen.getMaxVisibleColumns())
			scrollRight();
		System.out.println("dataRow: " + dataRow + "\tdataCol: " + dataCol);
		System.out.println("First VisibleCol: " + firstVisibleCol + "\tLastVisibleCol: " + lastVisibleCol);
		System.out.println("First VisibleRow: " + firstVisibleRow + "\tLastVisibleRow: " + lastVisibleRow);
	}

	public void enter() {
		this.data.lineBreak(dataRow++, dataCol);
		preferredCol = dataCol = 0;
		if (firstVisibleCol != 0) {
			firstVisibleCol = 0;
			lastVisibleCol = screen.getMaxVisibleColumns() - 1;
		}

		if (dataRow - firstVisibleRow >= screen.getMaxVisibleRows() - 1)
			scrollDown();

		System.out.println("dataRow: " + dataRow + "\tdataCol: " + dataCol);
		System.out.println("First VisibleCol: " + firstVisibleCol + "\tLastVisibleCol: " + lastVisibleCol);
		System.out.println("First VisibleRow: " + firstVisibleRow + "\tLastVisibleRow: " + lastVisibleRow);
	}

	public void backspace() {
		if (dataRow == 0 && dataCol == 0)
			return;

		if (dataCol != 0) {
			data.deleteCharacter(dataRow, --dataCol);

			if ((dataCol - firstVisibleCol) < screen.getMaxVisibleColumns() / 5)
				jumpCol(-(screen.getMaxVisibleColumns() / 5));

		} else {
			System.out.println("Not at beginning of line");
			scrollToEndOfLine();
			data.appendRow(dataRow, data.getRow(dataRow + 1));
			data.deleteRow(dataRow + 1);
		}

		System.out.println("dataRow: " + dataRow + "\tdataCol: " + dataCol);
		System.out.println("First VisibleCol: " + firstVisibleCol + "\tLastVisibleCol: " + lastVisibleCol);
		System.out.println("First VisibleRow: " + firstVisibleRow + "\tLastVisibleRow: " + lastVisibleRow);

	}

	public void scrollToEndOfLine() {
		dataCol = data.getRow(dataRow - 1).length;
		if (dataCol > screen.getMaxVisibleColumns()) {
			lastVisibleCol = dataCol + 1;
			firstVisibleCol = lastVisibleCol - screen.getMaxVisibleColumns();
		} else {
			lastVisibleCol = screen.getMaxVisibleColumns() - 1;
			firstVisibleCol = 0;
		}

		dataRow--;
	}

	public void up() {
		if (dataRow - 1 < 0)
			return;
		dataRow--;
		colPriority();

		if (data.getRow(dataRow).length < dataCol)
			dataCol = data.getRow(dataRow).length;

		if (lastVisibleRow - dataRow > screen.getMaxVisibleRows() - 1)
			scrollUp();

		if (dataCol - (screen.getMaxVisibleColumns() / 5) < 0) {
			firstVisibleCol = 0;
			lastVisibleCol = firstVisibleCol + (screen.getMaxVisibleColumns() - 1);
		} else {
			firstVisibleCol = dataCol - (screen.getMaxVisibleColumns() / 5);
			lastVisibleCol = firstVisibleCol + (screen.getMaxVisibleColumns() - 1);
		}
		System.out.println("dataRow: " + dataRow + "\tdataCol: " + dataCol);
		System.out.println("First VisibleCol: " + firstVisibleCol + "\tLastVisibleCol: " + lastVisibleCol);
		System.out.println("First VisibleRow: " + firstVisibleRow + "\tLastVisibleRow: " + lastVisibleRow);

	}

	public void down() {
		if (dataRow + 1 >= data.size()) // Where data.size() returns the number of rows in the data matrix
			return;
		dataRow++;
		colPriority();

		if (data.getRow(dataRow).length < dataCol)
			dataCol = data.getRow(dataRow).length;

		if (dataRow - firstVisibleRow > screen.getMaxVisibleRows() - 1)
			scrollDown();

		if (lastVisibleCol - dataCol >= screen.getMaxVisibleColumns()) {
			firstVisibleCol = dataCol;
			lastVisibleCol = firstVisibleCol + (screen.getMaxVisibleColumns() - 1);
		}

		System.out.println("dataRow: " + dataRow + "\tdataCol: " + dataCol);
		System.out.println("First VisibleCol: " + firstVisibleCol + "\tLastVisibleCol: " + lastVisibleCol);
		System.out.println("First VisibleRow: " + firstVisibleRow + "\tLastVisibleRow: " + lastVisibleRow);
	}

	// Changing preferredCol here
	public void left() {
		if (dataCol == 0 && dataRow == 0)
			return;

		if (dataCol == 0 && dataRow > 0) {
			dataRow--;
			preferredCol = dataCol = data.getRow(dataRow).length;

			if (lastVisibleRow - dataRow > screen.getMaxVisibleRows() - 1)
				scrollUp();

			if (data.getRow(dataRow).length >= lastVisibleCol) {
				lastVisibleCol = data.getRow(dataRow).length;
				firstVisibleCol = lastVisibleCol - (screen.getMaxVisibleColumns() - 1);
			}

		} else {
			preferredCol = --dataCol;
			if ((dataCol - firstVisibleCol) < screen.getMaxVisibleColumns() / 5)
				jumpCol(-(screen.getMaxVisibleColumns() / 5));
		}

		System.out.println("dataRow: " + dataRow + "\tdataCol: " + dataCol);
		System.out.println("First VisibleCol: " + firstVisibleCol + "\tLastVisibleCol: " + lastVisibleCol);
		System.out.println("First VisibleRow: " + firstVisibleRow + "\tLastVisibleRow: " + lastVisibleRow);
	}

	// Changing preferredCol here
	public void right() {
		// At end of line, right should put cursor on line below current one
		if (dataCol == data.getRow(dataRow).length && dataRow < data.size() - 1) {

			System.out.println("At the end of the line");

			preferredCol = dataCol = 0; // update preferredCol and dataCol to 0
			dataRow++;

			if (dataRow - firstVisibleRow > screen.getMaxVisibleRows() - 1)
				scrollDown();

			if (firstVisibleCol != 0) {
				firstVisibleCol = 0;
				lastVisibleCol = firstVisibleCol + (screen.getMaxVisibleColumns() - 1);
			}

		} else { // Somewhere before the end of the line

			if (dataCol != data.getRow(dataRow).length)
				preferredCol = ++dataCol;

			if ((lastVisibleCol - dataCol) < screen.getMaxVisibleColumns() / 5)
				jumpCol(screen.getMaxVisibleColumns() / 5);

		}
		System.out.println("dataRow: " + dataRow + "\tdataCol: " + dataCol);
		System.out.println("First VisibleCol: " + firstVisibleCol + "\tLastVisibleCol: " + lastVisibleCol);
		System.out.println("First VisibleRow: " + firstVisibleRow + "\tLastVisibleRow: " + lastVisibleRow);
	}

	// Have to change the column if the line doesn't have as many as the previous
	// line we were on
	private void colPriority() {
		if (data.getRow(dataRow).length < preferredCol)
			dataCol = data.getRow(dataRow).length;
	}

	public void jumpCol(int amount) {
		if (firstVisibleCol + amount < 0) {
			firstVisibleCol = 0;
			lastVisibleCol = firstVisibleCol + (screen.getMaxVisibleColumns() - 1);
		} 
		else if (lastVisibleCol + amount > data.getRow(dataRow).length && amount > 0) {
			lastVisibleCol = data.getRow(dataRow).length;
			firstVisibleCol = lastVisibleCol - (screen.getMaxVisibleColumns() - 1);
		} else {
			firstVisibleCol += amount;
			lastVisibleCol = firstVisibleCol + (screen.getMaxVisibleColumns() - 1);
		}
	}

	private void scrollDown() {
		firstVisibleRow++;
		lastVisibleRow++;
	}

	private void scrollUp() {
		firstVisibleRow--;
		lastVisibleRow--;
	}

	private void scrollLeft() {
		firstVisibleCol--;
		lastVisibleCol--;
	}

	private void scrollRight() {
		firstVisibleCol++;
		lastVisibleCol++;
	}

}
package com.textEditor;

public class CharacterMatrixTraversal {

	MutableCharacterMatrix data = new MutableCharacterMatrix();
	Cursor cursor;
	Screen screen;

	int activeRow = 0;
	int activeColumn = 0;
	int firstVisibleRow = 0;
	int firstVisibleColumn = 0;
	int lastVisibleRow;
	int lastVisibleColumn;

	public CharacterMatrixTraversal(char[][] chars, Screen screen, Cursor cursor) {
		this.data = new MutableCharacterMatrix(chars);
		this.cursor = cursor;
		this.screen = screen;
		this.lastVisibleRow = screen.getMaxVisibleRows();
		this.lastVisibleColumn = screen.getMaxVisibleColumns();
	}

	public CharacterMatrixTraversal(Screen screen, Cursor cursor) {
		this.data = new MutableCharacterMatrix();
		this.cursor = cursor;
		this.screen = screen;
		this.lastVisibleRow = screen.getMaxVisibleRows();
		this.lastVisibleColumn = screen.getMaxVisibleColumns();
		System.out.println("Active Row: " + activeRow + "\tactiveColumn: " + activeColumn);
	}

	public void render() {
		int x = 0;
		int y = 0;
		for (int i = firstVisibleRow; i <= lastVisibleRow
				&& i < data.numRows(); i++, y += Font.FONT_HEIGHT + screen.getRowSpacer(), x = 0) {
			for (int j = firstVisibleColumn; j < data.getRow(i).length
					&& j <= lastVisibleColumn; j++, x += Font.FONT_WIDTH + screen.getColSpacer()) {
				renderCharacter(data.getCharacter(i, j), x, y, screen);
			}
		}
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
		this.data.setRow(activeRow, this.data.insertCharacter(this.data.getRow(activeRow), c, activeColumn));
		incrementActiveColumn();

		if (activeColumn - firstVisibleColumn == screen.getMaxVisibleColumns())
			scrollRight();

		System.out.println("Active Row: " + activeRow + "\tactiveColumn: " + activeColumn);
		System.out.println("First Visible " + firstVisibleColumn + "\tLast Visible " + lastVisibleColumn);
	}

	public void enter() {
		this.data.setData(data.insertRow(this.data.getData(), new char[0], activeRow + 1));
		char[] segment = data.rowSegment(activeRow, activeColumn, data.getRow(activeRow).length - 1);
		data.setRow(activeRow + 1, data.appendRow(data.getRow(activeRow + 1), segment));
		data.setRow(activeRow, data.truncateRow(activeRow, activeColumn));
		incrementActiveRow();
		activeColumn = 0;
		cursor.setX(0);
		firstVisibleColumn = 0;
		lastVisibleColumn = screen.getMaxVisibleColumns() - 1;

		if (activeRow - firstVisibleRow == screen.getMaxVisibleRows())
			scrollDown();

		System.out.println("Active Row: " + activeRow + "\tactiveColumn: " + activeColumn);
		System.out.println("First Visible " + firstVisibleColumn + "\tLast Visible " + lastVisibleColumn);
	}

	public void backspace() {
		if (activeRow == 0 && activeColumn == 0)
			return;

		if (activeColumn != 0) {
			data.setRow(activeRow, data.deleteCharacter(data.getRow(activeRow), --activeColumn));
			scrollLeft();
		} else {
			scrollToEndOfLine();
			data.setRow(activeRow, data.appendRow(data.getRow(activeRow), data.getRow(activeRow + 1)));
			data.setData(data.deleteRow(data.getData(), activeRow + 1));
		}

		cursor.calibrateX(activeColumn, firstVisibleColumn);
		cursor.calibrateY(activeRow, firstVisibleRow);
	}

	private void scrollToEndOfLine() {
		activeColumn = data.getRow(activeRow - 1).length;
		if (activeColumn > screen.getMaxVisibleColumns()) {
			lastVisibleColumn = activeColumn + 1;
			firstVisibleColumn = lastVisibleColumn - screen.getMaxVisibleColumns();
		} else {
			lastVisibleColumn = screen.getMaxVisibleColumns() - 1;
			firstVisibleColumn = 0;
		}

		activeRow--;
		cursor.calibrateX(activeColumn, firstVisibleColumn);
		cursor.calibrateY(activeRow, firstVisibleRow);
	}

	// For when you hit right when the cursor is at the end of the line
	private void scrollToBeginningOfLine() {
		if (data.getData().length == activeRow + 1) // Do nothing if you are at the end of the line
			return;
		activeRow++;
		activeColumn = 0;
		lastVisibleColumn = screen.getMaxVisibleColumns() - 1;
		firstVisibleColumn = 0;
		cursor.calibrateX(activeColumn, firstVisibleColumn);
		cursor.calibrateY(activeRow, firstVisibleRow);
	}

	public void up() {
		if (activeRow == 0)
			return; // Do nothing if you're already at the top of the screen
		else if (cursor.getY() == 0) {
			scrollUp();
			decrementActiveRow();
		} else
			decrementActiveRow();
		
		if (data.getRow(activeRow).length < data.getRow(activeRow + 1).length)
			activeColumn = data.getRow(activeRow).length;
		
		cursor.calibrateX(activeColumn, firstVisibleColumn);
		cursor.calibrateY(activeRow, firstVisibleRow);
	}

	public void down() {
		if (activeRow + 1 == data.getData().length)
			return;
		if (cursor.getY() == screen.getHeight() - (Font.FONT_HEIGHT + screen.getRowSpacer())) {
			scrollDown();
			incrementActiveRow();
		} else
			incrementActiveRow();
		
		if (data.getRow(activeRow).length < data.getRow(activeRow - 1).length)
			activeColumn = data.getRow(activeRow).length;

		cursor.calibrateX(activeColumn, firstVisibleColumn);
		cursor.calibrateY(activeRow, firstVisibleRow);
	}

	public void left() {
		if (activeColumn == 0 && activeRow > 0)
			scrollToEndOfLine();
		else if (cursor.getX() == 0) {
			scrollLeft();
			decrementActiveColumn();
		} else
			decrementActiveColumn();

		cursor.calibrateX(activeColumn, firstVisibleColumn);
		System.out.println("Active Row: " + activeRow + "\tactiveColumn: " + activeColumn);
	}

	public void right() {
		if (activeColumn == data.getRow(activeRow).length)
			scrollToBeginningOfLine();
		else if (cursor.getX() == screen.getWidth() - (Font.FONT_WIDTH + screen.getColSpacer())) {
			scrollRight(); // If cursor X is in its rightmost position
			incrementActiveColumn();
		} else
			incrementActiveColumn();

		cursor.calibrateX(activeColumn, firstVisibleColumn);
		System.out.println("Active Row: " + activeRow + "\tactiveColumn: " + activeColumn);
	}

	private void incrementActiveRow() {
		activeRow++;
		cursor.calibrateY(activeRow, firstVisibleRow);
	}

	private void decrementActiveRow() {
		activeRow--;
		cursor.calibrateY(activeRow, firstVisibleRow);
	}

	private void decrementActiveColumn() {
		if (--activeColumn < 0)
			activeColumn = 0;
		if (firstVisibleColumn == 0)
			cursor.calibrateX(activeColumn, firstVisibleColumn);
	}

	private void incrementActiveColumn() {
		activeColumn++;
		cursor.calibrateX(activeColumn, firstVisibleColumn);
	}

	private void setActiveRow(int i) {
		activeRow = i;
		cursor.setY(i - firstVisibleRow);
	}

	private void setActiveColumn(int i) {
		activeColumn = i;
		cursor.setX(i * (Font.FONT_WIDTH + screen.getColSpacer()));
	}

	private void scrollUp() {
		if (--firstVisibleRow < 0)
			firstVisibleRow++;
		if (--lastVisibleRow < 0)
			lastVisibleRow++;
	}

	private void scrollDown() {
		firstVisibleRow++;
		lastVisibleRow++;
	}

	private void scrollLeft() {
		System.out.println("Scrolling Left");
		if (--firstVisibleColumn < 0)
			firstVisibleColumn++;
		if (--lastVisibleColumn < screen.getMaxVisibleColumns() - 1)
			lastVisibleColumn++;
	}

	private void scrollRight() {
		System.out.println("Scrolling Right");
		firstVisibleColumn++;
		lastVisibleColumn++;
	}

	public int getActiveRow() {
		return this.activeRow;
	}

	public int getActiveColumn() {
		return this.activeColumn;
	}

}

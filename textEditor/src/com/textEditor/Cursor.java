package com.textEditor;

public class Cursor {

	private int x = 0;
	private int y = 0;
	private int width = 3;
	private int height = Font.FONT_HEIGHT;
	private int screenWidth;
	private int screenHeight;
	int rowSpacer;
	int colSpacer;
	private long timer = System.currentTimeMillis();
	private long blinkDelay = 400;
	private boolean visible = true;

	public Cursor(int screenWidth, int screenHeight, int rowSpacer, int colSpacer) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.rowSpacer = rowSpacer;
		this.colSpacer = colSpacer;
	}

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

	public void update(int xActive, int yActive) {
		if (x != xActive || y != yActive) {
			this.timer = System.currentTimeMillis();
			this.visible = true;
		}

		this.x = xActive;
		this.y = yActive;

		long now = System.currentTimeMillis();
		if ((now - this.timer) > this.blinkDelay) {
			this.visible = !this.visible;
			this.timer = now;
		}
	}

	public void render(Screen screen) {
		if (!visible)
			return;
		int spriteCounter = 0;
		for (int yTrav = y; yTrav < y + height; yTrav++)
			for (int xTrav = x; xTrav < x + width; xTrav++)
				screen.pixels[(yTrav * screen.getWidth()) + xTrav] = 0x00F91D;
	}

	// I don't know why the cursor displays at the wrong location when activeColumn
	// is 40 and firstVisibleColumn is 1
	public void calibrateX(int activeColumn, int firstVisibleColumn) {
		this.x = (activeColumn - firstVisibleColumn) * (Font.FONT_WIDTH + colSpacer);
		prolongBlink();
		
		// This shouldn't need to be here
		if (this.x == 40 * Font.FONT_WIDTH + colSpacer)
			this.x -= Font.FONT_WIDTH + colSpacer;
		System.out.println("Cursor X: " + this.x + "\tCursor Y: " + this.y);
	}

	public void calibrateY(int activeRow, int firstVisibleRow) {
		this.y = (activeRow - firstVisibleRow) * (Font.FONT_HEIGHT + rowSpacer);
		prolongBlink();
		
		if (this.y == 21 * Font.FONT_HEIGHT + rowSpacer)
			this.y -= Font.FONT_HEIGHT + rowSpacer;
		System.out.println("Cursor X: " + this.x + "\tCursor Y: " + this.y);
	}

//	public void incrementX() {
//		if ((this.x += (Font.FONT_WIDTH + colSpacer)) > screenWidth - Font.FONT_WIDTH)
//			this.x = screenWidth - Font.FONT_WIDTH;
//		prolongBlink();
//	}
//
//	public void decrementX() {
//		if ((this.x -= (Font.FONT_WIDTH + colSpacer)) < 0)
//			this.x = 0;
//		prolongBlink();
//	}

//	public void incrementY() {
//		if ((this.y += (Font.FONT_HEIGHT + rowSpacer + 1)) > screenHeight - Font.FONT_HEIGHT)
//			this.y = screenHeight - Font.FONT_HEIGHT;
//		prolongBlink();
//	}

//	public void decrementY() {
//		if ((this.y -= (Font.FONT_HEIGHT + rowSpacer + 1)) < 0)
//			this.y = 0;
//		prolongBlink();
//	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		if (x <= screenWidth - Font.FONT_WIDTH)
			this.x = x;
		else
			this.x = screenWidth - Font.FONT_WIDTH;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public long getTimer() {
		return timer;
	}

	public void setTimer(long timer) {
		this.timer = timer;
	}

	public long getBlinkDelay() {
		return blinkDelay;
	}

	public void setBlinkDelay(long blinkDelay) {
		this.blinkDelay = blinkDelay;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}

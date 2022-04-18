package com.textEditor;

public class Cursor {

	private int x, y;
	private int width = 3;
	private int height = Font.FONT_HEIGHT;
	private long timer = System.currentTimeMillis();
	private long blinkDelay = 400;
	private boolean visible = true;

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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
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

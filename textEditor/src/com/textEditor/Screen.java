package com.textEditor;

public class Screen {
	
	public final int LINE_SPACER = 2; // pixels between rows, only relevant when displaying text onscreen
	public final int CHARACTER_SPACER = 0;
	
	public  int[] pixels;
	private int width;
	private int height;
	
	public Screen(int[] pixels, int width, int height) {
		this.pixels = pixels;
		this.width = width; 
		this.height = height;
	}
	
	public void renderBackground() {
		for (int y = 0; y < this.height; y++) 
			for (int x = 0; x < this.width; x++)
				this.pixels[(y * this.width) + x] = 0;
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

	public int[] getPixels() {
		return pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

}

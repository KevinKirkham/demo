package com.textEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Font {

	public static final int FONT_HEIGHT = 8;
	public static final int FONT_WIDTH = 8;

	public static int[][] uppercase = new int[26][FONT_WIDTH * FONT_HEIGHT];
	public static int[][] lowercase = new int[26][FONT_WIDTH * FONT_HEIGHT];
	public static int[][] numbers = new int[10][FONT_WIDTH * FONT_HEIGHT];
	public static int[][] special = new int[28][FONT_WIDTH * FONT_HEIGHT];
	
	public static FontTable table;	

	public Font() {
		URL url = this.getClass().getResource("/font.png");
		try {
			BufferedImage spriteSheet = ImageIO.read(url);
			int[][] sprites = spriteSlicer(spriteSheet, FONT_WIDTH, FONT_HEIGHT);
			fillUppercase(sprites);
			fillLowercase(sprites);
			fillNumbers(sprites);
			fillSpecial(sprites);
			this.table = new FontTable();
		} catch (Exception e) {
			System.out.println("Spritesheet file does not exist");
		}
	}

	private static void fillUppercase(int[][] sprites) {
		for (int i = 0; i < Font.uppercase.length; i++)
			Font.uppercase[i] = sprites[i];
	}

	private static void fillLowercase(int[][] sprites) {
		for (int i = 0; i < Font.lowercase.length; i++) {
			Font.lowercase[i] = sprites[i + 26];
		}
	}

	private static void fillNumbers(int[][] sprites) {
		for (int i = 0; i < Font.numbers.length; i++) {
			Font.numbers[i] = sprites[i + 26 + 26];
		}
	}

	private static void fillSpecial(int[][] sprites) {
		Font.special[0] = sprites[62];
		Font.special[1] = sprites[63];
		for (int i = 2; i < Font.special.length; i++) {
			Font.special[i] = sprites[i + 76];
		}
	}

	/**
	 * Parses each sprite out of a sprite sheet and stores each sprite's pixel data
	 * into a 2D array in which each row contains the pixel data for a single
	 * sprite, formatted in the same way as the pixel data of the BufferedImage to
	 * which it will be rendered.
	 * 
	 * 
	 * 
	 * @param spriteWidth  Width of each sprite on the sheet
	 * @param spriteHeight Height of each sprite on the sheet
	 */
	public static int[][] spriteSlicer(BufferedImage spriteSheet, int spriteWidth, int spriteHeight) {
		int numSprites = (spriteSheet.getWidth() / spriteWidth) * (spriteSheet.getHeight() / spriteHeight);
		int[][] sprites = new int[numSprites][spriteWidth * spriteHeight];
		int[] pixels = spriteSheet.getRGB(0, 0, spriteSheet.getWidth(), spriteSheet.getHeight(), null, 0,
				spriteSheet.getWidth());

		int colStep = 0; // Amount of pixels (columns) that need to be skipped in each row
		int rowStep = 0; // Amount of rows that need to be skipped
		int i = 0; // Index that we use to traverse the pixels array of the spritesheet
		int shHeight = 0; // How many spriteHeights we have gone down the spritesheet
		int spHeight = 0; // How many rows we have moved down the screen, we increase shHeight for every
							// spriteHeight down we move
		int spWidth = 0; // How many rows spriteWidth pixels long have been traversed (moving
							// horizontally, see conditional)
		int pixel = 0; // Individual pixels

		for (; shHeight < spriteSheet.getHeight()
				/ spriteHeight; colStep = 0, spHeight = 0, shHeight++, rowStep += spriteSheet.getWidth() / spriteWidth)
			for (; spHeight < spriteHeight; spHeight++, colStep += spriteWidth, spWidth = 0)
				for (; spWidth < spriteSheet.getWidth() / spriteWidth; spWidth++, pixel -= spriteWidth)
					for (; pixel < spriteWidth; pixel++)
						sprites[spWidth + rowStep][pixel + colStep] = pixels[i++];
		return sprites;
	}

}

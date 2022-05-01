package com.textEditor;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Driver extends Canvas {
	// OG resolution: 320 x 200
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int SCALE = 2;
	public static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public static int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	public static int updatesPerSecond = 30;
	public Screen screen = new Screen(pixels, WIDTH, HEIGHT, 1, 0);
	public Font font = new Font();
	
	CharacterMatrixTraversal data; 
	
	//Cursor cursor = new Cursor(screen.getWidth(), screen.getHeight(), screen.getRowSpacer(), screen.getColSpacer());
	
	public void run() {
		long lastUpdate = System.nanoTime();
		long sinceLast = 0;
		int updates = 0;
		long updateTimer = System.currentTimeMillis();

		// Define cycle time to be 1/60 of one second, so the game will update once
		// every 60th of a second
		// 1 second = 1,000,000,000 nanoseconds
		long nsBetweenUpdates = 1000000000 / Driver.updatesPerSecond;

		while (true) {
			long now = System.nanoTime();
			sinceLast += (now - lastUpdate);
			lastUpdate = now; // set the time of the last update to now for the next iteration of the loop

			// If 1/60 of a second has passed, update the game
			while (sinceLast >= nsBetweenUpdates) {
				updates++;
				update();
				render();

				sinceLast -= nsBetweenUpdates;
			}

			// If more than one second has passed, print status report for that second
			if (System.currentTimeMillis() - updateTimer > 1000) {
				updateTimer += 1000;
				//System.out.println("Updates: " + updates);
				updates = 0;
			}
		}
	}

	public void render() {
		// 1) get the BufferStrategy present in this Canvas object (probably overkill for a text editor)
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3); // Triple buffering - if you don't do this you have screen tearing
			return; // Don't continue with the render method, just return to caller
		}

		// 2) Manipulate the pixels of your image to your desire
		screen.renderBackground();
		data.render();
		//cursor.render(screen);
		
		// 3) Using the Graphics object of the BufferStrategy object, draw our
		// BufferedImage, dispose of the Graphics object, then show the alterations made
		// to the BufferStrategy
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public void update() {
		//cursor.blink();
		//System.out.println("Active Row: " + matrix.activeRow + " Active column: " + matrix.activeColumn);
	}

	public static void main(String[] args) {
		Driver text = new Driver();
		JFrame frame = new JFrame("Text Editor");
		frame.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(text);
		text.setFocusTraversalKeysEnabled(false);	// So all keys are read
		text.data = new CharacterMatrixTraversal(text.screen);
		text.addKeyListener(new InputHandler(text.data));
		text.run();
	}
}

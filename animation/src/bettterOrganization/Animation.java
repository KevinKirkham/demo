package bettterOrganization;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Animation {
	
	private Timer timer;
	public int delay;
	private URL url;
	private int height, width;	// height and width of each sprite
	private BufferedImage[] sprites;
	private int spriteCounter;
	
	public Animation(int delay, int spriteCount, String spriteSheet, int height, int width) {
		this.delay = delay;
		this.sprites = new BufferedImage[spriteCount];
		this.url = this.getClass().getResource(spriteSheet);
		this.height = height;
		this.width = width;
		
		loadSprites();
		setTimer();
	}
	
	public Animation(int delay, BufferedImage[] sprites) {
		this.delay = delay;
		this.sprites = sprites;
		
		setTimer();
	}
	
	/**
	 * Grabs the sprites out of the spritesheet and loads them into the sprite array.
	 */
	public void loadSprites() {
		try {
			BufferedImage spriteSheet = ImageIO.read(this.url);
			for (int i = 0, x = 0; i < this.sprites.length; i++, x += width + 1)
				this.sprites[i] = spriteSheet.getSubimage(x, 0, width, height);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Establishes and begins the timer that determines the speed at which the animation will advance.
	 */
	void setTimer() {
		// Create the timer that determines the speed of the animation
		this.timer = new Timer(delay, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incrementSpriteCounter();
			}
		});
		this.timer.start();
	}

	/**
	 * Increments the sprite counter. In the event that the counter is pointing at the last sprite in 
	 * the list, the counter is reset to 0.
	 */
	public void incrementSpriteCounter() {
		if (this.spriteCounter == this.sprites.length - 1)
			this.spriteCounter = 0;
		else spriteCounter++;
	}
	
	public int getDelay() {
		return this.delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public BufferedImage[] getSprites() {
		return sprites;
	}

	public void setSprites(BufferedImage[] sprites) {
		this.sprites = sprites;
	}

	public int getSpriteCounter() {
		return spriteCounter;
	}

	public void setSpriteCounter(int spriteCounter) {
		this.spriteCounter = spriteCounter;
	}
	
}

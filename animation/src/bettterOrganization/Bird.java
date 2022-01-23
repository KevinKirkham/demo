package bettterOrganization;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Bird implements MoveableShape {
	
	public static String ID = "BRD";
	private String shapeID;
	private int x, y;
	private int deltaX = -1;
	private int deltaY = 1;
	private int slope;
	private int wrapX;
	private int wrapY;
	private int leftInt;
	private int rightInt;
	private double distance;
	private int spawnWrapX, spawnWrapY;
	
	private BufferedImage[] sprites = new BufferedImage[10];
	private int spriteCounter;
	private int direction = 1;
	private Timer timer;
	private final int DELAY = 5;
	
	/**
	 * This is set to true when a wrap companion gets made for this shape.
	 * It does NOT indicate whether this object is a wrap companion object or not.
	 * 
	 * Setting this flag to true when the shape gets a wrap companion made for it stops 
	 * duplicate wrap companion objects from being spawned for a shape when it is on a 
	 * trajectory that crosses two boundaries thus triggering the creation of two wrap 
	 * companions. This would be the case when a shape is headed for a corner of the screen 
	 * but one side crosses one boundary before its adjacent side crosses the adjacent boundary
	 * of the screen.
	 * 
	 * In such a case, the first boundary is crosses according to the shape's 
	 * trajectory, a wrap companion is made according to that boundary crossing, then this
	 * flag is set to true. When the adjacent boundary of the shape crosses the adjacent boundary
	 * of the screen, a second wrap companion is not made because a check for this flag being true
	 * happens before wrap companions are spawned
	 */
	private boolean wrapCompanion;
	
	public Bird() {
		
		// Math.random() * (max - min + 1) + min
		this.x = (int) (Math.random() * (BorderView.ANIMATION_WIDTH - MoveableShape.ICON_WIDTH) + 1);
		this.y = (int) (Math.random() * (BorderView.ANIMATION_HEIGHT - MoveableShape.ICON_HEIGHT) + 1);
		this.shapeID = Bird.ID;
		this.spriteCounter = 1;
		init();
	}
	
	public Bird(int x, int y) {
		this.x = x;
		this.y = y;
		this.shapeID = Bird.ID;
		this.spriteCounter = 1;
		init();
	}
	
	public Bird(int x, int y, String shapeID) {
		this.x = x;
		this.y = y;
		this.shapeID = shapeID;
		this.spriteCounter = 1;
		init();
	}
	
	public void incrementID() {
		int id = Integer.parseInt(Bird.ID.substring(3));
		Bird.ID = "BRD-" + ++id;
	}
	
	public void init() {
		loadSprites();
		
		this.timer = new Timer(DELAY, new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				incrementSpriteCounter();
			}
		});
		this.timer.start();
	}
	
	public void loadSprites() {
		URL url = this.getClass().getResource("/bird_sprite_sheet.png");
		try {
			BufferedImage spriteSheet = ImageIO.read(url);
			sprites[0] = spriteSheet.getSubimage(0, 0, 100, 100);
			sprites[1] = spriteSheet.getSubimage(100, 0, 100, 100);
			sprites[2] = spriteSheet.getSubimage(200, 0, 100, 100);
			sprites[3] = spriteSheet.getSubimage(300, 0, 100, 100);
			sprites[4] = spriteSheet.getSubimage(400, 0, 100, 100);
			sprites[5] = spriteSheet.getSubimage(500, 0, 100, 100);
			sprites[6] = spriteSheet.getSubimage(600, 0, 100, 100);
			sprites[7] = spriteSheet.getSubimage(700, 0, 100, 100);
			sprites[8] = spriteSheet.getSubimage(800, 0, 100, 100);
			sprites[9] = spriteSheet.getSubimage(900, 0, 100, 100);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void incrementSpriteCounter() {
		if (spriteCounter == 9 || spriteCounter == 0) direction = -direction;		
		spriteCounter += direction;
	}

	@Override
	public void update() {
		this.x += this.deltaX;
		this.y += this.deltaY;
	}

	@Override
	public void render(Graphics2D g2) {
		g2.drawImage(sprites[spriteCounter], x, y, MoveableShape.ICON_WIDTH, MoveableShape.ICON_HEIGHT, null);
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int getDeltaY() {
		return this.deltaY;
	}

	@Override
	public int getDeltaX() {
		return this.deltaX;
	}

	@Override
	public String getShapeID() {
		return this.shapeID;
	}

	@Override
	public void setShapeID(String shapeID) {
		this.shapeID = shapeID;
	}

	@Override
	public boolean isWrapCompanion() {
		return this.wrapCompanion;
	}

	@Override
	public void setWrapCompanion(boolean wrapCompanion) {
		this.wrapCompanion = wrapCompanion;
	}

	@Override
	public MoveableShape getWrapCompanion(int x, int y) {
		System.out.println("Spawning wrap companion for shape " + this.shapeID + "  at: " + x + ", " + y);
		this.wrapCompanion = true;
		return new Bird(x, y, this.shapeID);
	}

	public double getSlope() {
		return this.slope;
	}
	
	public double getDistance() {
		return this.distance;
	}

	public int getLeftInt() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getRightInt() {
		return 0;
	}

	public void chart() {
		
	}
	
	public int getSpawnX() {
		return this.wrapX;
	}

	public int getSpawnY() {
		return this.wrapY;
	}

	@Override
	public int getDelX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDelY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpawnWrapX() {
		return this.spawnWrapX;
	}

	@Override
	public int getSpawnWrapY() {
		return this.spawnWrapY;
	}
}

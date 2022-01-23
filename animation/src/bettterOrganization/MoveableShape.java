package bettterOrganization;

import java.awt.Graphics2D;

public interface MoveableShape {
	
	public final int ICON_WIDTH = 100;
	public final int ICON_HEIGHT = 100;
	
	public void update();
	public void render(Graphics2D g2);
	public int getX();
	public int getY();
	public int getDeltaY();
	public int getDeltaX();
	public String getShapeID();
	public void setShapeID(String shapeID);
	public boolean isWrapCompanion();
	public void setWrapCompanion(boolean wrapCompanion);
	public MoveableShape getWrapCompanion(int x, int y);
	public String toString();
	
	public double getSlope();
	public int getLeftInt();
	public int getRightInt();
	public double getDistance();
	public int getSpawnX();
	public int getSpawnY();
	public void chart();
	
	public int getDelX();
	public int getDelY();
	public int getSpawnWrapX();
	public int getSpawnWrapY();
	
}

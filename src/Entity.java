import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public interface Entity {
	public void draw(Graphics g);
	public void update(int delta);
	
	public float getX();
	public float getY();
	public float getWidth();
	public float getHeight();
	
	public Rectangle getBounds();
	public boolean deleteMe();
}

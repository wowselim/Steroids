import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class LaserBeam implements Entity {
	private Image img;
	private float x, y;
	private float rotation;
	private boolean deleteMe = false;
	
	public LaserBeam(float x, float y, float rotation) throws SlickException {
		this.img = new Image("\\res\\beam.png");
		this.x = x;
		this.y = y;
		this.rotation = rotation;
	}
	
	public void waste() {
		deleteMe = true;
	}
	
	@Override
	public void draw(Graphics g) {
		img.setRotation(rotation);
		img.draw(x, y);
	}
	
	@Override
	public void update(int delta) {
		x += 5f * Math.sin(Math.toRadians(rotation));
		y -= 5f * Math.cos(Math.toRadians(rotation));
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}
	
	@Override
	public float getWidth() {
		return img.getWidth();
	}
	
	@Override
	public float getHeight() {
		return img.getHeight();
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, img.getWidth(), img.getHeight());
	}

	@Override
	public boolean deleteMe() {
		return deleteMe;
	}
}

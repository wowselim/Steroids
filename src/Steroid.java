import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Steroid implements Entity {
	private Type type;
	private float x, y;
	private float direction;
	private boolean deleteMe = false;

	private enum Type {
		TINY(30), SMALL(40), MIDDLE(50), BIG(70), HUGE(100);

		public int size;

		Type(int size) {
			this.size = size;
		}
	}

	public Steroid(int width, int height) {
		Random random = new Random();
		List<Type> types = Arrays.asList(Type.values());
		type = types.get(random.nextInt(types.size()));
		
		x = random.nextInt((width - type.size));
		y = random.nextInt((height - type.size));
		direction = random.nextInt(360);
	}
	
	public void decreaseSize() {
		switch(type) {
		case HUGE:
			x+= type.size / 2f;
			y+= type.size / 2f;
			type = Type.BIG;
			break;
		case BIG:
			type = Type.MIDDLE;
			break;
		case MIDDLE:
			type = Type.SMALL;
			break;
		case SMALL:
			type = Type.TINY;
			break;
		case TINY:
			deleteMe = true;
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setAntiAlias(true);
		g.setColor(Color.darkGray);
		g.fillOval(x, y, type.size, type.size);
	}

	@Override
	public void update(int delta) {
		x += 0.1f * Math.sin(Math.toRadians(direction));
		y -= 0.1f * Math.cos(Math.toRadians(direction));
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
		return type.size;
	}
	
	@Override
	public float getHeight() {
		return type.size;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, type.size, type.size);
	}

	@Override
	public boolean deleteMe() {
		return deleteMe;
	}
}

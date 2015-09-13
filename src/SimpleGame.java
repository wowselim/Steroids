import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class SimpleGame extends BasicGame {
	private Image plane;
	private float x, y;
	private int width, height;
	private int shootDelay = 300, shootTimer = 0;
	private int steroidSpawnDelay = 3000, spawnTimer = 0;
	private EntityPool<LaserBeam> beamPool;
	private EntityPool<Steroid> steroidPool;
	private int lives = 3;

	public SimpleGame(int width, int height) {
		super("Steroids");
		this.width = width;
		this.height = height;
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		plane = new Image("\\res\\rocket_ship.png");
		x = (width - plane.getWidth()) / 2.0f;
		y = (height - plane.getHeight()) / 2.0f;
		beamPool = new EntityPool<>(width, height, 128, "beamPool");
		steroidPool = new EntityPool<>(width, height, 128, "roidPool");

	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		
		shootTimer += delta;
		spawnTimer += delta;
		
		if (input.isKeyDown(Input.KEY_A)) {
			plane.rotate(-0.2f * delta);

		}

		if (input.isKeyDown(Input.KEY_D)) {
			plane.rotate(0.2f * delta);

		}

		if (input.isKeyDown(Input.KEY_W)) {
			float hip = 0.1f * delta;

			float rotation = plane.getRotation();

			x += hip * Math.sin(Math.toRadians(rotation));
			y -= hip * Math.cos(Math.toRadians(rotation));
		}
		
		beamPool.update(delta);
		steroidPool.update(delta);
		
		steroidPool.parallelStream().forEach(steroid -> {
			if(steroid.getBounds().intersects(new Rectangle(x, y, plane.getWidth(), plane.getHeight()))) {
				steroidPool.remove(steroid);
				lives--;
			}
		});
		
		steroidPool.parallelStream().forEach(steroid -> {
			beamPool.parallelStream().forEach(beam -> {
				if(steroid.getBounds().intersects(beam.getBounds())) {
					beam.waste();
					steroid.decreaseSize();
				}
			});
		});
		
		if(shootTimer >= shootDelay && input.isKeyDown(Input.KEY_SPACE)) {
			shootTimer = 0;
			LaserBeam beam = new LaserBeam(x + (plane.getWidth() / 2f), y, plane.getRotation());
			beamPool.add(beam);
		}
		
		if(spawnTimer >= steroidSpawnDelay) {
			spawnTimer = 0;
			steroidPool.add(new Steroid(width, height));
		}
		
		x = x > width ? 0 : x;
		x = x < 0 ? width : x;
		
		y = y > height ? 0 : y;
		y = y < 0 ? height : y;

	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		for(int i = 10; i < 36 * lives + 10; i+=36)
			Heart.draw(i, 10);
		beamPool.draw(g);
		steroidPool.draw(g);
		plane.draw(x, y);
	}

	public static void main(String args[]) throws SlickException {
		int width = 640, height = 480;
		AppGameContainer app = new AppGameContainer(new SimpleGame(width, height));

		app.setDisplayMode(width, height, false);
		app.setShowFPS(false);
		app.setVSync(true);
		app.start();
	}

}
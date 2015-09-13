import java.util.Objects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Heart {
	private static Image img;
	
	public static void draw(int x, int y) throws SlickException {
		if (Objects.isNull(img))
			img = new Image("\\res\\heart.png");
		img.draw(x, y, 0.5f);
	}
}

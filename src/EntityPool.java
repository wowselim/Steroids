import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.newdawn.slick.Graphics;

public class EntityPool<T extends Entity> {
	private int width, height;
	private List<T> entities;
	private List<T> entitiesToRemove;
	private List<T> entitiesToAdd;
	private String title;

	public EntityPool(int width, int height, int size) {
		this.width = width;
		this.height = height;
		entities = Collections.synchronizedList(new ArrayList<T>(size));
		entitiesToRemove = Collections.synchronizedList(new ArrayList<T>(size));
		entitiesToAdd = Collections.synchronizedList(new ArrayList<T>(size));
	}
	
	public EntityPool(int width, int height, int size, String title) {
		this(width, height, size);
		this.title = title;
	}

	public void add(T entity) {
		entitiesToAdd.add(entity);
	}

	public void remove(T entity) {
		entitiesToRemove.add(entity);
	}

	public void update(int delta) {
		entities.removeAll(entitiesToRemove);
		entitiesToRemove.clear();
		entities.addAll(entitiesToAdd);
		entitiesToAdd.clear();

		entities.parallelStream().forEach(entity -> {
			entity.update(delta);
			if(entity.deleteMe())
				remove(entity);
			else if (entity.getX() + entity.getWidth() < 0 || entity.getX() > width) {
				remove(entity);
			}
			else if (entity.getY() + entity.getHeight() < 0 || entity.getY() > height) {
				remove(entity);
			}
		});
	}
	
	public Stream<T> stream() {
		return entities.stream();
	}
	
	public Stream<T> parallelStream() {
		return entities.parallelStream();
	}

	public void draw(Graphics g) {
		entities.forEach(entity -> entity.draw(g));
	}
	
	public String toString() {
		return "EntityPool " + title + "\n noe: " + entities.size();
	}
}

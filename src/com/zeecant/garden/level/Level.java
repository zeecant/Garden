package com.zeecant.garden.level;

import java.util.ArrayList;
import java.util.List;

import com.zeecant.garden.level.entity.Entity;

public class Level {
	private Ground[] ground;
	private int[] state, seed;
	private int width, height;
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Entity> toKill = new ArrayList<Entity>();
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		this.ground = new Ground[width * height];
		this.state = new int[width * height];
		this.seed = new int[width * height];
		long t = System.currentTimeMillis();
		for (int i = 0; i < ground.length; i++) {
			ground[i] = Ground.BRICK;
			int h = (int) (t + (i % width) * 374761393 + (i / height) * 668265263);
			h = (h^(h >> 13)) * 1274126177;
			seed[i] = h^(h >> 16);
		}
	}
	
	public void update(float dt) {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update(dt);
			if (toKill.size() > 0) {
				for (Entity e : toKill) {
					entities.remove(e);
				}
				toKill.clear();
			}
		}
	}
	
	public Entity getNearest(Vector pos, Class<?> cls, float max) {
		Entity closest = null;
		float d = max * max;
		for (Entity e : entities) {
			if (!cls.isInstance(e)) continue;
			float d2 = pos.sub(e.pos).lengthSquared();
			if (d2 < d) {
				d = d2;
				closest = e;
			}
		}
		return closest;
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public void addEntity(Entity ent) {
		entities.add(ent);
	}
	
	public Ground getGround(int x, int y) {
		return ground[y * width + x];
	}
	
	public void setGround(int x, int y, Ground g) {
		ground[y * width + x] = g;
	}
	
	public int getState(int x, int y) {
		return state[y * width + x];
	}
	
	public void setState(int x, int y, int s) {
		state[y * width + x] = s;
	}
	
	public int getSeed(int x, int y) {
		return seed[y * width + x];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void addToKill(Entity e) {
		toKill.add(e);
	}
}

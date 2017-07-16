package com.zeecant.garden.level.entity;

import com.zeecant.garden.level.Ground;
import com.zeecant.garden.level.Level;
import com.zeecant.garden.level.Vector;

public class EntitySeed extends Entity {
	public int type;
	private long lastSit;
	
	public EntitySeed(Level level, float x, float y, int type) {
		super(level, x, y);
		this.type = type;
		lastSit = System.currentTimeMillis();
	}
	
	public void update(float dt) {
		Vector p = pos;
		super.update(dt);
		if (!p.eq(pos)) {
			lastSit = System.currentTimeMillis();
		}
		if (waitingToPlant()) {
			int rx = (int) pos.x;
			int ry = (int) pos.y;
			if (level.getGround(rx, ry) == Ground.DIRT && level.getState(rx, ry) >= 750) {
				makePlant();
			}
		}
	}
	
	public boolean waitingToPlant() {
		return System.currentTimeMillis() - lastSit >= 3000;
	}
	
	private void makePlant() {
		EntityPlant plant = new EntityPlant(level, pos.x, pos.y, 2);
		level.addEntity(plant);
		kill();
	}
}

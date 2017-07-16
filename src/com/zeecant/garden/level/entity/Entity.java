package com.zeecant.garden.level.entity;

import com.zeecant.garden.level.Level;
import com.zeecant.garden.level.Vector;

public class Entity {
	public Vector pos, acl;
	protected Level level;
	protected boolean alive = true;
	private Vector oldPos;
	
	public Entity(Level level, float x, float y) {
		this.level = level;
		pos = new Vector(x, y);
		oldPos = pos.copy();
		acl = new Vector(0, 0);
	}
	
	private void updatePhysics(float dt) {
		Vector vel = pos.sub(oldPos);
		vel = vel.add(vel.scale(-0.99f));
		pos = pos.add(vel.add(acl).scale(dt * dt));
		if (pos.x < 0) pos.x = 0;
		else if (pos.x >= level.getWidth()) pos.x = level.getWidth() - 1;
		if (pos.y < 0) pos.y = 0;
		else if (pos.y >= level.getHeight()) pos.y = level.getHeight() - 1;
		oldPos = pos.copy();
	}
	
	public void update(float dt) {
		updatePhysics(dt);
	}
	
	public void push(float dx, float dy) {
		pos.x += dx;
		pos.y += dy;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void kill() {
		alive = false;
		level.addToKill(this);
	}
}

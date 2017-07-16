package com.zeecant.garden.level.entity;

import java.util.Random;

import com.zeecant.garden.level.Level;
import com.zeecant.garden.level.Vector;

public class EntityBugSwarm extends Entity {
	private int count;
	private float food;
	private EntityLeaf target;
	private static Random rand = new Random();
	private static final int maxAcl = 3200;
	
	public EntityBugSwarm(Level level, float x, float y, int count) {
		super(level, x, y);
		this.count = count;
		food = 1;
	}
	
	public void update(float dt) {
		super.update(dt);
		food -= dt / 50 * count;
		if (food <= 0) {
			if (count > 1) count--;
			food = 1;
		}
		
		Entity e = level.getNearest(pos, EntityLeaf.class, 32);
		if (e != null) target = (EntityLeaf) e;
		if (target != null) {
			Vector d = target.pos.sub(pos);
			if (d.lengthSquared() > 16) {
				acl = new Vector(d.x * 400, d.y * 400);
			} else {
				acl = new Vector(0, 0);
				food += target.eat(dt / 10f * count);
				if (!target.isAlive()) target = null;
			}
		} else {
			if (pos.x <= 32) acl.x *= -1;
			if (pos.y <= 32) acl.y *= -1;
			if (pos.x >= level.getWidth() - 32) acl.x *= -1;
			if (pos.y >= level.getHeight() - 32) acl.y *= -1;
			acl.x += rand.nextFloat() * 400 - 200;
			acl.y += rand.nextFloat() * 400 - 200;
			if (acl.x > maxAcl) acl.x = maxAcl;
			else if (acl.x < -maxAcl) acl.x = -maxAcl;
			if (acl.y > maxAcl) acl.y = maxAcl;
			else if (acl.y < -maxAcl) acl.y = -maxAcl;
		}
		if (food > 2) {
			food--;
			count++;
		}
	}
	
	public int getSwarmSize() {
		return count;
	}
	
	public EntityLeaf getTarget() {
		return target;
	}
}

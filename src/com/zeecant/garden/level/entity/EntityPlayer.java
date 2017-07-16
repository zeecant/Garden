package com.zeecant.garden.level.entity;

import com.zeecant.garden.input.Controller;
import com.zeecant.garden.input.UseListener;
import com.zeecant.garden.level.Ground;
import com.zeecant.garden.level.Level;
import com.zeecant.garden.level.Vector;

public class EntityPlayer extends Entity implements UseListener {
	private boolean using = false;
	private long lastMove, lastUse, lastStopUse;
	public EntitySeed carriedSeed;
	private Vector spd = new Vector(0, 0);
	
	public EntityPlayer(Level level, float x, float y) {
		super(level, x, y);
		lastUse = System.currentTimeMillis();
		lastStopUse = System.currentTimeMillis() - 1000;
		Controller.addUseListener(this);
	}
	
	@Override
	public void update(float dt) {
		Vector dir = new Vector(0, 0);
		if (Controller.isUp()) dir.y -= 1;
		if (Controller.isDown()) dir.y += 1;
		if (Controller.isLeft()) dir.x -= 1;
		if (Controller.isRight()) dir.x += 1;
		if (dir.lengthSquared() > 0) {
			if (spd.isZero()) {
				if (isWatering()) lastStopUse = System.currentTimeMillis();
			}
			spd = dir.normalize().scale(1.2f);
			lastMove = System.currentTimeMillis();
		} else {
			spd = spd.scale(0.92f);
			if (spd.lengthSquared() < 0.1f) spd = new Vector(0, 0);
		}
		this.push(spd.x, spd.y);
		super.update(dt);
		
		using = Controller.isUsing();
		if (using && carriedSeed == null) {
			Entity e = level.getNearest(pos, EntitySeed.class, 12);
			if (e != null) carriedSeed = (EntitySeed) e;
		} else if (!using && carriedSeed != null) {
			carriedSeed.acl = new Vector(0, 0);
			carriedSeed = null;
		}
		if (carriedSeed != null) {
			Vector d = pos.sub(carriedSeed.pos);
			if (d.lengthSquared() > 64) {
				carriedSeed.acl = new Vector(d.x * 400, d.y * 400);
			} else {
				carriedSeed.acl = new Vector(0, 0);
			}
		}
		if (System.currentTimeMillis() - lastMove < 1000) {
			lastUse = System.currentTimeMillis();
		}
		if (isWatering()) {
			int r = 32;
			for (int y = -r; y <= r; y++) {
				for (int x = -r; x <= r; x++) {
					if (x * x + y * y > r * r) continue;
					int rx = (int) pos.x - x;
					int ry = (int) pos.y - y;
					if (rx < 0 || ry < 0 || rx >= level.getWidth() || ry >= level.getHeight()) continue;
					if (level.getGround(rx, ry) != Ground.DIRT) continue;
					float d = (float) Math.sqrt(x * x + y * y);
					int s = level.getState((int) pos.x - x, (int) pos.y - y);
					level.setState(rx, ry, Math.min(s + (int) ((r - d) / 3), 1000));
				}
			}
		}
	}
	
	public boolean isWatering() {
		return using && System.currentTimeMillis() - lastMove >= 500;
	}
	
	public long getLastUse() {
		return lastUse;
	}
	
	public long getLastStopUse() {
		return lastStopUse;
	}

	@Override
	public void onStartUse() {
		lastUse = System.currentTimeMillis();
	}

	@Override
	public void onStopUse() {
		lastStopUse = spd.isZero() ? System.currentTimeMillis() : lastStopUse;
	}
}

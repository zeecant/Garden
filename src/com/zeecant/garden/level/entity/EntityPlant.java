package com.zeecant.garden.level.entity;

import java.util.Random;

import com.zeecant.garden.level.Level;

public class EntityPlant extends Entity {
	private int height, leaves;
	private static Random rand = new Random();
	private long lastGrow;
	
	public EntityPlant(Level level, float x, float y, int height) {
		super(level, x, y);
		this.height = height;
		lastGrow = System.currentTimeMillis();
	}
	
	public void update(float dt) {
		super.update(dt);
		if (height < 32 && rand.nextFloat() <= 0.01f * (System.currentTimeMillis() - lastGrow) / 1000f) grow();
	}
	
	public int getHeight() {
		return height;
	}
	
	private void addLeaf(int ly) {
		int w = (int) (1 - (float) ly / height);
		int ox = (int) (Math.sin(ly / 7f) * 4f);
		int lx = (int) pos.x;
		if (ly % 2 == 0) lx = lx + 1 - w + ox;
		else lx = lx - 1 + w + ox;
		EntityLeaf l = new EntityLeaf(level, lx, (int) pos.y - ly, this, 1, 1);
		if (ly % 2 == 1) l.setFlipped(true);
		level.addEntity(l);
		leaves++;
	}
	
	public void grow() {
		lastGrow = System.currentTimeMillis();
		height++;
		if ((height - 4) % 3 == 0) addLeaf(height);
	}
	
	public void leafEaten() {
		leaves--;
		if (leaves <= 0) kill();
	}
}

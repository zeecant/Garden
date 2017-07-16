package com.zeecant.garden.level.entity;

import com.zeecant.garden.level.Level;

public class EntityLeaf extends Entity {
	private EntityPlant parent;
	private float origSize, size, nutrition;
	private boolean flipped;
	private long createTime;
	
	public EntityLeaf(Level level, float x, float y, EntityPlant parent, float size, float nutrition) {
		super(level, x, y);
		this.parent = parent;
		this.origSize = size;
		this.size = size;
		this.nutrition = nutrition;
		this.createTime = System.currentTimeMillis();
	}
	
	public void update(float dt) {
		super.update(dt);
	}
	
	/**
	 * Eat an amount of the leaf, returning the nutrition gained.
	 * @param amount
	 * @return nutrition gained
	 */
	public float eat(float amount) {
		amount = Math.min(size, amount);
		size -= amount;
		if (size <= 0) kill();
		return amount * nutrition;
	}
	
	public EntityPlant getParent() {
		return parent;
	}
	
	public float getRemaining() {
		return size / origSize;
	}
	
	public void setFlipped(boolean flipped) {
		this.flipped = flipped;
	}
	
	public boolean isFlipped() {
		return flipped;
	}
	
	public long getCreateTime() {
		return createTime;
	}
	
	@Override
	public void kill() {
		super.kill();
		parent.leafEaten();
	}
}

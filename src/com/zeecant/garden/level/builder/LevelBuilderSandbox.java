package com.zeecant.garden.level.builder;

import com.zeecant.garden.level.Ground;
import com.zeecant.garden.level.Level;
import com.zeecant.garden.level.entity.EntityBugSwarm;
import com.zeecant.garden.level.entity.EntitySeed;

public class LevelBuilderSandbox extends LevelBuilder {
	private final int border = 16;
	
	@Override protected Level build(int width, int height) {
		Level level = new Level(width, height);
		for (int y = border * 2; y < height - border; y++) {
			for (int x = border; x < width - border; x++) {
				level.setGround(x, y, Ground.DIRT);
			}
		}
		
		for (int i = 0; i < 30; i++) {
			EntitySeed seed = new EntitySeed(level, (int) (Math.random() * (width - 64) + 32), (int) (Math.random() * (height - 64) + 32), 0);
			level.addEntity(seed);
		}
		
		EntityBugSwarm swarm = new EntityBugSwarm(level, width / 2, height / 2, 5);
		level.addEntity(swarm);
		
		return level;
	}
}

package com.zeecant.garden.render;

import com.zeecant.garden.level.Level;
import com.zeecant.garden.level.entity.Entity;
import com.zeecant.garden.render.entity.RenderEntity;
import com.zeecant.garden.render.ground.RenderGround;

public class RenderLevel {
	public static void render(DrawImage image, Level level) {
		image.clear();
		for (int y = 0; y < level.getHeight(); y++) {
			for (int x = 0; x < level.getWidth(); x++) {
				RenderGround.render(image, level, level.getGround(x, y), x, y);
			}
		}
		for (Entity e : level.getEntities()) {
			RenderEntity.render(image, e);
		}
	}
}

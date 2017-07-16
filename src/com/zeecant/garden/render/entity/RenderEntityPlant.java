package com.zeecant.garden.render.entity;

import com.zeecant.garden.level.entity.Entity;
import com.zeecant.garden.level.entity.EntityPlant;
import com.zeecant.garden.render.DrawImage;

public class RenderEntityPlant extends RenderEntity {
	@Override
	protected void renderEnt(DrawImage image, Entity ent) {
		int ex = (int) ent.pos.x;
		int ey = (int) ent.pos.y;
		EntityPlant plant = (EntityPlant) ent;
		float darkness = (((plant.getLevel().getSeed(ex, ey)) % 1000) / 4000f + 0.75f);
		int r = (int) (0x4F * darkness) << 16;
		int g = (int) (0x91 * darkness) << 8;
		int b = (int) (0x4F * darkness);
		int col = 0xFF000000 | r | g | b;
		for (int y = 0; y < plant.getHeight(); y++) {
			float h = 1 - (float) y / plant.getHeight();
			int ox = (int) (Math.sin(y / 7f) * 4f);
			for (int x = 1; x <= h * 3; x++) {
				image.drawPixel(ex - x + ox, ey - y, ey, col);
				image.drawPixel(ex + x + ox, ey - y, ey, col);
			}
			image.drawPixel(ex + ox, ey - y, ey, col);
		}
	}
}

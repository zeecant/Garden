package com.zeecant.garden.render.entity;

import com.zeecant.garden.level.entity.Entity;
import com.zeecant.garden.level.entity.EntitySeed;
import com.zeecant.garden.render.DrawImage;

public class RenderEntitySeed extends RenderEntity {
	@Override
	protected void renderEnt(DrawImage image, Entity ent) {
		int ex = (int) ent.pos.x;
		int ey = (int) ent.pos.y;
		for (int y = 0; y < 4; y++) {
			int ox = (y == 0 || y == 3 ? 1 : 0);
			int c = y == 1 ? 0xFFADAA87 : 0xFFAF5C28;
			for (int x = -2 + ox; x < 3 - ox; x++) {
				image.drawPixel(ex + x, ey - y, ey + 1, c);
			}
		}
		float t = image.time();
		switch (((EntitySeed) ent).type) {
			case 0:
				image.drawPixel(ex, ey - 4, ey + 1, 0xFFADAA87);
				int oy = (int) ((Math.cos(t) + 1) / 1.5f);
				if (oy == 0) image.drawPixel(ex, ey - 5, ey + 1, 0xFFADAA87);
				int ox = (int) (Math.sin(t * 2) * 2);
				image.drawPixel(ex + ox, ey - 6 + oy, ey + 1, 0xFFADAA87);
				drawLeaf(image, ex + ox + 1, ey - 7 + oy, ey + 1, false);
				drawLeaf(image, ex + ox - 1, ey - 7 + oy, ey + 1, true);
				break;
		}
	}
	
	private void drawLeaf(DrawImage image, int x, int y, int z, boolean flip) {
		float t = image.time();
		for (int i = 0; i < 3; i++) {
			int oy = (int) ((Math.cos(t * 5f) + 1) / 2 * Math.min(i * 1.1f, 2));
			image.drawPixel(x + (flip ? -i : i), y + oy, z, 0xFF5DAA5D);
		}
	}
}

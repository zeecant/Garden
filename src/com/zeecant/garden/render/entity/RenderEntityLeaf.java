package com.zeecant.garden.render.entity;

import com.zeecant.garden.level.entity.Entity;
import com.zeecant.garden.level.entity.EntityLeaf;
import com.zeecant.garden.render.DrawImage;

public class RenderEntityLeaf extends RenderEntity {
	@Override
	protected void renderEnt(DrawImage image, Entity ent) {
		int ex = (int) ent.pos.x;
		int ey = (int) ent.pos.y;
		EntityLeaf leaf = (EntityLeaf) ent;
		float t = (float) (System.currentTimeMillis() - leaf.getCreateTime()) / 1000f;
		int z = (int) leaf.getParent().pos.y + 1;
		float r = 16 * leaf.getRemaining();
		float d = (float) (4 + Math.sin(t * 2.5f));
		for (int x = 0; x <= r; x++) {
			float max = (float) (Math.pow(4, 1.8) * Math.pow((x / r), 0.8) * Math.pow(1 - (x / r), 0.8)) / Math.max(1.5f, r / 16);
			int oy = (int) (x / d);
			if (leaf.isFlipped()) x = -x;
			int ca = x % 2 == 0 ? 0xFF569E56 : 0xFF5DAA5D;
			int cb = x % 2 == 0 ? 0xFF5DAA5D : 0xFF569E56;
			for (int y = 0; y <= max; y++) {
				image.drawPixel(ex + x, ey - y + oy, z, ca);
				image.drawPixel(ex + x, ey + y + oy, z, cb);
			}
			image.drawPixel(ex + x, ey + oy, z, 0xFF4F914F);
			if (leaf.isFlipped()) x = -x;
		}
	}
}

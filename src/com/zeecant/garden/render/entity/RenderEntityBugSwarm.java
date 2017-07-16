package com.zeecant.garden.render.entity;

import com.zeecant.garden.level.entity.Entity;
import com.zeecant.garden.level.entity.EntityBugSwarm;
import com.zeecant.garden.render.DrawImage;

public class RenderEntityBugSwarm extends RenderEntity {
	@Override
	protected void renderEnt(DrawImage image, Entity ent) {
		float t = image.time();
		int ex = (int) ent.pos.x;
		int ey = (int) ent.pos.y;
		EntityBugSwarm swarm = (EntityBugSwarm) ent;
		
		for (int i = 0; i < swarm.getSwarmSize(); i++) {
			float bx = (float) (Math.sin(t * (6 + i / 4f % 4)) * (4 + i % swarm.getSwarmSize() / 2)) + ex;
			float by = (float) (Math.cos(t * (6 + (i / 4f + 2) % 4)) * (4 + (i + 5) % swarm.getSwarmSize() / 2)) + ey;
			int z = (int) (swarm.getTarget() != null ? swarm.getTarget().getParent().pos.y + 10 : by);
			drawBug(image, (int) bx, (int) by, z);
		}
	}
	
	private void drawBug(DrawImage image, int x, int y, int z) {
		image.drawPixel(x, y, z, 0xFFD35E4A);
		image.drawPixel(x - 1, y, z, 0xFF872E23);
	}
}

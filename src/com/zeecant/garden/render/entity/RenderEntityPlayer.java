package com.zeecant.garden.render.entity;

import com.zeecant.garden.level.entity.Entity;
import com.zeecant.garden.level.entity.EntityPlayer;
import com.zeecant.garden.render.DrawImage;

public class RenderEntityPlayer extends RenderEntity {
	@Override
	protected void renderEnt(DrawImage image, Entity ent) {
		float t = image.time();
		float hvry = (float) (Math.sin(t * 5) * 3);
		float hvrx = (float) (Math.cos(t * 3) * 2);
		EntityPlayer pl = (EntityPlayer) ent;
		drawWisp(image, ent.pos.x + 4 + hvrx, ent.pos.y + 4 + hvry, 12, 1, pl);
		drawHalo(image, ent.pos.x + hvrx, ent.pos.y + hvry, 16, 0x69B527, 255);
		drawWisp(image, ent.pos.x + 4 + hvrx, ent.pos.y + 4 + hvry, 10, 0, pl);
		drawWisp(image, ent.pos.x + 4 + hvrx, ent.pos.y + 4 + hvry, 14, 2, pl);
	}
	
	private void drawWisp(DrawImage image, float ex, float ey, float r, float to, EntityPlayer ent) {
		int state = ent.isWatering() ? 1 : 0;
		float t = image.time() + to;
		float x = ex + (float) Math.cos(t * 2) * r;
		float y = ey + (float) Math.sin(t * 2.45f) * r;
		
		float wx = ex + (float) Math.cos(t * 8) * 10;
		float wy = ey + (float) Math.sin(t * 8) * 10;
		float d = 0;
		if (ent.isWatering()) d = Math.min((System.currentTimeMillis() - ent.getLastUse()) / 500f, 1f);
		else                  d = 1 - Math.min((System.currentTimeMillis() - ent.getLastStopUse()) / 500f, 1f);
		x = wx * d + x * (1 - d);
		y = wy * d + y * (1 - d);
		
		drawHalo(image, x, y, 8, state == 1 ? 0x459BDD : 0x45BC7D, 150);
	}
	
	private void drawHalo(DrawImage image, float ex, float ey, float r, int col, int ba) {
		for (int hy = 0; hy < r; hy++) {
			for (int hx = 0; hx < r; hx++) {
				int dx = (int) (hx - r / 2);
				int dy = (int) (hy - r / 2);
				float d = (float) Math.sqrt(dx * dx + dy * dy);
				int a = ba - (int) (Math.abs(d - r / 4f) * 4 / r * ba);
				int y = (int) ey + hy - 8;
				if (d < r / 2) image.drawPixel((int) ex + hx - 8, y, y + 1, (a << 24) | col);
			}
		}
	}
}

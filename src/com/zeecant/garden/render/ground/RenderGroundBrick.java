package com.zeecant.garden.render.ground;

import com.zeecant.garden.level.Level;
import com.zeecant.garden.render.DrawImage;

public class RenderGroundBrick extends RenderGround {
	@Override protected void render(DrawImage image, Level level, int x, int y) {
		float darkness = (((level.getSeed(x, y)) % 1000) / 16000f + 0.9f);
		int r = (int) (0x99 * darkness) << 16;
		int g = (int) (0x41 * darkness) << 8;
		int b = (int) (0x41 * darkness);
		image.drawPixel(x, y, 0, 0xFF000000 | r | g | b);
	}
}

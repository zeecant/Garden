package com.zeecant.garden.render.ground;

import com.zeecant.garden.level.Level;
import com.zeecant.garden.render.DrawImage;

public class RenderGroundDirt extends RenderGround {
	@Override protected void render(DrawImage image, Level level, int x, int y) {
		float wetness = (float) level.getState(x, y) / 1000;
		float darkness = (((level.getSeed(x, y)) % 1000) / 4000f + 0.75f) - wetness * 0.5f;
		int r = (int) (0x6B * darkness) << 16;
		int g = (int) (0x3D * darkness) << 8;
		int b = (int) (0x21 * darkness);
		image.drawPixel(x, y, 0, 0xFF000000 | r | g | b);
	}
}

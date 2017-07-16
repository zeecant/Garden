package com.zeecant.garden.render.ground;

import java.util.HashMap;
import java.util.Map;

import com.zeecant.garden.level.Ground;
import com.zeecant.garden.level.Level;
import com.zeecant.garden.render.DrawImage;

public abstract class RenderGround {
	public static void render(DrawImage image, Level level, Ground g, int x, int y) {
		mapping.get(g).render(image, level, x, y);
	}
	
	private static Map<Ground, RenderGround> mapping = new HashMap<Ground, RenderGround>();
	
	static {
		mapping.put(Ground.BRICK, new RenderGroundBrick());
		mapping.put(Ground.DIRT, new RenderGroundDirt());
	}
	
	protected abstract void render(DrawImage image, Level level, int x, int y);
}

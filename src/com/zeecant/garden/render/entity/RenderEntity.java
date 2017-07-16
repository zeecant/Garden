package com.zeecant.garden.render.entity;

import java.util.HashMap;
import java.util.Map;

import com.zeecant.garden.level.entity.*;
import com.zeecant.garden.render.DrawImage;

public abstract class RenderEntity {
	public static void render(DrawImage image, Entity e) {
		if (mapping.containsKey(e.getClass())) mapping.get(e.getClass()).renderEnt(image, e);
	}
	
	private static Map<Class<? extends Entity>, RenderEntity> mapping = new HashMap<Class<? extends Entity>, RenderEntity>();
	
	static {
		mapping.put(EntityPlayer.class, new RenderEntityPlayer());
		mapping.put(EntitySeed.class, new RenderEntitySeed());
		mapping.put(EntityPlant.class, new RenderEntityPlant());
		mapping.put(EntityLeaf.class, new RenderEntityLeaf());
		mapping.put(EntityBugSwarm.class, new RenderEntityBugSwarm());
	}
	
	protected abstract void renderEnt(DrawImage image, Entity ent);
}

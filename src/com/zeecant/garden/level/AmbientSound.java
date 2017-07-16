package com.zeecant.garden.level;

import com.zeecant.garden.audio.AudioManager;
import com.zeecant.garden.level.entity.Entity;
import com.zeecant.garden.level.entity.EntityBugSwarm;
import com.zeecant.garden.level.entity.EntityPlayer;

public class AmbientSound {
	private static long lastBugSound;
	private static long lastChimeSound;
	
	public static void checkSound(EntityPlayer player) {
		Level level = player.getLevel();
		long time = System.currentTimeMillis();
		if (time - lastBugSound >= 2000 && Math.random() <= 0.016) {
			Entity e = level.getNearest(player.pos, EntityBugSwarm.class, 128);
			if (e != null) {
				int d = (int) e.pos.sub(player.pos).length();
				AudioManager.getInstance().play("BUGS", (1f - (float) d / 128) / 2f);
				lastBugSound = time;
			}
		}
		if (time - lastChimeSound >= 5000 && Math.random() <= 0.001f) {
			lastChimeSound = time;
			AudioManager.getInstance().play("CHIMES" + (int) (Math.random() * 2 + 1));
		}
	}
}

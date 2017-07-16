package com.zeecant.garden;

import com.zeecant.garden.level.AmbientSound;
import com.zeecant.garden.level.Level;
import com.zeecant.garden.level.builder.LevelBuilder;
import com.zeecant.garden.level.entity.EntityPlayer;
import com.zeecant.garden.render.RenderLevel;
import com.zeecant.garden.window.Window;

public class GameManager {
	public static final GameManager instance = new GameManager();
	private Level currentLevel;
	private EntityPlayer player;
	private int fps;
	
	private GameManager() {}
	
	public void initialize() {
		currentLevel = LevelBuilder.build("SANDBOX", 400, 300);
		player = new EntityPlayer(currentLevel, 100, 100);
		currentLevel.addEntity(player);
	}
	
	public void update(float dt) {
		currentLevel.update(dt);
		AmbientSound.checkSound(player);
	}
	
	public void render() {
		if (currentLevel != null) RenderLevel.render(Window.getInstance().getDrawImage(), currentLevel);
	}
	
	public void setFPS(int fps) {
		this.fps = fps;
	}
	
	public int getFPS() {
		return fps;
	}
	
	public Level getCurrentLevel() {
		return currentLevel;
	}
}

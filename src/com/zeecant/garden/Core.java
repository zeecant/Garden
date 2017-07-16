package com.zeecant.garden;

import com.zeecant.garden.audio.AudioManager;
import com.zeecant.garden.input.Controller;
import com.zeecant.garden.window.Window;
import com.zeecant.garden.window.WindowCloseListener;

public class Core {
	public static void main(String[] args) {
		new Core().run();
	}
	
	private boolean running = true;
	private GameManager manager = GameManager.instance;
	
	public void onClose() {
		running = false;
	}
	
	public void run() {
		try {
			Config.loadNormalConfig();
			Window window = new Window("Game");
			window.onClose(new WindowCloseListener() {
				@Override public void windowClosed() {
					onClose();
				}
			});
			Controller.initialize();
			new AudioManager().initialize();
			manager.initialize();
			long lastTime = System.currentTimeMillis();
			long second = lastTime;
			int frames = 0;
			while (running) {
				if (System.currentTimeMillis() - lastTime >= 1000 / 60) {
					float dt = (float) (System.currentTimeMillis() - lastTime) / 1000f;
					window.flushKeyBuffer();
					manager.update(dt);
					manager.render();
					window.redraw();
					frames++;
					lastTime = System.currentTimeMillis();
				}
				if (System.currentTimeMillis() - second >= 1000) {
					manager.setFPS(frames);
					second += 1000;
					frames = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (AudioManager.getInstance() != null) AudioManager.getInstance().destroy();
		System.exit(0);
	}
}

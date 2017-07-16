package com.zeecant.garden.level.builder;

import com.zeecant.garden.level.Level;

public abstract class LevelBuilder {
	private static final LevelBuilderSandbox sandbox = new LevelBuilderSandbox();
	
	public static Level build(String name, int width, int height) {
		if (name.equals("SANDBOX")) return sandbox.build(width, height);
		return null;
	}
	
	protected abstract Level build(int width, int height);
}

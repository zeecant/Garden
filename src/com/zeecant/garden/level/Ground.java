package com.zeecant.garden.level;

public class Ground {
	private boolean isGrowable = false;
	
	public Ground(Object... props) {
		for (int i = 0; i < props.length; i += 2) {
			if (props[i] instanceof String) {
				switch ((String) props[i]) {
					case "growable":
						isGrowable = (boolean) props[i + 1];
						break;
				}
			}
		}
	}
	
	public boolean isGrowable() {
		return isGrowable;
	}
	
	public static final Ground DIRT = new Ground(
			"growable", true
	);
	public static final Ground BRICK = new Ground();
}

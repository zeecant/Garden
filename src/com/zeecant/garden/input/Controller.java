package com.zeecant.garden.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zeecant.garden.Config;

public class Controller {
	private static boolean using, up, down, left, right;
	private static Map<Integer, Action> keyToAction = new HashMap<Integer, Action>();
	private static Map<Action, Integer> actionToKey = new HashMap<Action, Integer>();
	private static List<UseListener> useListeners = new ArrayList<UseListener>();
	
	private Controller() {}
	
	public static boolean isUsing() {
		return using;
	}
	
	public static boolean isUp() {
		return up;
	}
	
	public static boolean isDown() {
		return down;
	}
	
	public static boolean isLeft() {
		return left;
	}
	
	public static boolean isRight() {
		return right;
	}
	
	public static void keyPress(int key) {
		if (keyToAction.containsKey(key)) {
			switch (keyToAction.get(key)) {
				case UP:
					up = true;
					break;
				case DOWN:
					down = true;
					break;
				case LEFT:
					left = true;
					break;
				case RIGHT:
					right = true;
					break;
				case USE:
					if (!using) for (UseListener l : useListeners) l.onStartUse();
					using = true;
					break;
			}
		}
	}
	
	public static void keyRelease(int key) {
		if (keyToAction.containsKey(key)) {
			switch (keyToAction.get(key)) {
				case UP:
					up = false;
					break;
				case DOWN:
					down = false;
					break;
				case LEFT:
					left = false;
					break;
				case RIGHT:
					right = false;
					break;
				case USE:
					if (using) for (UseListener l : useListeners) l.onStopUse();
					using = false;
					break;
			}
		}
	}
	
	public static void addUseListener(UseListener l) {
		if (useListeners.contains(l)) return;
		useListeners.add(l);
	}
	
	public static void removeUseListener(UseListener l) {
		useListeners.remove(l);
	}
	
	public static void map(Action action, int key) {
		if (actionToKey.containsKey(action)) {
			int oldKey = actionToKey.get(action);
			keyToAction.remove(oldKey);
		} else if (keyToAction.containsKey(key)) {
			Action oldAction = keyToAction.get(key);
			actionToKey.remove(oldAction);
		}
		actionToKey.put(action, key);
		keyToAction.put(key, action);
	}
	
	public static String state() {
		return "[ " +
				(up ? "UP " : "up ") +
				(down ? "DOWN " : "down ") +
				(left ? "LEFT " : "left ") +
				(right ? "RIGHT " : "right ") +
				(using ? "USE " : "use ") +
				"]";
	}
	
	public static void initialize() {
		Config cfg = Config.getGlobalConfig();
		map(Action.UP, cfg.getInt("KEY_UP"));
		map(Action.DOWN, cfg.getInt("KEY_DOWN"));
		map(Action.LEFT, cfg.getInt("KEY_LEFT"));
		map(Action.RIGHT, cfg.getInt("KEY_RIGHT"));
		map(Action.USE, cfg.getInt("KEY_USE"));
	}
}

package com.zeecant.garden;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Config {
	private static Config cfg;
	
	public static Config getGlobalConfig() {
		return cfg;
	}
	
	public static boolean exists(String filename) {
		return new File(filename).exists();
	}
	
	public static void loadNormalConfig() {
		String file = "./cfg";
		if (exists(file)) cfg = new Config(file);
		else {
			Map<String, String> defaultConfig = new HashMap<String, String>();
			defaultConfig.put("KEY_UP", "" + KeyEvent.VK_UP);
			defaultConfig.put("KEY_DOWN", "" + KeyEvent.VK_DOWN);
			defaultConfig.put("KEY_LEFT", "" + KeyEvent.VK_LEFT);
			defaultConfig.put("KEY_RIGHT", "" + KeyEvent.VK_RIGHT);
			defaultConfig.put("KEY_USE", "" + KeyEvent.VK_SPACE);
			cfg = new Config(file, defaultConfig);
			cfg.save();
		}
	}
	
	private String filename;
	private Map<String, String> settings = new HashMap<String, String>();
	
	public Config(String filename) {
		this.filename = filename;
		if (exists(filename)) loadConfig();
	}
	
	public Config(String filename, Map<String, String> settings) {
		this(filename);
		for (String k : settings.keySet()) {
			this.settings.put(k, settings.get(k));
		}
	}
	
	private void loadConfig() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filename));
			
			String line = null;
			while ((line = in.readLine()) != null) {
				if (line.contains(":")) {
					int index = line.indexOf(':');
					settings.put(line.substring(0, index), line.substring(index + 1).trim());
				}
			}
			in.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			PrintWriter out = new PrintWriter(filename, "UTF-8");
			for (String k : settings.keySet()) {
				out.println(k + ": " + settings.get(k));
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public String get(String key) {
		return settings.getOrDefault(key, "DEFAULT");
	}
	
	public int getInt(String key) {
		return Integer.parseInt(settings.getOrDefault(key, "A"));
	}
	
	public void set(String key, String val) {
		boolean doSave = false;
		if (settings.containsKey(key) && !settings.get(key).equals(val)) doSave = true;
		settings.put(key, val);
		if (doSave) save();
	}
}

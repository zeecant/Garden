package com.zeecant.garden.audio;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.jogamp.openal.AL;
import com.jogamp.openal.ALFactory;
import com.jogamp.openal.util.ALut;

public class AudioManager {
	private static AudioManager instance;
	private static AL al;
	private static float[] basePos = { 0f, 0f, 0f };
	private static float[] baseOri = { 0f, 0f, -1f, 0f, 1f, 0f };
	private final int SOURCE_COUNT = 16;
	private final Source[] sources = new Source[SOURCE_COUNT];
	private int nextSource = 0;
	private Map<String, Audio> audio = new HashMap<String, Audio>();
	
	public static AudioManager getInstance() {
		return instance;
	}
	
	public AudioManager() {
		instance = this;
	}
	
	public boolean initialize() {
		al = ALFactory.getAL();
		ALut.alutInit();
		al.alGetError();
		
		int[] sourceIds = new int[SOURCE_COUNT];
		al.alGenSources(sourceIds.length, sourceIds, 0);
		for (int i = 0; i < sources.length; i++) {
			sources[i] = new Source(sourceIds[i]);
		}
		al.alListenerfv(AL.AL_POSITION, basePos, 0);
		al.alListenerfv(AL.AL_VELOCITY, basePos, 0);
		al.alListenerfv(AL.AL_ORIENTATION, baseOri, 0);
		if (al.alGetError() != AL.AL_NO_ERROR) return false;
		
		File dir = new File("assets");
		if (dir.exists() && dir.isDirectory()) {
			for (File f: dir.listFiles()) {
				Audio sound = loadFile(f.getPath());
				String name = f.getName().substring(0, f.getName().indexOf('.')).toUpperCase();
				if (sound != null) audio.put(name, sound);
			}
		}
		
		return true;
	}
	
	public void destroy() {
		if (al != null) {
			for (Audio a : audio.values()) {
				al.alDeleteBuffers(1, new int[] { a.bufId }, 0);
			}
			int[] sourceIds = new int[sources.length];
			for (int i = 0; i < sources.length; i++) sourceIds[i] = sources[i].id;
			al.alDeleteSources(sourceIds.length, sourceIds, 0);
			ALut.alutExit();
		}
	}
	
	public void play(String name, float gain) {
		if (audio.containsKey(name)) {
			Source s = sources[nextSource++];
			nextSource = nextSource % sources.length;
			s.play(audio.get(name), gain);
		}
	}
	
	public void play(String name) {
		play(name, 1f);
	}
	
	private Audio loadFile(String filename) {
		int[] buffer = new int[1];
		al.alGenBuffers(1, buffer, 0);
		if (al.alGetError() != AL.AL_NO_ERROR) return null;
		int id = buffer[0];
		
		int[] format = new int[1];
		ByteBuffer[] data = new ByteBuffer[1];
		int[] size = new int[1];
		int[] freq = new int[1];
		int[] loop = new int[1];
		ALut.alutLoadWAVFile(filename, format, data, size, freq, loop);
		al.alBufferData(id, format[0], data[0], size[0], freq[0]);
		
		if (al.alGetError() != AL.AL_NO_ERROR) return null;
		
		return new Audio(id);
	}
	
	private class Source {
		private final int id;
		
		private Source(int id) {
			this.id = id;
			al.alSourcef(id, AL.AL_PITCH, 1.0f);
			al.alSourcef(id, AL.AL_GAIN, 1.0f);
			al.alSourcefv(id, AL.AL_POSITION, basePos, 0);
			al.alSourcefv(id, AL.AL_VELOCITY, basePos, 0);
		}
		
		public void play(Audio audio, float gain) {
			al.alSourcei(id, AL.AL_BUFFER, audio.bufId);
			al.alSourcef(id, AL.AL_GAIN, gain);
			al.alSourcePlay(id);
		}
	}
}

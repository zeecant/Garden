package com.zeecant.garden.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class DrawImage extends BufferedImage {
	private final Graphics2D g;
	private final int[] pixels;
	private final int[] z;
	private long startTime;
	
	public DrawImage(int width, int height) {
		super(width, height, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) getGraphics();
		pixels = ((DataBufferInt) getRaster().getDataBuffer()).getData();
		z = new int[pixels.length];
		startTime = System.currentTimeMillis();
	}
	
	public void clear() {
		clear(0, 0, 0);
	}
	
	public void clear(int r, int g, int b) {
		int black = (255 << 24) + (r << 16) + (g << 8) + b;
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = black;
			z[i] = Integer.MIN_VALUE;
		}
	}
	
	public int argb(int a, int r, int g, int b) {
		return (a << 24) + (r << 16) + (g << 8) + b;
	}
	
	public float time() {
		return (float) (System.currentTimeMillis() - startTime) / 1000f;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public void drawPixel(int x, int y, int z, int col) {
		if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) return;
		if (z < this.z[x + y * getWidth()]) return;
		
		if (((col >> 24) & 0xFF) != 255) {
			int dst = pixels[x + y * getWidth()];
			int rd = (dst >> 16) & 0xFF;
			int gd = (dst >> 8) & 0xFF;
			int bd = dst & 0xFF;
			int rs = (col >> 16) & 0xFF;
			int gs = (col >> 8) & 0xFF;
			int bs = col & 0xFF;
			
			float a = (float) ((col >> 24) & 0xFF) / 255f;
			int r = (int) (a * (rs - rd) + rd);
			int g = (int) (a * (gs - gd) + gd);
			int b = (int) (a * (bs - bd) + bd);
			col = 0xFF000000 | ((r << 16) | (g << 8) | b);
		}
		pixels[x + y * getWidth()] = col & 0xFFFFFFFF;
		this.z[x + y * getWidth()] = z;
	}
	
	public void drawText(String text, int x, int y, Color col) {
		g.setColor(col);
		g.drawChars(text.toCharArray(), 0, text.length(), x, y);
	}
	
	public void drawImage(Image image, int x, int y, int w, int h, int sx, int sy, int sw, int sh) {
		g.drawImage(image, x, y, x + w, y + h, sx, sy, sx + sw, sy + sh, null);
	}
}

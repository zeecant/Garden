package com.zeecant.garden.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.zeecant.garden.GameManager;
import com.zeecant.garden.level.Level;
import com.zeecant.garden.render.DrawImage;

public class DrawPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private int width, height;
	private DrawImage image;
	
	public DrawPanel(int width, int height) {
		this.width = width;
		this.height = height;
		image = new DrawImage(width, height);
	}
	
	public DrawImage getDrawImage() {
		return image;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		if (GameManager.instance.getCurrentLevel() != null) {
			Level l = GameManager.instance.getCurrentLevel();
			g.drawImage(image, 0, 0, width, height, 0, 0, l.getWidth(), l.getHeight(), null);
		} else {
			g.drawImage(image, 0, 0, width, height, null);
		}
	}
	
	@Override
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		image = new DrawImage(width, height);
	}
	
	@Override public Dimension getMinimumSize() {
		return new Dimension(width, height);
	}
	
	@Override public Dimension getMaximumSize() {
		return new Dimension(width, height);
	}
	
	@Override public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
}

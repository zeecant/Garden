package com.zeecant.garden.window;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import com.zeecant.garden.input.Controller;
import com.zeecant.garden.render.DrawImage;

public class Window implements WindowListener, ComponentListener, KeyListener {
	private class KeyAction {
		public final int key;
		public final boolean press;
		private KeyAction(int key, boolean press) {
			this.key = key;
			this.press = press;
		}
	}
	
	private JFrame jframe;
	private DrawPanel drawPanel;
	private WindowCloseListener closeListener = null;
	private List<KeyAction> keyBuffer = new LinkedList<KeyAction>();
	private static Window instance;
	
	public static Window getInstance() {
		return instance;
	}
	
	public Window(String name) {
		instance = this;
		jframe = new JFrame(name);
		jframe.addWindowListener(this);
		jframe.addComponentListener(this);
		jframe.addKeyListener(this);
		drawPanel = new DrawPanel(800, 600);
		jframe.add(drawPanel);
		jframe.setResizable(false);
		jframe.pack();
		jframe.setVisible(true);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		jframe.setLocation((size.width - jframe.getWidth()) / 2, (size.height - jframe.getHeight()) / 2);
	}
	
	public DrawImage getDrawImage() {
		return drawPanel.getDrawImage();
	}
	
	public void redraw() {
		drawPanel.repaint();
	}
	
	public void onClose(WindowCloseListener l) {
		closeListener = l;
	}
	
	public void flushKeyBuffer() {
		while (keyBuffer.size() > 0) {
			KeyAction a = keyBuffer.remove(0);
			if (a.press) Controller.keyPress(a.key);
			else         Controller.keyRelease(a.key);
		}
	}
	
	@Override public void windowClosing(WindowEvent e) {
		closeListener.windowClosed();
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		drawPanel.setSize(jframe.getContentPane().getWidth(), jframe.getContentPane().getHeight());
		jframe.revalidate();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keyBuffer.add(new KeyAction(e.getKeyCode(), true));
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		keyBuffer.add(new KeyAction(e.getKeyCode(), false));
	}
	
	@Override public void windowClosed(WindowEvent e) {}
	@Override public void windowActivated(WindowEvent e) {}
	@Override public void windowDeactivated(WindowEvent e) {}
	@Override public void windowDeiconified(WindowEvent e) {}
	@Override public void windowIconified(WindowEvent e) {}
	@Override public void windowOpened(WindowEvent e) {}
	
	@Override public void componentHidden(ComponentEvent e) {}
	@Override public void componentMoved(ComponentEvent e) {}
	@Override public void componentShown(ComponentEvent e) {}
	
	@Override public void keyTyped(KeyEvent e) {}
}


package com.vero.swingy.view.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.vero.model.player.Player;
import com.vero.swingy.model.enemies.Enemy;

public class ContentPanel extends JPanel {

	private int width;
	private int height;
	private final int SQUARE_SIZE = 40;
	private Player p;
	private ArrayList<Enemy> e;
	
	
	ContentPanel(int width, int height, Player p, ArrayList<Enemy> e){
		this.setLayout(null);
		this.p = p;
		this.e = e;
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.GREEN);
		this.setDoubleBuffered(true);
	}
	
	public void			paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		int sx = -1;
		int sy = -1;
		
		while (++sy < height / SQUARE_SIZE) {
			sx = -1;
			while (++sx < width / SQUARE_SIZE) {
				g2.setColor(Color.BLACK);
				g2.drawRect(sx*SQUARE_SIZE, sy*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
			}
		}
		for (int i = 0; i < e.size(); i++)
			g2.drawImage(e.get(i).getImage(), e.get(i).getX(), e.get(i).getY(), this);
		g2.drawImage(p.getImage(), p.getX(), p.getY(), this);
	}
	
	public void changeGraphics() {
		repaint();
	}
	
	public void setHeightWidth(int squares) {
		this.width = squares * SQUARE_SIZE;
		this.height = squares * SQUARE_SIZE;
	}
}

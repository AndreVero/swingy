package com.vero.swingy.model.enemies;

import javax.swing.ImageIcon;

public class Troll extends Enemy{

	public Troll(int x, int y) {
		super(x, y);
		
		hp = 100;
		attack = 10;
		level = 2;
		ImageIcon ii = new ImageIcon("classes/troll.png");
		image = ii.getImage();	
	}
}

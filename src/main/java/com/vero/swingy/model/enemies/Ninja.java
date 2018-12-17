package com.vero.swingy.model.enemies;

import javax.swing.ImageIcon;

public class Ninja extends Enemy {
	
	public Ninja(int x, int y) {
		super(x, y);
		
		hp = 50;
		attack = 5;
		level = 1;
		ImageIcon ii = new ImageIcon("classes/ninja.png");
		image = ii.getImage();
	}
}

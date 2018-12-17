package com.vero.swingy.model.enemies;

import javax.swing.ImageIcon;

public class Woman extends Enemy{

	public Woman(int x, int y) {
		super(x, y);
		
		hp = 1000;
		attack = 100;
		level = 3;
		ImageIcon ii = new ImageIcon("classes/woman.png");
		image = ii.getImage();
	}
}

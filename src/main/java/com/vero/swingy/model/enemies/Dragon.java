package com.vero.swingy.model.enemies;

import javax.swing.ImageIcon;

public class Dragon extends Enemy{

	public Dragon(int x, int y) {
		super(x, y);
		
		hp = 150;
		attack = 25;
		level = 3;
		ImageIcon ii = new ImageIcon("classes/dragon.png");
		image = ii.getImage();
	}

}

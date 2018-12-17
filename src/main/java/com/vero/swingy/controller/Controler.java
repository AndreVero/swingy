package com.vero.swingy.controller;

import java.util.ArrayList;
import java.util.Random;

import com.vero.model.player.Player;
import com.vero.swingy.model.enemies.Dragon;
import com.vero.swingy.model.enemies.Enemy;
import com.vero.swingy.model.enemies.Ninja;
import com.vero.swingy.model.enemies.Troll;
import com.vero.swingy.model.enemies.Woman;
import com.vero.swingy.view.window.Window;

public class Controler{
	
	private ArrayList<Window> windows;
	private int map[][];
	
	public 				Controler() {
		windows = new ArrayList<Window>();
	}
	
	public void			addMap(Window w) {
		windows.add(w);
	}
	
	public void 		check(){
		for (int i = 0; i < windows.size(); i++) {
				windows.get(i).setVis();
		}
	}
	
	public void			reCreateMap(ArrayList<Enemy> e, Player p) {
		if (p.getLevel() == 13) {
			for (int i = 0; i < windows.size(); i++)
				if (windows.get(i).getVis() == 1) {
					windows.get(i).win();
				}
		}
		Random r = new Random();
		if (p.getExperience() >= p.getLevel() * 1000 + Math.pow((p.getLevel() - 1), 2) * 450)
			p.setLevel(p.getLevel() + 1);
		int squares = (p.getLevel() - 1) * 5 + 10 - (p.getLevel() % 2);
		p.setX(squares / 2 * 40);
		p.setY(squares / 2 * 40);
		map = new int[squares][squares];
		for (int i = 0; i < e.size(); i++)
			e.remove(i);
		for (int i = 0; i < p.getLevel() * 10; i++)
    	{
			if (i == 0)
				e.add(new Woman(r.nextInt(squares) * 40 ,r.nextInt(squares) * 40));
			else if (i % 5 == 0)
    			e.add(new Dragon(r.nextInt(squares) * 40 ,r.nextInt(squares) * 40));
    		else if (i % 3 == 0)
    			e.add(new Troll(r.nextInt(squares) * 40 ,r.nextInt(squares) * 40));
    		else
    			e.add(new Ninja(r.nextInt(squares) * 40 ,r.nextInt(squares) * 40));
    		while (((e.get(i).getX() == p.getX()) && (e.get(i).getY() == p.getY())) || (map[e.get(i).getY() / 40][e.get(i).getX() / 40] == 1))
    		{
    			e.get(i).setX(r.nextInt(squares) * 40);
    			e.get(i).setY(r.nextInt(squares) * 40);
    		}
    		map[e.get(i).getY() / 40][e.get(i).getX() / 40] = 1;
    	}
		for (int i = 0; i < windows.size(); i++)
			windows.get(i).setSize(squares, squares);
	}
}

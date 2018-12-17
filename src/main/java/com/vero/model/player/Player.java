package com.vero.model.player;

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Player extends JComponent{

	private int dx;
	private int dy;
	private int x;
	private int y;
	private int attack;
	private int level;
	private int defence;
	private int hp;
	private int hpStart;
	private int experience;
	private int prevX;
	private int prevY;
	
	@NotNull
	@Size(min = 1, max = 100)
	private String name;
	private String type;
	private final int SQUARE_SIZE = 40;
	private String src;
	
	private Image image;
	
	 public Player clone(Player p) {
		 	return (new Player(p.getName(), p.getHp(), p.getDefence(), p.getAttack(), p.getSrc(), p.getType(), p.getLevel(), p.getExperience()));
		  }
	 
	public Player(String name, int hp, int def, int attack, String src, String type, int level, int exp) {
		super();
		this.attack = attack;
		this.level = level;
		this.defence = def;
		this.hp = hp;
		this.hpStart = hp;
		this.name = name;
		this.experience = exp;
		this.type = type;
		this.src = src;
		this.x = (((level - 1) * 5 + 10 - (level % 2)) / 2) * SQUARE_SIZE;
		this.y = (((level - 1) * 5 + 10 - (level % 2)) / 2) * SQUARE_SIZE;
		ImageIcon ii = new ImageIcon(src);
		image = ii.getImage();
		
		if (type.equals("Orc")) {
			this.hpStart = 100;
			}
		else if (type.equals("Elf")) {
			this.hpStart = 130;
		}
		else {
			this.hpStart = 100;
		}
	}
	
	public String getSrc() {
		return src;
	}

	@Override
	public String toString() {
		return "Player [attack=" + attack + ", level=" + (level - 1) + ", defence=" + defence + ", hp=" + hp + ", experience="
				+ experience + ", name=" + name + ", type=" + type + "]";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getHpStart() {
		return hpStart;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void move() {
		prevX = x;
		prevY = y;
		x += dx;
		y += dy;
		dx = 0;
		dy = 0;
}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	

	public int getPrevX() {
		return prevX;
	}

	public void setPrevX(int prevX) {
		this.prevX = prevX;
	}

	public void setPrevY(int prevY) {
		this.prevY = prevY;
	}

	public int getPrevY() {
		return prevY;
	}

	public void setY(int y) {
		this.y = y;
	}
	public Image getImage() {
		return image;
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT)
			dx = -40;
		if (key == KeyEvent.VK_RIGHT)
			dx = 40;
		if (key == KeyEvent.VK_UP) 
			dy = -40;
		if (key == KeyEvent.VK_DOWN)
			dy = 40;	
	}
}

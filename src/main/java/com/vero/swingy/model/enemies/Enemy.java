package com.vero.swingy.model.enemies;

import java.awt.Image;

public class Enemy {
	protected int x;
	protected int y;
	protected int attack;
	protected int hp;
	protected Image image;
	protected int level;
	
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}
	
	public Image getImage() {
		return image;
	}
	
	public int		getHP() {
		return hp;
	}

	public RandomArtifact	dropDie(int level) {
		return Artifact.getArtifact().getBonuses(level);
	}

	public int getDamage() {
		return attack;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
}

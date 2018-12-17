package com.vero.swingy.view.window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.vero.database.DataBase;
import com.vero.model.player.Player;
import com.vero.swingy.controller.Controler;
import com.vero.swingy.model.enemies.Enemy;
import com.vero.swingy.model.enemies.RandomArtifact;
import com.vero.swingy.model.enemies.TypeOfArtifact;


public class WindowConsole implements Window {

	private int visible;
	private Controler ob;
	private Player p;
	private ArrayList<Enemy> e;
	private BufferedReader bs;
	private int width;
	private int height;
	private final int SQUARE_SIZE = 40;
	private Random r;
	
	public 		WindowConsole(Player p, ArrayList<Enemy> e, Controler ob, boolean window) {
		r = new Random();
		this.p = p;
		this.e = e;
		this.ob = ob;
		ob.addMap(this);
		width = ((p.getLevel() - 1) * 5 + 10 - (p.getLevel() % 2)) * 40;
		height = ((p.getLevel() - 1) * 5 + 10 - (p.getLevel() % 2)) * 40;
		bs = new BufferedReader(new InputStreamReader(System.in));
		if (!window)
			visible = 1;
		try {
			play();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	public int getVis() {
		return visible;
	}

	public void setVis() {
		if (visible == 0)
			visible = 1;
		else
			visible = 0;
	}

	private void play() throws SQLException {
		String s;
			try {
				while ((s = bs.readLine()) != null) {
					if (visible == 1) {
					p.setPrevX(p.getX());
					p.setPrevY(p.getY());
					if (s.equals("1")) {
						if (p.getY() - 40 < 0)
							ob.reCreateMap(e, p);
						else
							p.setY(p.getY() - 40);
					}
					else if (s.equals("3")) {
						if (p.getX() - 40 < 0)
							ob.reCreateMap(e, p);
						else
							p.setX(p.getX() - 40);
					}
					else if (s.equals("4")) {
						if (p.getX() + 40 >= width)
							ob.reCreateMap(e, p);
						else
							p.setX(p.getX() + 40);
					}
					else if (s.equals("2")) {
						if (p.getY() + 40 >= height)
							ob.reCreateMap(e, p);
						else
							p.setY(p.getY() + 40);
					}
					else if (s.equals("GUI"))
						ob.check();
					else if (s.equals("Stat"))
						printStat();
					else if (s.equals("Exit")) {
						System.out.println("Save your hero ?\nYes(1)/No(0)");
						String answer = bs.readLine();
						if (answer.equals("1")) {
							boolean check = true;
							List<Player> ps = DataBase.getDb().getPlayers();
							for (int i = 0; i < ps.size(); i++) {
								if (p.getName().equals(ps.get(i).getName())) {
									DataBase.getDb().updateUser(p.getDefence(), p.getAttack(), p.getHp(), p.getExperience(), p.getLevel(), p.getName());
									check = false;
									break;
								}
							}
							if (check) {
								DataBase.getDb().addUser(p.getName(), p.getType(), p.getSrc(), p.getDefence(), p.getAttack(), p.getHp(), p.getLevel(), p.getExperience());
							
							}
							DataBase.getDb().getCon().close();
							System.exit(0);
						}
						DataBase.getDb().getCon().close();
						System.exit(0);
					}
					printMap();
					checkCollision();
				}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	private void checkCollision() {
		for (int i = 0; i < e.size(); i++) {
				if (e.get(i).getX() == p.getX() && e.get(i).getY() == p.getY()) {
					if (checkBattle())
						battle(i);
					else {
						p.setX(p.getPrevX());
						p.setY(p.getPrevY());
						printMap();
					}
				}
		}
	}
	
	private boolean checkBattle() {
		System.out.println("Do you want to fight ? Yes(1)/No(0)");
		try {
			String str = bs.readLine();
			if (str.equals("1"))
				return true;
			else
				return (r.nextBoolean());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	private void printMap() {
		System.out.println("North = 1\nSouth = 2\nWest = 3\nEast = 4\nSee Player Stat = Stat\nSee GUI interface = GUI\nExit = Exit");
		for (int i = 0; i < height / SQUARE_SIZE; i++)
		{
			for (int j = 0; j < width / SQUARE_SIZE; j++) {
				if (j == p.getX() / SQUARE_SIZE && i == p.getY() / SQUARE_SIZE)
					System.out.print("X");
				else {
					int check = 0;
					for (int k = 0; k < e.size(); k++)
						if (e.get(k).getX() / SQUARE_SIZE == j && e.get(k).getY() / SQUARE_SIZE == i) {
							System.out.print(e.get(k).getLevel());
							check = 1;
							break;
						}
					if (check == 0)
						System.out.print(".");
				}
			}
			System.out.println("");
		}
	}
	
	private void printStat() {
		System.out.printf("Name : %s\nAttack : %d\nDefence : %d\nLevel : %d\nHP : %d\nExperience : %d\n", p.getName(), p.getAttack(), p.getDefence(),
				p.getLevel() - 1, p.getHp(), p.getExperience());
	}
	
	public void regObserver(Controler ob) {
		this.ob = ob;
	}

	public void setSize(int width, int height) {
		this.width = width * SQUARE_SIZE;
		this.height = height * SQUARE_SIZE;
	}
	
	private void 	battle(int i) {
		int exp = e.get(i).getHp();
		while (p.getHp() > 0 && e.get(i).getHp() > 0) {
			if (r.nextInt(100) > p.getDefence()) {
				System.out.println(p.getName() + " take " + e.get(i).getDamage() + " damage!");
				p.setHp(p.getHp() - (e.get(i).getDamage()));
			}
			else 
				System.out.println("Enemy miss!");
			System.out.println("You give " + p.getAttack() +" damage to monster!");
			e.get(i).setHp(e.get(i).getHp() - p.getAttack());
		}
		if (e.get(i).getHp() <= 0 && p.getHp() > 0)
		{
			String res = "0";
			if (r.nextBoolean())
			{
				System.out.println("YOU WIN!!!!!!!");
				RandomArtifact rArt = e.get(i).dropDie(e.get(i).getLevel());
				try {
					System.out.println("Do you want this : " + rArt.getName() + "\nYes(1)\nNo(0)");
					res = bs.readLine();
				} catch (IOException e1) {
					e1.printStackTrace();
				}	
				if (res.equals("1")) {
					if (rArt.getType() == TypeOfArtifact.ATTACK && p.getAttack() < 250)
						p.setAttack(p.getAttack() + rArt.getAdding());
					else if (rArt.getType() == TypeOfArtifact.DEFENCE && p.getDefence() < 85)
						p.setDefence(p.getDefence() + rArt.getAdding());
					else if (rArt.getType() == TypeOfArtifact.HP)
						p.setHp(p.getHpStart() + rArt.getAdding());
				}
			}
			p.setExperience(p.getExperience() + exp);
			e.remove(e.get(i));
			printMap();
		}
		else {
			System.out.println("You lose!:_((((((");
			try {
				DataBase.getDb().getCon().close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		}
	}

	public void win() {
		System.out.println("You Win this Game!!!");
		try {
			DataBase.getDb().getCon().close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.exit(0);
	}
}

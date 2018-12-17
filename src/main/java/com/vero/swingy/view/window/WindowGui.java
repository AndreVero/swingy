package com.vero.swingy.view.window;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.vero.database.DataBase;
import com.vero.model.player.Player;
import com.vero.swingy.controller.Controler;
import com.vero.swingy.model.enemies.Enemy;
import com.vero.swingy.model.enemies.RandomArtifact;
import com.vero.swingy.model.enemies.TypeOfArtifact;

public class WindowGui extends JFrame implements Window {
	private JLabel attackStat;
	private JLabel levelStat;
	private JLabel defenceStat;
	private JLabel hpStat;
	private JLabel experienceStat;
	private JLabel nameStat;
	private JLabel pictureOfBattle;
	private Player pl;
	private ArrayList<Enemy> ene;
	private ContentPanel cP;
	private JScrollPane sP;
	private Controler ob;
	private int visible;
	private int SQUARE_SIZE = 40;
	private JPanel contentPane;
	private Random r;
	private JTextArea display;
	private JScrollPane scroll;
	
	public WindowGui(Player p, ArrayList<Enemy> e, boolean window){
		this.pl = p;
		this.ene = e;
		
		r = new Random();
		
		this.ob = null;
		if (window)
			this.visible = 1;
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				if (JOptionPane.showConfirmDialog(null, 
			            "Save your hero?", "Goodbye!", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					try {
					boolean check = true;
					List<Player> ps = DataBase.getDb().getPlayers();
						for (int i = 0; i < ps.size(); i++) {
							if (pl.getName().equals(ps.get(i).getName())) {
								DataBase.getDb().updateUser(pl.getDefence(), pl.getAttack(), pl.getHp(), pl.getExperience(), pl.getLevel(), pl.getName());
								check = false;
								break;
							}
						}
						if (check)
							DataBase.getDb().addUser(pl.getName(), pl.getType(), pl.getSrc(), pl.getDefence(), pl.getAttack(), pl.getHp(), pl.getLevel(), pl.getExperience());
						DataBase.getDb().getCon().close();
						System.exit(0);
					} catch (SQLException ex) {
						System.out.println("Something wrong in SQL_Connection");
						System.exit(1);
					}
			        }
				try {
					DataBase.getDb().getCon().close();
				} catch (SQLException e) {
					System.out.println("Something wrong in SQL_Connection");
					System.exit(1);
				}
				System.exit(0);
			}
		});
		this.setLayout(null);
		this.setTitle("SWINGY");
		int squares = (p.getLevel() - 1) * 5 + 10 - (p.getLevel() % 2);
		cP = new ContentPanel(squares * SQUARE_SIZE, squares * SQUARE_SIZE, p, e);
		sP = new JScrollPane(cP);
		sP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sP.setBounds(300, 0, 380, 380);
		contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(680, 380));
        contentPane.add(sP);
    	
    	nameStat = new JLabel("Name of your hero : " + p.getName());
    	levelStat = new JLabel("Level : " + (p.getLevel() - 1));
    	defenceStat = new JLabel("Defence : " + p.getDefence());
    	hpStat = new JLabel("HP : " + p.getHp());
    	experienceStat = new JLabel("Exp : " + p.getExperience());
    	attackStat = new JLabel("Attack : " + p.getAttack());
    	
    	pictureOfBattle = new JLabel();
    	pictureOfBattle.setBounds(10, 250, 250, 150);
    	pictureOfBattle.setVisible(true);
    	contentPane.add(pictureOfBattle);
    	
    	nameStat.setBounds(10, 10, 200, 20);
    	nameStat.setVisible(true);
        contentPane.add(nameStat);
        
        display = new JTextArea();
        display.setEditable(false); 
        scroll = new JScrollPane(display);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(10, 150, 250, 100);
        contentPane.add(scroll);
        
        levelStat.setBounds(10, 30, 100, 20);
    	levelStat.setVisible(true);
        contentPane.add(levelStat);
        
        defenceStat.setBounds(10, 50, 100, 20);
    	defenceStat.setVisible(true);
        contentPane.add(defenceStat);
        
        hpStat.setBounds(10, 70, 100, 20);
    	hpStat.setVisible(true);
        contentPane.add(hpStat);
        
        experienceStat.setBounds(10, 90, 100, 20);
    	experienceStat.setVisible(true);
        contentPane.add(experienceStat);
        
        attackStat.setBounds(10, 110, 100, 20);
    	attackStat.setVisible(true);
        contentPane.add(attackStat);
        
        addKeyListener(new TAdapter());
        setFocusable(true);
        
		this.setContentPane(contentPane);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		if (window)
			this.setVisible(true);
	}
	
	private void checkColision() {
		if (visible == 1)
		{
		for (int i = 0; i < ene.size(); i++) {
			if (ene.get(i).getX() == pl.getX() && ene.get(i).getY() == pl.getY()) {	
				if (checkBattle())
					battle(i);
				else {
					pl.setX(pl.getPrevX());
					pl.setY(pl.getPrevY());
				}
			}
		}
	}
}
	
	private boolean checkBattle() {
		String[] but = {"Yes", "No"};
		int oP = JOptionPane.showOptionDialog(null, "Do you want to fight ?", "FIGHT", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE ,
				new ImageIcon("classes/swords.png"), but, but[0]);
		if (oP == 0)
			return true;
		else
			return (r.nextBoolean());
	}

	private class TAdapter extends KeyAdapter{
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_0){
				ob.check();
			}
			int squares = (pl.getLevel() - 1) * 5 + 10 - (pl.getLevel() % 2);
			if ((e.getKeyCode() == KeyEvent.VK_LEFT && pl.getX() - 40 < 0) || (e.getKeyCode() == KeyEvent.VK_RIGHT && pl.getX() + 40 > (squares * SQUARE_SIZE) - 40)
			|| (e.getKeyCode() == KeyEvent.VK_UP && pl.getY() - 40 < 0) || (e.getKeyCode() == KeyEvent.VK_DOWN && pl.getY() + 40 > (squares * SQUARE_SIZE) - 40))
				ob.reCreateMap(ene, pl);
			else
				pl.keyReleased(e);
			levelStat.setText("Level : " + (pl.getLevel() - 1));
			defenceStat.setText("Defence : " + pl.getDefence());
			hpStat.setText("HP : " + pl.getHp());
			experienceStat.setText("Exp : " + pl.getExperience());
			attackStat.setText("Attack : " + pl.getAttack());
			pl.move();
			checkColision();
			cP.changeGraphics();
		}
	}

	public void setVis() {
		if (this.visible == 1)
			this.visible = 0;
		else
			this.visible = 1;
		if (this.visible == 0) {
			this.setVisible(false);
			this.setFocusable(false);
		}
		else
		{
			this.setVisible(true);
			cP.setVisible(true);
			this.setFocusable(true);
		}
	}

	public int getVis() {
		return visible;
	}

	public void regObserver(Controler ob) {
		this.ob = ob;
		ob.addMap(this);	
	}
	
	public void setSize(int width, int height) {
		cP.setHeightWidth(width);
		cP.setPreferredSize(new Dimension(width * SQUARE_SIZE, height * SQUARE_SIZE));
		cP.revalidate();
		sP.getViewport().setViewPosition(new Point(pl.getX(), pl.getY()));
		contentPane.repaint();
	}
	
	private void 	battle(int i) {
		display.setText("");
		int exp = ene.get(i).getHp();
		while (pl.getHp() > 0 && ene.get(i).getHp() > 0) {
			if (r.nextInt(100) > pl.getDefence()) {
				display.setText(display.getText() + "\n" + pl.getName() + " take " + ene.get(i).getDamage() + " damage!");
				pl.setHp(pl.getHp() - (ene.get(i).getDamage()));
			}
			else 
				display.setText(display.getText() + "\nEnemy miss!");
			display.setText(display.getText() + "\n" + "You give " + pl.getAttack() +" damage to monster!");
			ene.get(i).setHp(ene.get(i).getHp() - pl.getAttack());
		}
		if (ene.get(i).getHp() <= 0 && pl.getHp() > 0)
		{
			display.setText(display.getText() + "\n" + "YOU WIN!!!!!!!");
			if (r.nextBoolean())
			{
				RandomArtifact rArt = ene.get(i).dropDie(ene.get(i).getLevel());
				String[] but = {"Yes", "No"};
				int oP = JOptionPane.showOptionDialog(null, rArt.getName(), "YOU WIN", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE , rArt.getIi(), but, but[0]);
				if (oP == 0) {
					if (rArt.getType() == TypeOfArtifact.ATTACK && pl.getAttack() < 250)
						pl.setAttack(pl.getAttack() + rArt.getAdding());
					else if (rArt.getType() == TypeOfArtifact.DEFENCE && pl.getDefence() < 85)
						pl.setDefence(pl.getDefence() + rArt.getAdding());
					else if (rArt.getType() == TypeOfArtifact.HP)
						pl.setHp(pl.getHpStart() + rArt.getAdding());
				}
			}
			ImageIcon ii = new ImageIcon("classes/battle" + r.nextInt(4) +".jpg");
			pictureOfBattle.setIcon(ii);
			pictureOfBattle.setVisible(true);
			pl.setExperience(pl.getExperience() + exp);
			ene.remove(ene.get(i));
		}
		else {
			JOptionPane.showMessageDialog(null, "Your lose!");
			try {
				DataBase.getDb().getCon().close();
				System.exit(0);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void win() {
		JOptionPane.showMessageDialog(null, "You win this game!!!");
		try {
			DataBase.getDb().getCon().close();
			System.exit(0);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}


package com.vero.swingy.model.creationOfPlayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.vero.database.DataBase;
import com.vero.model.player.Player;

public class Creation {

	public Player chooseHeroGui() throws Exception {
		String[] but = {"Choose previos created hero", "Create a new hero"};
		int oP = JOptionPane.showOptionDialog(null, "Make Your choise", "SWINGY", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE ,
				new ImageIcon("classes/swords.png"), but, but[0]);
		if (oP == 0) {
			List<Player> ps = DataBase.getDb().getPlayers();
			ChooseWindow cw = new ChooseWindow(ps);
			Player res = null;
			while (res == null) {
				Thread.sleep(100);
				res = cw.getResult();
				if (res != null)
				{
					cw.setVisible(false);
					cw.dispose();
				}
			}
			return (res);
		}
		else if (oP == 1) {
			ChooseWindow cw = new ChooseWindow();
			Player res = null;
			while (res == null) {
				Thread.sleep(100);
				res = cw.getResult();
				if (res != null)
				{
					cw.setVisible(false);
					cw.dispose();
				}
			}
			return (res);
		}
		else
			return (null);
	}
	
	public Player chooseHeroConsole() throws Exception {
		List<Player> ps = DataBase.getDb().getPlayers();
		BufferedReader bs = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Choose previous created hero (1)\nCreate new hero (2)");
		String s = bs.readLine();
		if (s.equals("1"))
		{
			int i = -1;
			int res = -1;
			while (++i < ps.size())
				System.out.println((i + 1) + " :  " +  ps.get(i).getName());
			System.out.println("Choose your hero!");
			String line;
			while (!(line = bs.readLine()).matches("[0-9]+") || (res = Integer.parseInt(line)) > i || (res = Integer.parseInt(line)) <= 0)
				System.out.println("Not correct hero!");
			return (ps.get(res - 1));
		}
		else if (s.equals("2")) {
			String name;
			String type;
			
			System.out.println("Choose name of your hero!");
			name = bs.readLine();
			for (int i = 0; i < ps.size(); i++) {
				if (ps.get(i).getName().equals(name)) {
					System.out.println("This name is not for you! Choose another.");
					return null;
				}
			}
			System.out.println("Choose the type of hero!\nOrc (more powerfull)\nElf (more hp)\nKnight (more defence)");
			type = bs.readLine();
			while (!type.equals("Orc") && !type.equals("Elf") && !type.equals("Knight")) {
				System.out.println("Not correct choose of type");
				System.out.println("Choose the type of hero!\nOrc (more powerfull)\nElf (more hp)\nKnight (more defence)");
				type = bs.readLine();
			}
			int def = 5;
			int attack = 15;
			int hp = 100;
			String src = "";
			if (s.equals("Orc")) {
				attack += 15;
				src = "classes/orc.png";
			}
			else if (s.equals("Elf")) {
				hp += 30;
				src = "classes/elf.png";
			}
			else {
				def += 20;
				src = "classes/knight.png";
			}
			return (new Player(name, hp, def, attack, src, type, 1, 0));
		}
		else
			return (null);
	}
}

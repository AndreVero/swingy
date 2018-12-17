package com.vero.swingy.model.creationOfPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;

import com.vero.database.DataBase;
import com.vero.model.player.Player;

public class ChooseWindow extends JFrame {
	
	private JButton choose;
	private JComboBox players;
	private JLabel name;
	private JLabel def;
	private JLabel attack;
	private JLabel image;
	private JLabel hp;
	private JLabel type;
	private JLabel exp;
	private JLabel level;
	private JTextField tf;
	private List<Player> p;
	private Player result;
	private ValidatorFactory factory ;
    private Validator validator ;
	
	ChooseWindow(List<Player> pl) {
		this.p = pl;
		tf = null;
		
		initChooseWindow();
		
		choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < p.size(); i++) {
					if (p.get(i).getName().equals((String)players.getSelectedItem())) {
						result = p.get(i).clone(p.get(i));
						break;
					}
				}
			}
		});
	}

	ChooseWindow() {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		factory = Validation.buildDefaultValidatorFactory();
	    validator = factory.getValidator();
	     
		p = new ArrayList<Player>();
		this.p.add(new Player("Orc", 100, 10, 30, "classes/orc.png" , "Orc", 1, 0));
		this.p.add(new Player("Elf", 130, 10, 10,  "classes/elf.png" , "Elf", 1, 0));
		this.p.add(new Player("Knight", 100, 25, 15,  "classes/knight.png" , "Knight", 1, 0));
		
		tf = new JTextField();
		tf.setBounds(125, 350, 100, 25);
		tf.setVisible(true);
		this.add(tf);
		
		JLabel setName = new JLabel("Set name");
		setName.setBounds(50, 350, 100, 25);
		setName.setVisible(true);
		this.add(setName);
		
		initChooseWindow();
		choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < p.size(); i++) {
					if (p.get(i).getName().equals((String)players.getSelectedItem())) {
						try {
							List<Player> lst = DataBase.getDb().getPlayers();
							for (int j = 0; j < lst.size(); j++) {
								if (lst.get(j).getName().equals(tf.getText())) {
									result = null;
									JOptionPane.showMessageDialog(null, "This name not for your. Choose another!");
									return ;
								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						result = p.get(i).clone(p.get(i));
						break;
					}
				}
			}
		});
	}
	
	private void initChooseWindow() {
		result = null;
		
		ArrayList<String> names = new ArrayList<String>();
		for (int i = 0; i < p.size(); i++) {
			names.add(p.get(i).getName());
		}
		String arr[] = new String[names.size()];
		arr = names.toArray(arr);

		choose = new JButton("Ok");
		choose.setBounds(125, 400, 100, 25);
		
		players = new JComboBox(arr);
		players.setSelectedItem(0);
		players.setBounds(150, 50, 100, 25);
		players.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Player res = null;
				String strName = (String)players.getSelectedItem();
				for (int i = 0; i < p.size(); i++) {
					if (p.get(i).getName() == strName) {
						res = p.get(i);
						break;
					}
				}
				name.setText("Name : " + res.getName());
				def.setText("Defence : " + Integer.toString(res.getDefence()));
				attack.setText(" Attack : " + Integer.toString(res.getAttack()));
				type.setText("Type : " + res.getType());
				exp.setText("Experience : " + Integer.toString(res.getExperience()));
				hp.setText("HP : " + Integer.toString(res.getHp()));
				level.setText("Level : " + (res.getLevel() - 1));
				ImageIcon ii = new ImageIcon("classes/" + res.getType() + "Icon.png");
				image.setIcon(ii);
			}
		});
		players.setVisible(true);
		
		def = new JLabel("Defence : " + Integer.toString(p.get(0).getDefence()));
		def.setBounds(20, 200, 100, 40);
		def.setVisible(true);
		
		level = new JLabel("Level : " + Integer.toString(p.get(0).getLevel() - 1));
		level.setBounds(20, 0, 100, 40);
		level.setVisible(true);
		
		attack = new JLabel(" Attack : " + Integer.toString(p.get(0).getAttack()));
		attack.setBounds(20, 150, 100, 40);
		attack.setVisible(true);
		
		name = new JLabel("Name : " + p.get(0).getName());
		name.setBounds(20, 50, 100, 40);
		name.setVisible(true);
		
		hp = new JLabel("HP : " + Integer.toString(p.get(0).getHp()));
		hp.setBounds(20, 250, 100, 40);
		hp.setVisible(true);
		
		type = new JLabel("Type : " + p.get(0).getType());
		type.setBounds(20, 100, 100, 40);
		type.setVisible(true);
		
		exp = new JLabel("Experience : " + Integer.toString(p.get(0).getExperience()));
		exp.setBounds(20, 300, 200, 50);
		exp.setVisible(true);
		
		ImageIcon ii = new ImageIcon("classes/" + p.get(0).getType() + "Icon.png");
		image = new JLabel();
		image.setIcon(ii);
		image.setBounds(150, 100, 200, 200);
		image.setVisible(true);
		
		this.setLayout(null);
		this.setTitle("Choose Hero");
		this.setSize(350, 500);
		this.add(players);
		this.add(choose);
		this.add(hp);
		this.add(def);
		this.add(type);
		this.add(exp);
		this.add(name);
		this.add(attack);
		this.add(level);
		this.add(image);
		
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public Player getResult() {
		if (result != null && tf != null) {
		 result.setName(tf.getText());
		 Set<ConstraintViolation<Player>> constraintViolations = validator.validate(result);
	     if (constraintViolations.size() > 0) {
	    	 for (ConstraintViolation<Player> violation : constraintViolations) {
	    		 JOptionPane.showMessageDialog(null, "Need Name!");
	    		 result =  null;
	            }
	        } else {
	        	JOptionPane.showMessageDialog(null, "Hero Created!");
	        }
		}
		return result;
	}
}

package com.vero.swingy;

import java.sql.SQLException;
import java.util.ArrayList;

import com.vero.database.DataBase;
import com.vero.model.player.Player;
import com.vero.swingy.controller.Controler;
import com.vero.swingy.model.creationOfPlayer.Creation;
import com.vero.swingy.model.enemies.Enemy;
import com.vero.swingy.view.window.WindowConsole;
import com.vero.swingy.view.window.WindowGui;


public class GameStart
{
    public static void main( String[] args )
    {
    	boolean gui = false;
    	
    	if (args.length == 1) {
    		if (args[0].equals("gui"))
    			gui = true;
    		else if (args[0].equals("console")) 
    			gui = false;
    		else {
    			System.out.println("usage : swingy [gui] [console]");
        		System.exit(0);
    		}
    	} else {
    		System.out.println("usage : swingy [gui] [console]");
    		System.exit(0);
    	}
    	Player p = null;
    	Creation cc = new Creation();
    	while (p == null) {
    		try {
    			if (gui) {
    				p = cc.chooseHeroGui();
    			}
    			else
    				p = cc.chooseHeroConsole();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	if (!gui)
    		System.out.println("Press enter!");
    	Controler ob = new Controler();
    	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    	ob.reCreateMap(enemies, p);
    	WindowGui w = new WindowGui(p, enemies, gui);
        w.regObserver(ob);
    	WindowConsole wc = new WindowConsole(p, enemies, ob, gui);
    	
    	try {
			DataBase.getDb().getCon().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
}

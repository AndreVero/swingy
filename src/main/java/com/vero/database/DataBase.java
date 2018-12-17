package com.vero.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vero.model.player.Player;

public class DataBase {
	
	private String createDataBase = "CREATE TABLE IF NOT EXISTS heroes (\n"
            + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
            + "	name text NOT NULL,\n"
            + " type text NOT NULL,\n"
            + " src text NOT NULL,\n"
            + " def integer,\n"
            + " attack integer,\n"
            + " hp integer,\n"
            + "	exp integer,\n"
            + " level integer\n"
            + ");" ;
	
	private String insertDataBase = "INSERT INTO heroes values($next_id, ?, ?, ?, ?, ?, ?, ?, ?);";
	private String getDataBase = "SELECT * FROM heroes;";
	private Connection con;
	public static DataBase db;
	
	
	public static DataBase getDb() {
		if (db == null)
			db = new DataBase();
		return db;
	}

	private DataBase() {
    	try {
    	    Class.forName("org.sqlite.JDBC");
    	    con = DriverManager.getConnection("jdbc:sqlite:sample.db");
    	    Statement st = con.createStatement();
    	    ResultSet res = st.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='heroes';");
    	    if (!res.next())
    	    {
    	    	st.execute(createDataBase);
    	    	initBD();
    	    }
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    	
	}
	
	public Connection getCon() {
		return con;
	}

	private void initBD() {
		try {
			addUser("Sir Roderic", "Knight", "classes/knight.png", 25, 15, 100, 1, 0);
			addUser("Dominator", "Orc", "classes/orc.png", 10, 30, 100, 1, 0);
			addUser("Legolas", "Elf", "classes/elf.png", 10, 10, 130, 1, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void addUser(String name, String type, String src, int def, int attack, int hp, int exp, int level) throws SQLException {
    	PreparedStatement prep = con.prepareStatement(insertDataBase);
    	prep.setString(2,  name);
    	prep.setString(3, type);
    	prep.setString(4, src);
    	prep.setInt(5, def);
    	prep.setInt(6, attack);
    	prep.setInt(7, hp);
    	prep.setInt(8, exp);
    	prep.setInt(9, level);
    	prep.execute();
    	prep.close();
    }
	
    public void updateUser(int def, int attack, int hp, int level, int exp, String name){
    	String upd = "UPDATE heroes SET def = ?"
    			+ ", attack = ?"
    			+  ", hp = ?"
    			+  ", exp = ?"
    			+   ", level = ?"
    			+ " WHERE name = ?;";
    	try {
			PreparedStatement prep = con.prepareStatement(upd);
			prep.setInt(1, def);
			prep.setInt(2, attack);
			prep.setInt(3, hp);
			prep.setInt(4, exp);
			prep.setInt(5, level);
			prep.setString(6, name);
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public List<Player> getPlayers() throws SQLException {
    	List<Player> list = new ArrayList<Player>();
    	Statement state = con.createStatement();
    	ResultSet res = state.executeQuery(getDataBase);
    	while (res.next())
    		list.add(new Player(res.getString(2), res.getInt(7), res.getInt(5), res.getInt(6), res.getString(4), res.getString(3), res.getInt(8), res.getInt(9)));
    	state.close();
    	return list;
    }

}

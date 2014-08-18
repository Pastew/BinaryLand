package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DbManager {

	private Connection conn;
	private Statement stat;
	   
	public DbManager(String dbPath){ // jdbc:sqlite:players.db
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
 
        try {
            conn = DriverManager.getConnection(dbPath);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        } 
	}
	
	public void createPlayerTable(){
        String createPlayers = "CREATE TABLE IF NOT EXISTS players "
        		+ "(player_id INTEGER PRIMARY KEY AUTOINCREMENT, "
        		+ "login varchar(255), "
        		+ "pass varchar(255), "
        		+ "highScore INTEGER)";
        createTable(createPlayers);
	}
	
	public void createTable(String sqlString){
        try {
            stat.execute(sqlString);
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
            e.printStackTrace();
        }
	}

	public boolean insertPlayer(String login, String pass) {
	       try {
	           PreparedStatement prepStmt = conn.prepareStatement(
	                   "insert into players values (NULL, ?, ?, ?);");
	           prepStmt.setString(1, login);
	           prepStmt.setString(2, pass);
	           prepStmt.setInt(3, 0);
	           prepStmt.execute();
	       } catch (SQLException e) {
	           System.err.println("Blad przy wstawianiu playera");
	           e.printStackTrace();
	           return false;
	       }
	       
	       return true;
	   }
	
	public void setHighScore(String name, int score){
		String statement = "UPDATE players SET highScore="+score+" WHERE login='" + name + "'" ;
		try {
			stat.execute(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getHighScore(String name){
		String statement = "SELECT highScore FROM players WHERE login='" + name + "'" ;
		ResultSet res;
		int score=0;
		
		try {
			res = stat.executeQuery(statement);
			if(res.next())
				score=res.getInt("highScore");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return score;
	}
	
	public List<PlayerData> selectHighScoresList() {
	       List<PlayerData> players = new ArrayList<PlayerData>();
	       try {
	           ResultSet result = stat.executeQuery(""
	           		+ "SELECT player_id, login, highScore "
	           		+ "FROM players "
	           		+ "WHERE highScore>0 "
	           		+ "ORDER BY highScore DESC"); 
	           int id, highScore;
	           String login, pass;
	           
	           while(result.next()) {
	               id = result.getInt("player_id");
	               login = result.getString("login");
	               pass = "";
	               highScore = result.getInt("highScore");
	               
	               players.add(new PlayerData(id, login, pass, highScore));
	           }
	       } catch (SQLException e) {
	           e.printStackTrace();
	           return null;
	       }
	       return players;
	   }
	
	public List<PlayerData> selectPlayers() {
	       List<PlayerData> players = new LinkedList<PlayerData>();
	       try {
	           ResultSet result = stat.executeQuery("SELECT * FROM players"); // SELECT 1 FROM players WHERE login=? AND pass=? limit 1
	           int id;
	           String login, pass;
	           
	           while(result.next()) {
	               id = result.getInt("player_id");
	               login = result.getString("login");
	               pass = result.getString("pass");
	               
	               players.add(new PlayerData(id, login, pass));
	           }
	       } catch (SQLException e) {
	           e.printStackTrace();
	           return null;
	       }
	       return players;
	  }
	
	public void closeConnection() {
	       try {
	           conn.close();
	       } catch (SQLException e) {
	           System.err.println("Problem z zamknieciem polaczenia");
	           e.printStackTrace();
	       }
	   }
}

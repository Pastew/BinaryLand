package socket.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbManager {

	private Connection conn;
	private Statement stat;
	   
	public DbManager(String dbPath){ // jdbc:sqlite:serverPlayers.db
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
 
        try {
            conn = DriverManager.getConnection(dbPath);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("SERWER ERROR: Problem z otwarciem polaczenia z baza danych");
            e.printStackTrace();
        } 
	}
	
	public void createPlayerTable(){
        String createPlayers = "CREATE TABLE IF NOT EXISTS players "
        		+ "(player_id INTEGER PRIMARY KEY AUTOINCREMENT, "
        		+ "login varchar(255), "        		
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

	public boolean insertPlayer(String login, int highScore) {
	       try {
	           PreparedStatement prepStmt = conn.prepareStatement(
	                   "insert into players values (NULL, ?, ?);");
	           prepStmt.setString(1, login);
	           prepStmt.setInt(2, highScore);
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
	
	public List<PlayerData> selectPlayers() {
	       List<PlayerData> players = new ArrayList<PlayerData>();
	       try {
	           ResultSet result = stat.executeQuery("SELECT * FROM players"); 
	           int id, highScore;
	           String login;
	           
	           while(result.next()) {
	               id = result.getInt("player_id");
	               login = result.getString("login");
	               highScore = result.getInt("highScore");
	               
	               players.add(new PlayerData(id, login, highScore));
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

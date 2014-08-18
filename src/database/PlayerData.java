package database;

public class PlayerData {
	String login, pass;
	int id;
	int highScore;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setHighScore(int highScore){
		this.highScore = highScore;
	}
	
	public int getHighScore(){
		return highScore;
	}
	
	public String getPass() {
		return pass;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public PlayerData(int id, String login, String pass) {
		this.id = id;
		this.login = login;
		this.pass = pass;
		this.highScore=0;
	}
	public PlayerData(int id, String login, String pass, int highScore) {
		this(id, login, pass);
		this.highScore = highScore;
	}
	
	@Override
    public String toString() {
        return "["+id+"] - "+login+" - "+pass;
    }
	
}

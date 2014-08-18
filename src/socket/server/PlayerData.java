package socket.server;

public class PlayerData {
	String login;
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
	

	public void setLogin(String login) {
		this.login = login;
	}


	public PlayerData(int id, String login, int highScore) {
		this.id = id;
		this.login = login;
		this.highScore=highScore;
	}
	
	
	@Override
    public String toString() {
        return "["+id+"] - "+login+" : " + highScore;
    }
	
}

package dataModel;

public class PlayerData {
	String login, pass;
	int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
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
		super();
		this.id = id;
		this.login = login;
		this.pass = pass;
	}
	
	@Override
    public String toString() {
        return "["+id+"] - "+login+" - "+pass;
    }
	
}

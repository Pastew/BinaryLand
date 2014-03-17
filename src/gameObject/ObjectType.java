package gameObject;

public enum ObjectType {
	FLOOR("res/floor.png"), 
	STONE("res/stone.png"), 
	BRICK("res/brick.png"), 
	EMPTY_STONE("res/empty_stone.png"), 
	GOAL("res/goal.png"),
	
	PLAYER1("res/player1.png"), 
	PLAYER2("res/player2.png");
	
	public final String location;
	ObjectType(String location){
		this.location = location;
	}
}

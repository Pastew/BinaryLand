package socket.protocol;

import java.io.Serializable;

public class HighScoreFrame extends Frame implements Serializable
{
	private static final long serialVersionUID = 1324780234702143701L;
	
	private String username;
	private int score;
	
	public HighScoreFrame()
	{
		super.setType( FrameType.HighScore );
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public void setUsername( String username )
	{
		this.username = username;
	}
	
	public int getScore()
	{
		return this.score;
	}
	
	public void setScore( int score )
	{
		this.score = score;
	}
	
	@Override
	public String toString() {
		return username + " | score= " + score;
	}

	
}

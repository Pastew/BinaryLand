package socket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import socket.Utils;
import socket.protocol.Frame;
import socket.protocol.HighScoreFrame;
import socket.protocol.LoginFrame;
import socket.protocol.MessageFrame;
import socket.protocol.ResultFrame;

public class ExampleServer extends BasicServer
{
	private class Logic
	{
		DbManager dbManager;
		private ObjectInputStream inputStream = null;
		private ObjectOutputStream outputStream = null;
		
		public Logic( Socket clientSocket ) throws IOException
		{
			try
			{
				this.outputStream = Utils.createOutputStream( clientSocket );
				this.inputStream = Utils.createInputStream( clientSocket );
			}
			catch ( IOException ex )
			{
				if ( this.outputStream != null )
					Utils.close( this.outputStream );
				
				throw ex;
			}			
		}
		
		public void close()
		{
			Utils.close( this.inputStream );
			Utils.close( this.outputStream );
		}
		
		public Frame readFrame() throws ClassNotFoundException, IOException
		{
			return (Frame) this.inputStream.readObject();
		}
		
		public void interpretLoginFrame( LoginFrame loginFrame ) throws IOException
		{
			ResultFrame resultFrame = new ResultFrame();
			
			if ( "root".equals( loginFrame.getUsername() ) && "root".equals( loginFrame.getPassword() ) )
			{
				System.out.println( "Login success." );
				resultFrame.setCorrect( true );
			}
			else
			{
				System.out.println( "Login failed." );
				resultFrame.setCorrect( false );
			}
			
			this.outputStream.writeObject( resultFrame );
		}
		
		public void interpretMessageFrame( MessageFrame messageFrame )
		{
			System.out.println( "Message from client: " + messageFrame.getMessage() );
		}
		
		public void interpretHighScoreFrame( HighScoreFrame highScoreFrame )
		{			
			String name = highScoreFrame.getUsername();
			int score = highScoreFrame.getScore();
			System.out.println( "[Serwer] Dostalem nowy highscore do zapisu: " 
			+ "\nLogin: " + name 
			+ "\nWynik: " + score );
			
			addHighScore( name, score );
		}
		
		public void sendMessage( String message ) throws IOException
		{
			MessageFrame messageFrame = new MessageFrame();
			messageFrame.setMessage( message );
			
			this.outputStream.writeObject( messageFrame );
		}
	
		public void addHighScore(String name, int score){		
			
			
			dbManager = new DbManager("jdbc:sqlite:serverPlayers.db");
			dbManager.createPlayerTable();
			
			ArrayList<PlayerData>players = (ArrayList<PlayerData>) dbManager.selectPlayers();
			PlayerData player = null;
			//szukam czy jest ju¿ taki player w bazie
			for(PlayerData el : players)
				if(el.getLogin().equals(name))
					player = el;
			//jeœli jest, to mu nadpisuje wynik (jeœli jest wiêkszy ni¿ stary)
			if(player!=null)
				dbManager.setHighScore(player.getLogin(), score);
			else	//jeœli nie to tworze playera w bazie z nowym wynikiem
				dbManager.insertPlayer(name, score);
			
			//TODO wywal to u do³u
			players = (ArrayList<PlayerData>) dbManager.selectPlayers();
			
			StringBuilder data = new StringBuilder();
	  	   int i = 0;
	  	   for(PlayerData el : players){
	  		   ++i;
	  		   data.append(i + ". " + el.getLogin() + "\t" + el.getHighScore() + "\n");
	  	   }
	  	   System.out.println(data.toString());		  	
		}
	}
	
	@Override
	protected void performClient( Socket clientSocket ) // dla kazdego kolejnego podlaczonego klienta wykonywana jest ta metoda
	{
		Logic logic = null; // na potrzeby kazdego klienta wymagane jest utworzenie osobnej logiki

		try
		{
			logic = new Logic( clientSocket );
		}
		catch ( IOException ex )
		{
			System.out.println( "Streams creation error." );
			Utils.close( clientSocket );
			
			return;
		}
		
		finish :
		try
		{
			while ( this.isLooped() ) // jesli serwer jest uruchomiony to podtrzymujemy wszystkie petle
			{
				// if ( this.inputStream.available() > 0 )
				{
					Frame frame = logic.readFrame(); // odbieramy ramke od klienta
					
					switch ( frame.getType() )
					{
						case Login:
							logic.interpretLoginFrame( (LoginFrame) frame );
							
							break;
						
						case Message:
							logic.interpretMessageFrame( (MessageFrame) frame );
							//Utils.sleep( 5000 );
							//logic.sendMessage( "I am server." );
							
							break;
							
						case HighScore:
							logic.interpretHighScoreFrame( (HighScoreFrame) frame );	
							logic.sendMessage("ok");
							break;
						
						case Disconnect:
						default:
							break finish;
					}
				}
				 //else
				 Utils.sleep( 100 );
			}
		}
		catch ( IOException | ClassNotFoundException ex )
		{
			System.out.println( "Communication error." );
		}
		
		logic.close();
		Utils.close( clientSocket );
	}
}

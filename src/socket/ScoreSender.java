package socket;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import socket.client.ExampleClient;

public class ScoreSender {
	public static boolean sendScoreToServer(String name, int score){
		
		ExampleClient client = new ExampleClient();
		
		try {
			InetAddress address = Inet4Address.getByName( "127.0.0.1" );
			int port = 8070;
			client.connect( address, port );
			System.out.println( "Client connected." );
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		//System.out.println(name + ": " +score);
		//while(!client.isConnected()){}
		Utils.sleep(100);
		client.sendHighScoreFrame(name, score);
		//ArrayList<HighScoreFrame> list = client.getListOfHighscores();
		client.disconnect();
		return true;
	}
}

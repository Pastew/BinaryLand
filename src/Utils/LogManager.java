package Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogManager {
	public static void addLog(String name, int score){
		BufferedWriter bufferedWriter=null;
		
		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			File log = new File("log.txt");
			
			if(!log.exists()){
				log.createNewFile();
			}
			
			FileWriter logWriter = new FileWriter(log.getName(), true);
			bufferedWriter = new BufferedWriter(logWriter);
			
			String data = "\n"+ dateFormat.format(date) + " : " + name + "\t" + score;
			bufferedWriter.write(data);
			bufferedWriter.close();	
		}
		catch(IOException e){
			e.printStackTrace();
		}finally{
			if(bufferedWriter!=null)
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}

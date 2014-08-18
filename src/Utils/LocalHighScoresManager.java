package Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class LocalHighScoresManager {
	public static void addHighScore(String name, int score){
		Map<String, Integer> oldScores = new HashMap<String, Integer>();		
		BufferedWriter bufferedWriter = null;
		FileWriter logWriter=null;
		
		try {
			File file = new File("highScores.txt");
			if(!file.exists()){
				file.createNewFile();
			}
			
			Scanner scanner = new Scanner(file);
						
			String line;
			
			while (scanner.hasNext()) {
				line = scanner.nextLine();
				String[] data = line.split(" ");
				System.out.println("LINE: " + line);
				oldScores.put(data[0], Integer.parseInt(data[1]));
			}
			scanner.close();
			oldScores.put(name, score);
	
			//zapisywanie nowych wynikow
			
			PrintWriter writer = new PrintWriter(file);
			writer.print("");
			writer.close();
			
			logWriter = new FileWriter(file.getName(), true);
			bufferedWriter = new BufferedWriter(logWriter);
			//Set<Entry<String, Integer>> set = oldScores.entrySet();
		    //Iterator<Entry<String, Integer>> iterator = set.iterator();
			
		    Map<String, Integer> newScores = sortByComparator(oldScores);
		    
		    int i = 0;
			for (Map.Entry<String, Integer> entry : newScores.entrySet()) {
				String data = entry.getKey() + " " + entry.getValue()+"\n";
				bufferedWriter.write(data);				
				i++;
				if (i>10) return;
			}
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			bufferedWriter.close();
			logWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<String, Integer> sortByComparator(Map unsortMap) {
		 
		List list = new LinkedList(unsortMap.entrySet());
 
		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
                                       .compareTo(((Map.Entry) (o2)).getValue());
			}
		});
 
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}

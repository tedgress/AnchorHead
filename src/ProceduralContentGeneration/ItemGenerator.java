package ProceduralContentGeneration;

import ifgameengine.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.StringTokenizer;

public class ItemGenerator {

	
	public static LinkedList<Pair<String,String>> items = new LinkedList<Pair<String,String>>();
	private static Random rng = null; 
	
	public static Pair<String,String> getRandomItem(int seed){
		if (ItemGenerator.items.isEmpty())
			ItemGenerator.loadItemsFromFile("listOfMagicalItems.txt");
		
		if (ItemGenerator.rng == null)
			ItemGenerator.rng = new Random(seed);
		
		return ItemGenerator.items.get(ItemGenerator.rng.nextInt(ItemGenerator.items.size() - 1));
		
		
	}
	
	public static void loadItemsFromFile(String fileName){
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String str;
			while ((str = in.readLine()) != null ) {
				
				
				if (!str.isEmpty()) {
					StringTokenizer st = new StringTokenizer(str, "-");

					Pair<String, String> item = new Pair<String, String>(
							st.nextToken(), st.nextToken());

					ItemGenerator.items.add(item);
				}
			}
			in.close();
		} catch (IOException e) {
		}

	}

}

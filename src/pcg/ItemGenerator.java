package pcg;

import ifgameengine.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.StringTokenizer;

public class ItemGenerator {

	
	public static LinkedList<Pair<String,String>> items = new LinkedList<Pair<String,String>>();

	
	public static Pair<String,String> getRandomItem(){
		if (ItemGenerator.items.isEmpty())
			ItemGenerator.loadItemsFromFile("listOfMagicalItems.txt");

		
		return ItemGenerator.items.get(PCG_Random.get_rng().nextInt(ItemGenerator.items.size() - 1));
		
		
	}
	
	public static void loadItemsFromFile(String fileName){
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String str;
			while ((str = in.readLine()) != null ) {
				
				
				if (!str.isEmpty()) {
					StringTokenizer st = new StringTokenizer(str, "-");

					Pair<String, String> item = new Pair<String, String>(
							st.nextToken().trim(), st.nextToken());

					ItemGenerator.items.add(item);
				}
			}
			in.close();
		} catch (IOException e) {
		}

	}

}

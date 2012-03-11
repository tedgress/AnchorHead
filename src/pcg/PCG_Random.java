package pcg;

import java.util.Random;

public class PCG_Random {

	private static Random rng = null; 
	
	public static void seed_rng(long seed){
		if (null == rng)
			PCG_Random.rng = new Random(seed);
		
		
	}
	
	public static Random get_rng(){
		if ( PCG_Random.rng == null )
			throw new NullPointerException("RNG not seeded");
		
		return PCG_Random.rng;
	}
	
}

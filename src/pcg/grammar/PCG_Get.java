package pcg.grammar;

import java.util.LinkedList;

import pcg.Quest;

public class PCG_Get implements
		Abstract_PCG_Symbol {
	

	@Override
	public void expand() {
		
		//Currently the grammar is pretty simple, the get expands into:
		// <get> := <goto> gather
		// where:
		// <goto> := explore
		// So it always results in <get> := explore gather
		
		
		PCG_Explore explore = new PCG_Explore();
		Quest.getQuest().addToQuest(explore);
		
		PCG_Gather gather = new PCG_Gather();
		Quest.getQuest().addToQuest(gather);

	}

}

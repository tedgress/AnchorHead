package pcg.quests;

import ifgameengine.IFAction;

import java.util.LinkedList;
import java.util.List;

import pcg.Quest;
import pcg.QuestTemplate;

public class FindLostItem extends QuestTemplate {
	
	protected LinkedList<String> template = new LinkedList<String>();

	public FindLostItem(LinkedList<String> l) {
		super(l);
		this.template.addAll(l);

		
		// TODO Auto-generated constructor stub
	}
	
	public LinkedList<IFAction> buildQuestMessage(){
		//Build effects
		String story = "Someone in Anchorhead is missing a magical item, please help them find it!";

		
		IFAction announce = new IFAction(Quest.getQuest().getQuestGiver().getID(), "talk", "player",
				"reply", story);
		
		LinkedList<IFAction> effects = new LinkedList<IFAction>();
		
		effects.add(announce);
		
		return effects;
	}

	public static FindLostItem create(){
		LinkedList<String> l = new LinkedList<String>();
		
		l.add("get");
		l.add("give");
		
		FindLostItem quest = new FindLostItem(l);
		
		quest.startQuest();
		
		return quest;
		
		
		
	}
}

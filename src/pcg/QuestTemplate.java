package pcg;

import ifgameengine.IFAction;
import ifgameengine.IFHint;
import ifgameengine.IFRoom;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import pcg.grammar.Abstract_PCG_Symbol;
import pcg.grammar.PCG_Explore;
import pcg.grammar.PCG_Gather;
import pcg.grammar.PCG_Get;
import pcg.grammar.PCG_Give;
import pcg.grammar.PCG_Intro;
import storyengine.IFCondition;
import storyengine.IFConditionAction;
import storyengine.IFConditionAnd;
import storyengine.IFConditionLocation;
import storyengine.IFConditionNot;
import storyengine.IFConditionPlotpoint;
import storyengine.IFPlotPoint;

public abstract class QuestTemplate {
	
	protected LinkedList<String> expression = new LinkedList<String>();
	
	protected static HashMap<String, Class<?>> dictionary = new HashMap<String, Class<?>>();
	
	protected String story = new String();
	
	public QuestTemplate(List<String> l){
		this.expression.addAll(l);
		

	}
	
	public String getStory() {return this.story;}
	
	protected abstract LinkedList<IFAction> buildQuestMessage();

	public void startQuest(){
		
		LinkedList<IFAction> effects = this.buildQuestMessage();
		
		IFAction intro = new IFAction(Quest.getQuest().getQuestGiver().getID(), "talk", "player",
				"reply", "Stranger, can you help me with a quest?!");
	
		
		IFPlotPoint pp = this.createQuestIntro();
		
		Quest.getQuest().addToQuest( new PCG_Intro( pp ));
		
		//IFPlotPoint pp = this.launchQuest(effects);
		
		//Quest.getQuest().addToQuest( new PCG_Intro( pp ));
		
		this.expandQuest();
		
		
		for (String s : Quest.getQuest().getQuestStory() )
	    	story = story + s;
		
		IFAction announce = new IFAction(Quest.getQuest().getQuestGiver().getID(), "talk", "player",
				"reply", story);
		
		effects.addFirst(intro);
		effects.add(announce);
		
		pp.replaceEffects(effects);

	}
	private IFPlotPoint createQuestIntro(){
		//Create the plot point
		String ppName = "intro_" + Quest.getNextSequenceNumber();
		String plotName = Quest.getQuest().getQuestName();
		
		//Build the preconditions
		IFPlotPoint previousPP = Quest.getQuest().getLastPlotpoint();
		
		IFRoom currentLoc = Quest.getQuest().getGame_state().contains("player").getRoom();
		
		IFConditionLocation locCondition = new IFConditionLocation("player", currentLoc.getID());
		
		IFConditionPlotpoint thisPP = new IFConditionPlotpoint(ppName);
		IFConditionNot notThisPP = new IFConditionNot(thisPP);
		
		
		LinkedList<IFCondition> preconditions = new LinkedList<IFCondition>();
		
		preconditions.add(notThisPP);
		preconditions.add(locCondition);
		
		if (null != previousPP) {
			preconditions.add(new IFConditionPlotpoint(previousPP.getName()));
		}
		
		IFConditionAnd precondition = new IFConditionAnd(preconditions);
		
		//Build the trigger
		
		IFCondition trigger = locCondition;
		
		//Build effects
		IFAction announce = new IFAction(Quest.getQuest().getQuestGiver().getID(), "talk", "player",
				"reply", "Stranger, can you help me with a quest?!");
		
		
		LinkedList<IFAction> effects = new LinkedList<IFAction>();
		
		effects.addFirst(announce);
		
		
		//Build hint
		String hintText = "Hmmm, maybe you should go back and see what quest the " + Quest.getQuest().getQuestGiver() + " was talking about.";
		
		IFHint hint = new IFHint(hintText, 3);
		
		LinkedList<IFHint> hints = new LinkedList<IFHint>();
		
		hints.add(hint);
		
		//Put it all together
		
		return new IFPlotPoint(ppName, plotName, precondition, trigger, effects, null);
		
	}
	
	
	protected void expandQuest() {

		// Ideally this would use reflection to find the name and map to the
		// class automatically...
		dictionary.put("get", PCG_Get.class);
		dictionary.put("goto", PCG_Explore.class);
		dictionary.put("give", PCG_Give.class);
		dictionary.put("gather", PCG_Gather.class);
		
		Abstract_PCG_Symbol symbol = null;

		for (String s : this.expression) {
			if (s.equals("get"))
				symbol = new PCG_Get();
			else if (s.equals("goto"))
				symbol = new PCG_Explore();
			else if (s.equals("give"))
				symbol = new PCG_Give();
			else if (s.equals("gather"))
				symbol = new PCG_Gather();
			else
				throw new NullPointerException();
			
			symbol.expand();
				
				
			
		}

	}

}

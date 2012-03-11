package pcg.grammar;

import java.util.LinkedList;

import storyengine.IFPlotPoint;

import gui.SimpleNLP;
import ifgameengine.IFAction;
import ifgameengine.IFHint;
import ifgameengine.IFItemObject;
import ifgameengine.IFObject;
import ifgameengine.IFRoom;
import ifgameengine.Pair;
import pcg.ItemGenerator;
import pcg.PCG_Random;
import pcg.Quest;
import storyengine.IFCondition;
import storyengine.IFConditionAction;
import storyengine.IFConditionAnd;
import storyengine.IFConditionLocation;
import storyengine.IFConditionNot;
import storyengine.IFConditionPlotpoint;
import storyengine.IFPlotPoint;

public class PCG_Gather implements PCG_TerminalSymbol {

	IFPlotPoint pp = null;
	IFItemObject item = null;
	
	public PCG_Gather(){
		
		//Create the plot point
		String ppName = "gather_" + Quest.getNextSequenceNumber();
		String plotName = Quest.getQuest().getQuestName();
		
		//Build the preconditions
		IFPlotPoint previousPP = Quest.getQuest().getLastPlotpoint();
		
		PCG_Explore lastExploreSymbol = (PCG_Explore) Quest.getQuest().getLastTermOfType(PCG_Explore.class);
		
		IFRoom room = null;
		
		if (null != lastExploreSymbol)
			room = lastExploreSymbol.getLocation();
		else
			room = Quest.getQuest().getGame_state().containsObject("player").getRoom();
		
		Pair<String,String> randomItem = ItemGenerator.getRandomItem();
		
		int x_loc = 50;
		int y_loc = 50;
		
		item = new IFItemObject(randomItem.m_a, room, x_loc, y_loc, randomItem.m_b);
		
		room.addObject(item);
		
		SimpleNLP.insertObjectSynonym_pcg(item);
		
		//***********
		
		
		IFConditionLocation locCondition = new IFConditionLocation("player", room.getID());
		
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
		
		IFAction itemTaken = new IFAction("player", "take", item.getID());
		
		IFConditionAction trigger = new IFConditionAction(itemTaken);
		
		//Build effects
		IFAction announce = new IFAction("player", "talk", "player",
				"reply", "I found the " + item.getID() + "!");
		
		LinkedList<IFAction> effects = new LinkedList<IFAction>();
		
		effects.add(announce);
		
		
		//Build hint
		String hintText = "Hmmm, that quest won't solve itself.  Wonder what the  " + item.getID() + "is";
		
		IFHint hint = new IFHint(hintText, 3);
		
		LinkedList<IFHint> hints = new LinkedList<IFHint>();
		
		hints.add(hint);
		
		//Put it all together
		
		this.pp = new IFPlotPoint(ppName, plotName, precondition, trigger, effects, null);
		
		
	}
	
	@Override
	public void expand() {
		Quest.getQuest().addToQuest(this);

	}

	@Override
	public IFPlotPoint evaluate() {
		
		return this.pp;
	}
	
	public String describe(){
		
		return "Go out and seek the " + this.item.getID();
	}
	
	public IFItemObject getItem() {
		return this.item;
	}

}

package pcg.grammar;

import java.util.LinkedList;

import ifgameengine.IFAction;
import ifgameengine.IFHint;
import ifgameengine.IFRoom;
import pcg.PCG_Random;
import pcg.Quest;
import storyengine.IFCondition;
import storyengine.IFConditionAnd;
import storyengine.IFConditionLocation;
import storyengine.IFConditionNot;
import storyengine.IFConditionPlotpoint;
import storyengine.IFPlotPoint;

public class PCG_Explore implements PCG_TerminalSymbol {

	IFPlotPoint pp = null;
	IFRoom exploreLoc = null;
	
	public PCG_Explore(){
		
		//Create the plot point
		String ppName = "explore_" + Quest.getNextSequenceNumber();
		String plotName = Quest.getQuest().getQuestName();
		
		//Build the preconditions
		IFPlotPoint previousPP = Quest.getQuest().getLastPlotpoint();
		
		exploreLoc = Quest.getQuest().getGame_state().getRooms().get(PCG_Random.get_rng().nextInt(Quest.getQuest().getGame_state().getRooms().size()));
		IFConditionLocation locCondition = new IFConditionLocation("player", exploreLoc.getID());
		
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
		IFAction announce = new IFAction("player", "talk", "player",
				"reply", "This is the place the " + Quest.getQuest().getQuestGiver().getID() + " was talking about!  I'm on the right track!");
		
		LinkedList<IFAction> effects = new LinkedList<IFAction>();
		
		effects.add(announce);
		
		
		//Build hint
		String hintText = "Hmmm, that quest won't solve itself.  Wonder what is in the " + exploreLoc.getID();
		
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
		if (this.exploreLoc == null)
			throw new NullPointerException();
		
		return "You must go to the " + this.exploreLoc.getID() + ".  ";
	}
	
	public IFRoom getLocation(){
		return this.exploreLoc;
	}

}

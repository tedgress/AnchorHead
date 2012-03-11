package pcg.grammar;

import java.util.LinkedList;

import storyengine.IFPlotPoint;

import ifgameengine.IFAction;
import ifgameengine.IFCharacter;
import ifgameengine.IFHint;
import ifgameengine.IFItemObject;
import ifgameengine.IFRoom;
import pcg.PCG_Random;
import pcg.Quest;
import storyengine.IFCondition;
import storyengine.IFConditionAction;
import storyengine.IFConditionAnd;
import storyengine.IFConditionLocation;
import storyengine.IFConditionNot;
import storyengine.IFConditionPlotpoint;

public class PCG_Give implements PCG_TerminalSymbol {

	IFPlotPoint pp = null;
	IFCharacter givee = null;
	IFRoom giveeRoom = null;
	IFItemObject itemToGive = null;
	
	public PCG_Give() {
				//Create the plot point
		String ppName = "give_" + Quest.getNextSequenceNumber();
		String plotName = Quest.getQuest().getQuestName();
		
		//Build the preconditions
		IFPlotPoint previousPP = Quest.getQuest().getLastPlotpoint();
		
		givee = Quest.getQuest().getGame_state().getAllNPCs().get(PCG_Random.get_rng().nextInt(Quest.getQuest().getGame_state().getAllNPCs().size()));
		
		giveeRoom = Quest.getQuest().getGame_state().containsObject(givee.getID()).getRoom();
		
		
		IFConditionLocation locCondition = new IFConditionLocation("player", giveeRoom.getID());
		
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
		
		PCG_Gather itemReceived = (PCG_Gather) Quest.getQuest().getLastTermOfType(PCG_Gather.class);
		this.itemToGive = itemReceived.getItem();
		
		IFAction giveItem = new IFAction("player", "give", givee.getID(), itemToGive.getID());
		
		IFConditionAction trigger = new IFConditionAction(giveItem);
		
		//Build effects
		IFAction announce = new IFAction(givee.getID(), "talk", "player",
				"reply", "You have found the " + this.itemToGive.getID() + "!  Thank you so much!");
		
		LinkedList<IFAction> effects = new LinkedList<IFAction>();
		
		effects.add(announce);
		
		
		//Build hint
		String hintText = "The " + givee.getID() +"was looking for that " + this.itemToGive.getID() + ".  He's in the " + giveeRoom.getID();
		
		IFHint hint = new IFHint(hintText, 3);
		
		LinkedList<IFHint> hints = new LinkedList<IFHint>();
		
		hints.add(hint);
		
		//Put it all together
		
		this.pp = new IFPlotPoint(ppName, plotName, precondition, trigger, effects, null);
		
		
	}
	
	public IFRoom getGiveeRoom(){
		return this.giveeRoom;
	}
	
	public IFCharacter getGivee(){
		return this.givee;
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
		return "Go to the " + this.giveeRoom.getID() + " and give the " + this.itemToGive.getID() + " to the " + this.givee.getID() +".  ";
	}

}

package pcg;

import java.util.LinkedList;
import java.util.Queue;

import pcg.grammar.PCG_Intro;
import pcg.grammar.PCG_TerminalSymbol;

import storyengine.IFPlotPoint;
import storyengine.IFStoryState;
import ifgameengine.IFCharacter;
import ifgameengine.IFGameState;

public class Quest {
	
	private static Quest quest = null;
	
	IFCharacter questGiver = null;
	IFGameState game_state = null;
	IFStoryState story_state = null;
	private static int nextNumber = 0;
	
	public static int getNextSequenceNumber(){
		Quest.nextNumber++;
		
		return Quest.nextNumber;
	}
	
	private LinkedList<PCG_TerminalSymbol> quest_expression = new LinkedList<PCG_TerminalSymbol>();
	
	public IFStoryState getStory_state() {
		return story_state;
	}

	public void setStory_state(IFStoryState story_state) {
		this.story_state = story_state;
	}

	String questName = null;
	
	public String getQuestName() {
		return questName;
	}

	public void setQuestName(String questName) {
		this.questName = questName;
	}

	public IFGameState getGame_state() {
		return game_state;
	}

	public void setGame_state(IFGameState game_state) {
		this.game_state = game_state;
	}

	public IFCharacter getQuestGiver() {
		return questGiver;
	}

	public void setQuestGiver(IFCharacter questGiver) {
		this.questGiver = questGiver;
	}

	private Quest(){
		//private default constructor
	}
	
	public static Quest getQuest() {
		if ( null == Quest.quest )
			Quest.quest = new Quest();
		
		return Quest.quest;
	}
	
	public void addToQuest(PCG_TerminalSymbol t){
		this.quest_expression.add(t);
		this.story_state.insertPlotpoint_pcg(t.evaluate());
	}
	
	public PCG_TerminalSymbol getLastTermOfType(Class<?> class_type){
		
		Queue<PCG_TerminalSymbol> symbols = new LinkedList<PCG_TerminalSymbol>(this.quest_expression);
		
		while (!symbols.isEmpty()){
			if ( symbols.peek().getClass().isAssignableFrom(class_type) )
				return symbols.peek();
			
			symbols.poll();
		}
		
		return null;
		
		
		
	}
	
	public LinkedList<String> getQuestStory(){
		
		LinkedList<String> qStory = new LinkedList<String>();
		
		for (PCG_TerminalSymbol s : this.quest_expression){
			if (!PCG_Intro.class.isInstance(s))
				qStory.add(s.describe());
		}
		
		return qStory;
	}
	
	public IFPlotPoint getLastPlotpoint(){
		if (this.quest_expression.isEmpty())
			return null;
		else
			return this.quest_expression.getLast().evaluate();
	}
	
	public boolean isQuestComplete(){
		if (this.story_state == null )
			return false;
		
		else if (IFPlotPoint.FINISHED ==  this.story_state.getPlotPointState( this.quest_expression.getLast().evaluate() ))
			return true;
		else
			return false;
		
	}
	
	public boolean isQuestActive(){
		if (null != quest && !this.isQuestComplete())
			return true;
		return false;
	}
	


}

package drama_management;

import ifgameengine.IFAction;
import ifgameengine.IFGameState;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import storyengine.IFPlotPoint;
import storyengine.IFStoryState;

public class DramaManager implements IFDramaManager {

	IFStoryState currentStoryState = null;
	IFGameState currentGameState = null;
	HashSet<IFPlotPoint> totalPlot = null;
	
	
	private static final int ONE_MINUTE_IN_CYCLES = 1200;
	
	List<String> failedInput = new LinkedList<String>();
	String failedInputResponse = "Say what?";

	protected IFGameState getCurrentGameState() {
		return currentGameState;
	}

	protected void setCurrentGameState(IFGameState currentGameState) {
		this.currentGameState = currentGameState;
	}

	protected IFStoryState getCurrentStoryState() {
		return currentStoryState;
	}

	protected void setCurrentStoryState(IFStoryState currentState) {
		this.currentStoryState = currentState;
	}

	@Override
	public void update(IFStoryState story, IFGameState game,
			List<String> logOutput) {

		// Set current game information
		this.setCurrentStoryState(story);
		this.setCurrentGameState(game);
		
		if (game.getCycle() % ONE_MINUTE_IN_CYCLES == 0) {
			this.talkToSelf("I'm not feeling so good.");
			
		}
		
		//System.out.println(this.getRemainingPlotPoints().toString());
		
		

	}
	
	protected Collection<IFPlotPoint> getRemainingPlotPoints(){
		if (this.totalPlot == null)
			this.totalPlot = new HashSet<IFPlotPoint>(this.getCurrentStoryState().getStory().getPlotPoints());
		
		HashSet<IFPlotPoint> remaining = new HashSet<IFPlotPoint>(this.totalPlot);
			
		remaining.removeAll( this.getCurrentStoryState().getPlotPointsVisited() );
		
		return remaining;
		
		
	}

	protected void talkToSelf(String message){
		
		if (null == this.getCurrentStoryState()
				|| null == this.getCurrentGameState())
			throw new NullPointerException("Null States");

		this.getCurrentGameState().enqueueAction(
				new IFAction("player", "talk", "player", "reply", message),
				this.getCurrentStoryState());

	}

	@Override
	public String informBadInput(String unrecognized) {
		
		//Add the input to the queue
		this.failedInput.add(unrecognized);
		
		//Decide what to do an return a response.
		//NOTE: This will occur in the GUI thread...
		return this.failedInputResponse;
		
	
	}
}

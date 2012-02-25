package drama_management;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import storyengine.IFPlotPoint;
import storyengine.IFStoryState;
import ifgameengine.AbstractHint;
import ifgameengine.IFGameState;
import ifgameengine.IFHint;

public class HintRepository {
	
	protected HashSet<IFHint> hinted = new HashSet<IFHint>();
	protected Stack<Integer> cycleHinted = new Stack<Integer>();
	private static final int MIN_TIME_BETWEEN_HINTS = 2400;
	
	protected int getCycleLastHintAccepted() {
		if (!this.cycleHinted.isEmpty()){
			return this.cycleHinted.peek();
			 
		}
		else
			return 0;
		
	}
	
	public int getNumTimesHinted(){
		return this.hinted.size();
	}

	public IFHint getHint(IFStoryState story_state, IFGameState game_state, List<String> logOutput){
		
		IFHint nextHint = null;
		
		if ( this.hintedRecently(game_state) ){
			//No hint for you!
		}
		else{
			Queue<IFPlotPoint> remainingHintablePP = new LinkedList<IFPlotPoint>(this.getRemainingHintable(story_state));
			
			if (!remainingHintablePP.isEmpty()){
				nextHint = remainingHintablePP.poll().getHints().remove(0);
			}
			
			for (IFPlotPoint pp : remainingHintablePP){
				
			}
		}
		
	
		return nextHint;
	}
	
	public boolean hintedRecently(IFGameState game_state){
		if (game_state.getCycle() < HintRepository.MIN_TIME_BETWEEN_HINTS && this.getNumTimesHinted() == 0)
			return false;
		else if ((game_state.getCycle() - this.getCycleLastHintAccepted() < HintRepository.MIN_TIME_BETWEEN_HINTS))
			return true;
		else
			return false;
		
	}
	
	public void updateHint(AbstractHint recentHint, boolean hintAccepted, IFGameState game_state){
		if (hintAccepted) {
			this.hinted.add((IFHint)recentHint);
			this.cycleHinted.push(game_state.getCycle());
		}
	}
	
	protected HashSet<IFPlotPoint> getRemainingHintable(IFStoryState story_state){
		
		HashSet<IFPlotPoint> remaining = new HashSet<IFPlotPoint>();
		
		for (IFPlotPoint pp : story_state.getRemainingPlotPoints()) {
			for (IFHint h : pp.getHints()) {
				// TODO At the moment, assuming one hint per plot point
				if (!this.hinted.contains(h))
					remaining.add(pp);
			}

		}
		
		return remaining;
	}

}


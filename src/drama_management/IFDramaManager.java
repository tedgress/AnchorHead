package drama_management;

import java.util.List;

import ifgameengine.IFGameState;
import storyengine.IFPlotPoint;
import storyengine.IFStoryState;

public interface IFDramaManager {
	
	
	/**
	 * Update the Drama Manager.
	 * 
	 * @param story Current Story state, this can be modified by the DM
	 * @param game Current Game state, this can be modified by the DM
	 * @param logOutput String output for logging, this can be modified by the DM
	 */
	public void update(IFStoryState story, IFGameState game, List<String> logOutput);
	
	/**
	 * Inform the DM that the user's input was not recognized
	 * 
	 * @param unrecognized The user's unrecognized command
	 * @param current game state
	 * @return Return the string that should be displayed to the user
	 */
	public String informBadInput(String unrecognized, IFGameState game_state);
	
	public void informPlotPointComplete(IFPlotPoint pp, IFGameState game_state);
	
	public void cheat(boolean realHints);

}

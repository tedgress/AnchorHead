package drama_management;

import java.util.List;

import storyengine.IFStoryState;
import ifgameengine.AbstractHint;
import ifgameengine.IFAction;
import ifgameengine.IFGameState;


public class PlayerPatron {
	

	/**
	 * Attempts to accept the hint and provide the player a hint in a creative way.
	 * 
	 * @param hint The offered hint.
	 * @param story_state Current Story state, this may be modified by the patron
	 * @param game_state The Current game state, this may be modified by the patron
	 * @param logOutput Log of actions
	 * @return Returns true if the hint is consumed (accepted), otherwise false
	 */
	public boolean hintPlayer(AbstractHint hint, IFStoryState story_state, IFGameState game_state, List<String> logOutput){
		
		//TED: (from Josh) I will support the toString method as a way of getting the hint text
		this.talkToSelf(hint.toString(), story_state, game_state);
		
		return true;
	}
	
	/**
	 * Display a message as if the player is talking to himself (or thinking outloud)
	 * @param message The message to be displayed to the user
	 * @param story_state The current story state
	 * @param game_state The current game state
	 */
	protected void talkToSelf(String message, IFStoryState story_state,
			IFGameState game_state) {

		game_state.enqueueAction(new IFAction("player", "talk", "player",
				"reply", message), story_state);

	}
}

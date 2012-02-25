package drama_management;

import java.util.LinkedList;
import java.util.List;

import ifgameengine.IFGameState;
import storyengine.IFStoryState;

public class PlayerModel {
	
	List<String> failedInput = new LinkedList<String>();
	String failedInputResponse = "Say what?";
	private static final int ONE_MINUTE_IN_CYCLES = 1200;
	
	
	public boolean isPlayerFrustrated(IFStoryState story_state, IFGameState game_state, List<String> logOutput){
		
		if (game_state.getCycle() % (PlayerModel.ONE_MINUTE_IN_CYCLES / 2) == 0 )
			return true;
		
		return false;
	}
	
	public String addBadUserInput(String input){
		
		return failedInputResponse;
	}

}

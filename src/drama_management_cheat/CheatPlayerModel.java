package drama_management_cheat;

import ifgameengine.IFGameState;

import java.util.List;

import storyengine.IFStoryState;
import drama_management.AbstractHintMachine;
import drama_management.AbstractPlayerModel;
import drama_management.HintRepository;

public class CheatPlayerModel implements AbstractPlayerModel {

	boolean isFrustrated = false;
	
	@Override
	public boolean isPlayerFrustrated(IFStoryState story_state,
			IFGameState game_state, List<String> logOutput,
			AbstractHintMachine hint_repo) {
		if(isFrustrated){
			isFrustrated = false;
			return true;
		}
		return false;
	}

	@Override
	public String addBadUserInput(String input, IFGameState game_state) {
		// TODO Auto-generated method stub
		if (input.equals("cheat")){
			this.isFrustrated = true;
			return "CHEATER!";
		}
		return "Say what?";
	}

	@Override
	public void markEvent(int cycle, int move) {
		// TODO Auto-generated method stub

	}

}

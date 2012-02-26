package drama_management;

import java.util.List;

import ifgameengine.IFGameState;
import storyengine.IFStoryState;

public interface AbstractPlayerModel {
	
	public boolean isPlayerFrustrated(IFStoryState story_state, IFGameState game_state, List<String> logOutput, AbstractHintMachine hint_repo);
	
	public String addBadUserInput(String input, IFGameState game_state);
	
	public void markEvent(int cycle, int move);

}

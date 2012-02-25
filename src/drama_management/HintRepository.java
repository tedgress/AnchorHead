package drama_management;

import java.util.List;

import storyengine.IFStoryState;
import ifgameengine.AbstractHint;
import ifgameengine.IFGameState;
import ifgameengine.IFHint;

public class HintRepository {
	
	public IFHint getHint(IFStoryState story_state, IFGameState game_state, List<String> logOutput){
		
		
		return new IFHint("Hmmm, maybe I should start looking around", 3);
	}
	
	public void updateHint(AbstractHint recentHint, boolean hintAccepted){
		
	}

}


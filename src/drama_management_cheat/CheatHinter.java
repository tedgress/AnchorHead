package drama_management_cheat;

import ifgameengine.AbstractHint;
import ifgameengine.IFGameState;
import ifgameengine.IFHint;

import java.util.List;

import storyengine.IFStoryState;
import drama_management.AbstractHintMachine;

public class CheatHinter implements AbstractHintMachine {
	
	int cheated = 0;

	@Override
	public IFHint getHint(IFStoryState story_state, IFGameState game_state,
			List<String> logOutput) {
		// TODO Auto-generated method stub
		cheated++;
		return new IFHint("You are a cheater!", 3);
	}

	@Override
	public int getNumTimesHinted() {
		// TODO Auto-generated method stub
		return cheated;
	}

	@Override
	public boolean hintedRecently(IFGameState game_state) {
		// TODO Auto-generated method stub
		
		if (game_state.getCycle() % 2 == 0)
			return true;
		else
			return false;
	}

	@Override
	public void updateHint(AbstractHint recentHint, boolean hintAccepted,
			IFGameState game_state) {
		// TODO Auto-generated method stub

	}

}

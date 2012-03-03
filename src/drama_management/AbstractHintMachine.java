package drama_management;

import java.util.List;

import storyengine.IFStoryState;
import ifgameengine.AbstractHint;
import ifgameengine.IFGameState;
import ifgameengine.IFHint;

public interface AbstractHintMachine {
	
	public IFHint getHint(IFStoryState story_state, IFGameState game_state, List<String> logOutput);
	
	public int getNumTimesHinted();
	
	public boolean hintedRecently(IFGameState game_state);
	
	public void updateHint(AbstractHint recentHint, boolean hintAccepted, IFGameState game_state);
	
	public int get_time_between_hints();

	public void set_time_between_hints(int mIN_TIME_BETWEEN_HINTS);

}

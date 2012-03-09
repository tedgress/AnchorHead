package ProceduralContentGeneration;

import java.util.List;

import ifgameengine.IFGameState;
import storyengine.IFStoryState;

public interface AbstractStoryGenerator {
	
	public boolean generate(IFStoryState story_state, IFGameState game_state, List<String> logOutput);

}

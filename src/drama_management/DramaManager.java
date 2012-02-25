package drama_management;

import ifgameengine.AbstractHint;
import ifgameengine.IFGameState;
import java.util.List;

import storyengine.IFStoryState;

public class DramaManager implements IFDramaManager {
	
	protected PlayerModel model = new PlayerModel();
	protected HintRepository hint_repo = new HintRepository();
	protected PlayerPatron patron = new PlayerPatron();
	

	@Override
	public void update(IFStoryState story, IFGameState game,
			List<String> logOutput) {

		//Is the player frustrated?
		if ( this.model.isPlayerFrustrated(story, game, logOutput)){
			//Let's help him out, grab a hint
			AbstractHint hint = this.hint_repo.getHint(story, game, logOutput);
			
			if (null != hint){
				//Attempt to offer the hint to the player
				boolean hintAccepted = this.patron.hintPlayer(hint, story, game, logOutput);
				
				//Update the hint status
				this.hint_repo.updateHint(hint, hintAccepted);
				
			}
		}
		
	

	}
	

	@Override
	public String informBadInput(String unrecognized) {
		
		//Decide what to do an return a response.
		//NOTE: This will occur in the GUI thread...
		return this.model.addBadUserInput(unrecognized);
		
	
	}
}

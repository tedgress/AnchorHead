package drama_management;

import ifgameengine.AbstractHint;
import ifgameengine.IFGameState;
import java.util.List;

import pcg.PCG_Random;
import pcg.QuestGenerator;

import drama_management_cheat.CheatHinter;
import drama_management_cheat.CheatPlayerModel;

import storyengine.IFPlotPoint;
import storyengine.IFStoryState;

public class DramaManager implements IFDramaManager {

	protected AbstractPlayerModel model = new PlayerModel();
	protected AbstractHintMachine hint_repo = new HintRepository();
	protected PlayerPatron patron = new PlayerPatron();
	protected QuestGenerator qg = new QuestGenerator();

	@Override
	public void update(IFStoryState story, IFGameState game,
			List<String> logOutput) {

		// Is the player frustrated?
		if (this.model.isPlayerFrustrated(story, game, logOutput,
				this.hint_repo)) {

			if (this.model.isPlayerVeryFrustrated(game)) {

				qg.generate(story, game, logOutput);
			} else {
				// Let's help him out, grab a hint
				AbstractHint hint = this.hint_repo.getHint(story, game,
						logOutput);

				if (null != hint) {
					// Attempt to offer the hint to the player
					boolean hintAccepted = this.patron.hintPlayer(hint, story,
							game, logOutput);

					// Update the hint status
					this.hint_repo.updateHint(hint, hintAccepted, game);

				}
			}
		}

	}

	@Override
	public String informBadInput(String unrecognized, IFGameState game_state) {

		// Decide what to do an return a response.
		// NOTE: This will occur in the GUI thread...
		return this.model.addBadUserInput(unrecognized, game_state);

	}

	@Override
	public void informPlotPointComplete(IFPlotPoint pp, IFGameState game_state) {

		this.model.markEvent(game_state.getCycle(),
				game_state.getNumberPlayerMoves());

	}

	@Override
	public void cheat(boolean realHints) {

		if (realHints) {
			this.hint_repo = new HintRepository();
			this.hint_repo.set_time_between_hints(0);
		} else {
			this.hint_repo = new CheatHinter();
		}
		this.model = new CheatPlayerModel();

	}
}

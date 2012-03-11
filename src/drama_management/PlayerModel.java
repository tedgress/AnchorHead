package drama_management;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import pcg.Quest;

import ifgameengine.IFAction;
import ifgameengine.IFGameState;
import ifgameengine.Pair;
import storyengine.IFStoryState;

public class PlayerModel implements AbstractPlayerModel {

	List<String> failedInput = new LinkedList<String>();
	String failedInputResponse = "Say what?";
	private static final int ONE_MINUTE_IN_CYCLES = 1200;
	private static final int FRUSTRATION_LIMIT = 20;
	private static final int VERY_FRUSTRATED_LIMIT = 30;
	protected HashMap<Integer, Integer> cyclesToMoves = new HashMap<Integer, Integer>();
	protected Stack<Pair<Integer, Integer>> significantMoveHistory = new Stack<Pair<Integer, Integer>>();
	protected int lastMoveNumber = 0;
	private final static Logger log = Logger.getLogger(PlayerModel.class
			.getName());
	private static FileHandler logFile;

	public PlayerModel() {
		log.setLevel(Level.FINEST);
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
			java.util.Date date = new java.util.Date();

			String timestamp = df.format(date);

			logFile = new FileHandler("log" + timestamp + ".txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.addHandler(logFile);

	}

	public boolean isPlayerFrustrated(IFStoryState story_state,
			IFGameState game_state, List<String> logOutput,
			AbstractHintMachine hint_repo) {

		if (game_state.getNumberPlayerMoves() > this.lastMoveNumber) {
			// Update info
			this.failedInput.clear();
			this.lastMoveNumber = game_state.getNumberPlayerMoves();
		}

		int isQuestActive = 0;
		if ( Quest.getQuest().isQuestActive() )
			isQuestActive = 12;
		
		// Log frustration levels
		log.finest(game_state.getCycle() + " "
		        + this.calculate_f_c(game_state) + " "
				+ this.calculate_r_n(game_state) + " "
				+ this.calculate_m_p(game_state) + " "
				+ this.totalFrustration(game_state) + " "
				+ isQuestActive + " " +
				+ PlayerModel.FRUSTRATION_LIMIT + " "
				+ PlayerModel.VERY_FRUSTRATED_LIMIT);

		if (this.totalFrustration(game_state) >= PlayerModel.FRUSTRATION_LIMIT
				&& this.totalFrustration(game_state) != 0) {

			return true;
		}


		return false;
	}
	
	public boolean isPlayerVeryFrustrated(IFGameState game_state){
		
		if (this.totalFrustration(game_state) >= PlayerModel.VERY_FRUSTRATED_LIMIT
				&& this.totalFrustration(game_state) != 0) {

			return true;
		}


		return false;
		
	}

	// Frustration is measured by three factors:
	// 1. Unrecognized input, c, calculated by f(c)
	// 2. Repeated commands, r, calculated by r(n)
	// 3. Moves since last plotpoint, m(p)
	protected int totalFrustration(IFGameState game_state) {
		int k_r = 10; // Factor for repeated commands
		int k_c = 3; // Factor for unrecognized commands
		int k_m = 1; // Factor for moves since last plot point

		int triFactors = (k_c * this.calculate_f_c(game_state))
				+ (k_r * this.calculate_r_n(game_state))
				+ (k_m * this.calculate_m_p(game_state));

		return triFactors;
	}

	public String addBadUserInput(String input, IFGameState game_state) {

		this.failedInput.add(input);

		if (game_state.getNumberPlayerMoves() < 3)
			return "Sorry, I don't understand what you want to do.  Try really simple commands like take, look, move, etc...";
		else if (this.failedInput.size() >= 2)
			return "Maybe I should have read the leaflet in the mansion...";

		return failedInputResponse;
	}

	public void markEvent(int cycle, int move) {
		this.cyclesToMoves.put(cycle, move);
		this.significantMoveHistory
				.add(new Pair<Integer, Integer>(cycle, move));
	}

	/**
	 * Calculates the frustration factor measured, m(p), moves since last plot
	 * point
	 *
	 * @param game_state
	 * @return An integer value; the number of moves since last plot point
	 */
	protected int calculate_m_p(IFGameState game_state) {

		if (!this.significantMoveHistory.isEmpty()) {
			return game_state.getNumberPlayerMoves()
					- this.significantMoveHistory.peek().m_b;
		} else
			return game_state.getNumberPlayerMoves();

	}

	/**
	 * Calculates f(c), the number of failed input attempts since the last
	 * accepted command
	 *
	 * @param game_state
	 * @return the number of failed input attempts since the last successfully
	 *         inputed command.
	 */
	protected int calculate_f_c(IFGameState game_state) {

		return this.failedInput.size();
	}

	/**
	 * Calculates the number of similar repeated commands
	 *
	 * @param game_state
	 * @return The number of repeated commands of the same type
	 */
	protected int calculate_r_n(IFGameState game_state) {
		ListIterator<IFAction> actionI = game_state
				.getUserActionTrace()
				.getActions()
				.listIterator(
						game_state.getUserActionTrace().getActions().size());

		int MAX_REPEATED_COMMANDS = 2;

		boolean limit_reached = false;

		if (actionI.hasPrevious()) {
			IFAction lastAction = actionI.previous();

			for (int x = 0; x < MAX_REPEATED_COMMANDS; x++) {
				if (!actionI.hasPrevious())
					break;

				IFAction previousAction = actionI.previous();

				if (previousAction.equals(lastAction)
						&& x == (MAX_REPEATED_COMMANDS - 1)) {
					limit_reached = true;
				} else if (!previousAction.equals(lastAction))
					break;

				lastAction = previousAction;
			}
		}

		if (limit_reached)
			return MAX_REPEATED_COMMANDS;
		else
			return 0;
	}

}

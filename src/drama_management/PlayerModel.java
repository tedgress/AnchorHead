package drama_management;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import ifgameengine.IFAction;
import ifgameengine.IFGameState;
import ifgameengine.Pair;
import storyengine.IFStoryState;

public class PlayerModel implements AbstractPlayerModel {

	List<String> failedInput = new LinkedList<String>();
	String failedInputResponse = "Say what?";
	private static final int ONE_MINUTE_IN_CYCLES = 1200;
	private static final int FRUSTRATION_LIMIT = 20;
	protected HashMap<Integer, Integer> cyclesToMoves = new HashMap<Integer, Integer>();
	protected Stack<Pair<Integer, Integer>> significantMoveHistory = new Stack<Pair<Integer, Integer>>();
	protected int lastMoveNumber = 0;
	private final static Logger log = Logger.getLogger(PlayerModel.class
			.getName());
	private static FileHandler logFile;

	public PlayerModel() {
		log.setLevel(Level.INFO);
		try {
			java.util.Date date = new java.util.Date();
			Timestamp timestamp = new Timestamp(date.getTime());

			logFile = new FileHandler("log" + timestamp.toString() + ".txt");
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
		System.out.println("f(c) = " + this.calculate_f_c(game_state)
				+ " r(n) = " + this.calculate_r_n(game_state) + " m(p) = "
				+ this.calculate_m_p(game_state));

		// Log frustration levels
		log.info(game_state.getCycle() + " " 
		        + this.calculate_f_c(game_state) + " " 
				+ this.calculate_r_n(game_state) + " "
				+ this.calculate_m_p(game_state) + " "
				+ this.totalFrustration(game_state, hint_repo) + " "
				+ PlayerModel.FRUSTRATION_LIMIT);

		if (this.totalFrustration(game_state, hint_repo) >= PlayerModel.FRUSTRATION_LIMIT
				&& this.totalFrustration(game_state, hint_repo) != 0) {
			System.out.println(this.totalFrustration(game_state, hint_repo));
			return true;
		}

		// if (game_state.getCycle() % (PlayerModel.ONE_MINUTE_IN_CYCLES / 2) ==
		// 0 )
		// return true;

		return false;
	}

	// Frustration is measured by three factors:
	// 1. Unrecognized input, c, calculated by f(c)
	// 2. Repeated commands, r, calculated by r(n)
	// 3. Moves since last plotpoint, m(p)
	protected int totalFrustration(IFGameState game_state,
			AbstractHintMachine hint_repo) {
		int k_r = 10; // Factor for repeated commands
		int k_c = 1; // Factor for unrecognized commands
		int k_m = 1; // Factor for moves since last plot point

		int triFactors = (k_c * this.calculate_f_c(game_state))
				+ (k_r * this.calculate_r_n(game_state))
				+ (k_m * this.calculate_m_p(game_state));

		return triFactors;
	}

	public String addBadUserInput(String input, IFGameState game_state) {

		this.failedInput.add(input);

		if (game_state.getNumberPlayerMoves() < 10)
			return "Sorry, I don't understand what you want to do.  Try really simple commands like talk, look, move, etc...";

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
		int COEFECCIENT_OF_REPEATED_ACTIONS = 10;
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
			return COEFECCIENT_OF_REPEATED_ACTIONS;
		else
			return 0;
	}

}

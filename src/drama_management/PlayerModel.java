package drama_management;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

import ifgameengine.IFAction;
import ifgameengine.IFGameState;
import ifgameengine.Pair;
import storyengine.IFStoryState;

public class PlayerModel {
	
	List<String> failedInput = new LinkedList<String>();
	String failedInputResponse = "Say what?";
	private static final int ONE_MINUTE_IN_CYCLES = 1200;
	private static final int FRUSTRATION_LIMIT = 20;
	protected HashMap<Integer, Integer> cyclesToMoves = new HashMap<Integer, Integer>();
	protected Stack<Pair<Integer, Integer>> significantMoveHistory = new Stack<Pair<Integer, Integer>>();
	protected int lastMoveNumber = 0;
	
	
	
	
	public boolean isPlayerFrustrated(IFStoryState story_state, IFGameState game_state, List<String> logOutput, HintRepository hint_repo){
		
		int FRUSTRATION_LIMIT = 20;
		
		if (game_state.getNumberPlayerMoves() > this.lastMoveNumber){
			//Update info
			this.failedInput.clear();
			this.lastMoveNumber = game_state.getNumberPlayerMoves();
		}
		System.out.println(
		"f(u) = " + this.calculate_f_u(game_state) +
		" g(r) = " + this.calculate_g_r(game_state) +
		" m(p) = " + this.calculate_m_p(game_state) );
		
		if ( this.totalFrustration(game_state, hint_repo) >= PlayerModel.FRUSTRATION_LIMIT && this.totalFrustration(game_state, hint_repo) != 0) {
			System.out.println(this.totalFrustration(game_state, hint_repo));
			return true;
		}
			
		//if (game_state.getCycle() % (PlayerModel.ONE_MINUTE_IN_CYCLES / 2) == 0 )
			//return true;
		
		return false;
	}
	
	//Frustration is measured by three factors:
	//1.  Unrecognized input, u, calculated by f(u)
	//2.  Repeated commands, r, calculated by g(r)
	//3.  Moves since last plotpoint, m_p, calculated by h(m_p)
	protected int totalFrustration(IFGameState game_state, HintRepository hint_repo){
		int k_f = 10;
		
		int triFactors = this.calculate_f_u(game_state) + (k_f * this.calculate_g_r(game_state)) + this.calculate_m_p(game_state);
		
		return triFactors;
	}
	
	public String addBadUserInput(String input, IFGameState game_state){
		
		this.failedInput.add(input);
		
		if (game_state.getNumberPlayerMoves() < 10)
			return "HELP ME";
		
		return failedInputResponse;
	}
	
	public void markEvent(int cycle, int move){
		this.cyclesToMoves.put(cycle, move);
		this.significantMoveHistory.add(new Pair<Integer, Integer>(cycle,move));
	}
	
	
	protected int calculate_m_p(IFGameState game_state){
		ListIterator<Pair<Integer, IFAction>> actionI = game_state.getUserActionTrace().getActionTrace().listIterator(game_state.getUserActionTrace().getActionTrace().size());
		
		if (!this.significantMoveHistory.isEmpty()) {
			return game_state.getNumberPlayerMoves() - this.significantMoveHistory.peek().m_b;
		}
		else
			return game_state.getNumberPlayerMoves();

		
	}
	
	protected int calculate_f_u(IFGameState game_state){
		
		return this.failedInput.size();
	}
	
	
	protected int calculate_g_r(IFGameState game_state){
		ListIterator<IFAction> actionI = game_state.getUserActionTrace().getActions().listIterator(game_state.getUserActionTrace().getActions().size());
		
		int MAX_REPEATED_COMMANDS = 2;
		int COEFECCIENT_OF_REPEATED_ACTIONS = 10;
		boolean limit_reached = false;
		
		if (actionI.hasPrevious()){
			IFAction lastAction = actionI.previous();
			
			for (int x = 0; x < MAX_REPEATED_COMMANDS; x++){
				if (!actionI.hasPrevious())
					break;
				
				IFAction previousAction = actionI.previous();
				
				if (previousAction.equals(lastAction) && x == ( MAX_REPEATED_COMMANDS - 1) ){
					limit_reached = true;
				}
				else if (!previousAction.equals(lastAction))
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

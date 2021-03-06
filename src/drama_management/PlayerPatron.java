package drama_management;

import java.util.List;

import storyengine.IFStoryState;
import ifgameengine.AbstractHint;
import ifgameengine.IFAction;
import ifgameengine.IFGameState;


public class PlayerPatron {
	

	/**
	 * Attempts to accept the hint and provide the player a hint in a creative way.
	 * 
	 * @param hint The offered hint.
	 * @param story_state Current Story state, this may be modified by the patron
	 * @param game_state The Current game state, this may be modified by the patron
	 * @param logOutput Log of actions
	 * @return Returns true if the hint is consumed (accepted), otherwise false
	 */
	public boolean hintPlayer(AbstractHint hint, IFStoryState story_state, IFGameState game_state, List<String> logOutput){
		
		//TED: (from Josh) I will support the toString method as a way of getting the hint text
		//this.talkToSelf(hint.toString(), story_state, game_state);
		//this.talkToDrunk(hint.toString(), story_state, game_state);
		//this.drunkTalksToHimself(hint.toString(), story_state, game_state);
		
		String currentRoom = game_state.containsObject("player").getRoom().getID();
		

		/*
		 *  Room Names (a.k.a. places to throw a keggar??)
		 * 
		 *  street
		 *  park
		 *  livingroom
		 *  hall - Talking Portrait
		 *  backyard - Crypt Ghosts X
		 *  bedroom - 
		 *  basement - 
		 *  bar - Crazy Drunk x
		 *  magic-shop - Talking Grimoire x
		 *  library - Wizard's Hat x
		 *  observatory - scholar X
		 *  sewers - Rat King x
		 */
		
		
		if (currentRoom.equals("street")) {
			
		} else if (currentRoom.equals("park")) {
			
		} else if (currentRoom.equals("livingroom")) {
			
		} else if (currentRoom.equals("hall")) {
			
			IFAction myAction = new IFAction("haunted_frame", IFAction.TALK, "haunted_frame", IFAction.IDLE, "...<whisper>...");
			IFAction myAction2 = new IFAction("haunted_frame", IFAction.TALK, "haunted_frame", IFAction.IDLE, "..<whisper>..");
			IFAction myAction3 = new IFAction("haunted_frame", IFAction.TALK,  "haunted_frame", IFAction.IDLE, hint.toString());
			myAction.stationary = true;
			myAction2.stationary = true;
			myAction3.stationary = true;
			game_state.enqueueAction(myAction, story_state);
			game_state.enqueueAction(myAction2, story_state);
			game_state.enqueueAction(myAction3, story_state);
			
		
			
		} else if (currentRoom.equals("backyard")) {
			
			IFAction myAction = new IFAction("ghost-twins", IFAction.TALK, "ghost-twins", IFAction.IDLE, ".........");
			IFAction myAction2 = new IFAction("ghost-twins", IFAction.TALK, "ghost-twins", IFAction.IDLE, "We may help you....");
			IFAction myAction3 = new IFAction("ghost-twins", IFAction.TALK,  "ghost-twins", IFAction.IDLE, hint.toString());
			myAction.stationary = true;
			myAction2.stationary = true;
			myAction3.stationary = true;
			game_state.enqueueAction(myAction, story_state);
			game_state.enqueueAction(myAction2, story_state);
			game_state.enqueueAction(myAction3, story_state);
			
			
		} else if (currentRoom.equals("bedroom")) {
			
		} else if (currentRoom.equals("basement")) {
			
		} else if (currentRoom.equals("bar")) {
			
			//if main character is in the bar....and is frustrated...the crazy drunk starts to
			//talk to himself giving away hints.
			this.hCharkTalksToHimself(hint.toString(), "crazy-drunk", story_state, game_state);
			
		} else if (currentRoom.equals("magic-shop")) {

			//if main character is in the magic shop....and is frustrated...the grimoire of 
			//secrets flies at him
			

			IFAction myAction = new IFAction("grimoire", IFAction.TALK, "grimoire", IFAction.IDLE, "Are you having trouble?");
			IFAction myAction2 = new IFAction("grimoire", IFAction.TALK, "grimoire", IFAction.IDLE, "I, the book of shadows, may be of service....");
			IFAction myAction3 = new IFAction("grimoire", IFAction.TALK, "grimoire", IFAction.IDLE, hint.toString());
			myAction.stationary = true;
			myAction2.stationary = true;
			myAction3.stationary = false;
			game_state.enqueueAction(myAction, story_state);
			game_state.enqueueAction(myAction2, story_state);
			game_state.enqueueAction(myAction3, story_state);
			
			
			//game_state.enqueueAction(new IFAction("grimoire", "talk", "player",
			//		"reply", "Are you frustrated player? "), story_state);
			

			//game_state.enqueueAction(new IFAction("grimoire", "talk", "player",
			//		"reply", "I, the GRIMOIRE OF SECRETS, shall aid you."), story_state);
			
			
			//this.talkToPlayerHint(hint.toString(), "grimoire", story_state, game_state);
			
			
		} else if (currentRoom.equals("library")) {
			
			//if main character is in the magic shop....and is frustrated...the grimoire of 
			//secrets flies at him
			

			IFAction myAction = new IFAction("wizard_hat", IFAction.TALK, "wizard_hat", IFAction.IDLE, "Are you having trouble?");
			IFAction myAction2 = new IFAction("wizard_hat", IFAction.TALK, "wizard_hat", IFAction.IDLE, "Even though I am trapped within this hat...");
			IFAction myAction3 = new IFAction("wizard_hat", IFAction.TALK, "wizard_hat", IFAction.IDLE, "I may be of assistance...");
			IFAction myAction4 = new IFAction("wizard_hat", IFAction.TALK, "wizard_hat", IFAction.IDLE, hint.toString());
			myAction.stationary = true;
			myAction2.stationary = true;
			myAction3.stationary = true;
			myAction3.stationary = false;
			game_state.enqueueAction(myAction, story_state);
			game_state.enqueueAction(myAction2, story_state);
			game_state.enqueueAction(myAction3, story_state);
			

			
		} else if (currentRoom.equals("observatory")) {
			
			
			IFAction myAction = new IFAction("scholar", IFAction.TALK, "scholar", IFAction.IDLE, "..mmmhmm...mmmhmmm...");
			IFAction myAction2 = new IFAction("scholar", IFAction.TALK, "scholar", IFAction.IDLE, "Yes..yes...may I help you? Oh I see...");
			IFAction myAction3 = new IFAction("scholar", IFAction.TALK,  "scholar", IFAction.IDLE, hint.toString());
			myAction.stationary = true;
			myAction2.stationary = true;
			myAction3.stationary = false;
			game_state.enqueueAction(myAction, story_state);
			game_state.enqueueAction(myAction2, story_state);
			game_state.enqueueAction(myAction3, story_state);

			
		} else if (currentRoom.equals("sewers")) {
			
			
			game_state.enqueueAction(new IFAction("player", "talk", "rat-king",
					"reply", "I AM the mighty rat king..."), story_state);
			
			this.talkToPlayerHint(hint.toString(), "rat-king", story_state, game_state);
			
		}
		
		
		
		return true;
		
	}
	
	/**
	 * Display a message as if the player is talking to himself (or thinking outloud)
	 * @param message The message to be displayed to the user
	 * @param story_state The current story state
	 * @param game_state The current game state
	 */
	protected void talkToSelf(String message, IFStoryState story_state,
			IFGameState game_state) {

		game_state.enqueueAction(new IFAction("player", "talk", "player",
				"reply", message), story_state);

	}
	
	
	/**
	 * Display a message, that the "crazy drunk" character is talking to the player
	 * @param message The message to be displayed to the user
	 * @param story_state The current story state
	 * @param game_state The current game state
	 */
	protected void talkToPlayerHint(String message, String character_id, IFStoryState story_state,
			IFGameState game_state) {

		
		//talker, talk command, talk-receiver, reply, message
		game_state.enqueueAction(new IFAction(character_id, "talk", "player",
				"reply", message), story_state);

	}
	
	

	/**
	 * Display a message, that the "crazy drunk" character is talking to the player
	 * @param message The message to be displayed to the user
	 * @param story_state The current story state
	 * @param game_state The current game state
	 */
	protected void hintCharacterTalksToAnother(String message, String hCharID, String destCharID, IFStoryState story_state,
			IFGameState game_state) {

		
		//talker, talk command, talk-receiver, reply, message
		game_state.enqueueAction(new IFAction(destCharID, "talk", hCharID,
				"reply", message), story_state);

	}
	

	/**
	 * Display a message, that the "crazy drunk" character is talking to the player
	 * @param message The message to be displayed to the user
	 * @param story_state The current story state
	 * @param game_state The current game state
	 */
	protected void hCharkTalksToHimself(String message, String hCharID, IFStoryState story_state,
			IFGameState game_state) {

		
		//talker, talk command, talk-receiver, reply, message
		game_state.enqueueAction(new IFAction(hCharID, "talk", hCharID,
				"reply", message), story_state);

	}


}

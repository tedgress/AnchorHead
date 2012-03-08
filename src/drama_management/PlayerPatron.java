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
		 *  backyard - Crypt Ghost
		 *  bedroom - 
		 *  basement - 
		 *  bar - Crazy Drunk
		 *  magic-shop - Talking Grimoire
		 *  library - Scholar
		 *  observatory
		 *  sewers - Rat King
		 */
		
		
		if (currentRoom.equals("street")) {
			
		} else if (currentRoom.equals("park")) {
			
		} else if (currentRoom.equals("livingroom")) {
			
		} else if (currentRoom.equals("hall")) {
			
		} else if (currentRoom.equals("backyard")) {
			
		} else if (currentRoom.equals("bedroom")) {
			
		} else if (currentRoom.equals("basement")) {
			
		} else if (currentRoom.equals("bar")) {
			
			//if main character is in the bar....and is frustrated...the crazy drunk starts to
			//talk to himself giving away hints.
			this.hCharkTalksToHimself(hint.toString(), "crazy-drunk", story_state, game_state);
			
		} else if (currentRoom.equals("magic-shop")) {

			//if main character is in the magic shop....and is frustrated...the grimoire of 
			//secrets flies at him
			
			
			game_state.enqueueAction(new IFAction("grimoire", "talk", "player",
					null, "Are you frustrated player?"), story_state);

			
			//!this.hintCharacterTalksToAnother("Are you frustrated player?", "grimoire", "player",  story_state, game_state);
			
			//game_state.enqueueAction(new IFAction("grimoire", "talk", "player",
			//		"reply", "Are you frustrated player? "), story_state);
			

			//game_state.enqueueAction(new IFAction("grimoire", "talk", "player",
			//		"reply", "I, the GRIMOIRE OF SECRETS, shall aid you."), story_state);
			
			
			//this.talkToPlayerHint(hint.toString(), "grimoire", story_state, game_state);
			
			
		} else if (currentRoom.equals("library")) {
			
		} else if (currentRoom.equals("observatory")) {
			
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

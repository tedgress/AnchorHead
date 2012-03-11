package pcg;

import ifgameengine.IFCharacter;
import ifgameengine.IFGameState;
import ifgameengine.IFObject;

import java.util.LinkedList;
import java.util.List;

import pcg.grammar.PCG_Explore;
import pcg.grammar.PCG_Gather;
import pcg.grammar.PCG_Give;
import pcg.quests.FindLostItem;

import storyengine.IFStoryState;

public class QuestGenerator implements AbstractStoryGenerator {

	protected QuestTemplate quest = null;
	protected int questNumber = 0;

	public boolean generate(IFStoryState story_state, IFGameState game_state,
			List<String> logOutput) {

		long seed = game_state.getCycle();
		PCG_Random.seed_rng(seed);

		if (!Quest.getQuest().isQuestActive() && this.quest != null){
			//create a new quest
			Quest.getQuest().newQuest();
			this.questNumber++;
		}
		else if (Quest.getQuest().isQuestActive())
			return false;
		
		// setup the quest
		Quest.getQuest().setGame_state(game_state);
		Quest.getQuest().setQuestName("quest_" + this.questNumber);
		Quest.getQuest().setStory_state(story_state);

		

		// Randomly select a quest giver in the room
		IFCharacter questGiver = this.selectCharacter(game_state);

		if (questGiver == null)
			return false;

		Quest.getQuest().setQuestGiver(questGiver);

		this.quest = FindLostItem.create();

		// Activate the quest, mainly for logging reasons
		Quest.getQuest().setQuestActive();

		return true;
	}

	private IFCharacter selectCharacter(IFGameState game_state) {
		LinkedList<IFCharacter> chars_in_room = new LinkedList<IFCharacter>();
		IFCharacter chosenOne = null;

		for (IFObject obj : game_state.containsObject("player").getRoom()
				.getObjects()) {

			if (obj.getClass().isAssignableFrom(IFCharacter.class)) {
				if (!obj.getID().equals("player")) {
					chars_in_room.add((IFCharacter) obj);
				}

			}

		}

		if (!chars_in_room.isEmpty())
			chosenOne = chars_in_room.get(PCG_Random.get_rng().nextInt(
					chars_in_room.size()));

		if (null != chosenOne)
			System.out.println(chosenOne.getID());

		return chosenOne;
	}

}

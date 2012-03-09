package storyengine;

import ifgameengine.IFAction;
import ifgameengine.IFGameState;

import org.jdom.Element;

public class IFConditionAction extends IFCondition {
	IFAction m_action = null;
	
	/**
	 * Constructor used for PCG
	 * @param action
	 */
	public IFConditionAction(IFAction action){
		this.m_action = action;
	}
	
	public IFConditionAction(){
		//nothing special
		
	}
	public boolean evaluate(IFGameState game,IFStoryState story) {
		return game.succeededActionP(m_action);
	}
	
	public static IFConditionAction loadFromXML(Element root,String path) {
		Element ae = root.getChild("action");
		if (ae!=null) {
			IFConditionAction c = new IFConditionAction();
			c.m_action = IFAction.loadFromXML(ae, path);
			return c;
		}
		return null;
	}

}

package storyengine;

import ifgameengine.IFGameState;

import org.jdom.Element;

public class IFConditionNot extends IFCondition {

	IFCondition m_condition = null;
	
	/**
	 * Constructor for PCG
	 * @param condition
	 */
	public IFConditionNot(IFCondition condition){
		this.m_condition = condition;
	}
	
	public IFConditionNot(){
		//nothing special
	}
	
	public boolean evaluate(IFGameState game,IFStoryState story) {		
		return !m_condition.evaluate(game,story);
	}

	public static IFConditionNot loadFromXML(Element root,String path) {
		IFConditionNot nc = new IFConditionNot();
		
		Element e = root.getChild("condition");
		nc.m_condition = IFCondition.loadFromXML(e, path);
		
		return nc;
	}
}

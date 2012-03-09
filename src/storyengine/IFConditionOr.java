package storyengine;

import ifgameengine.IFGameState;

import java.util.LinkedList;

import org.jdom.Element;

public class IFConditionOr extends IFCondition {

	LinkedList<IFCondition> m_conditions = new LinkedList<IFCondition>();
	
	/**
	 * Constructor for PCG
	 * @param conditions
	 */
	public IFConditionOr(LinkedList<IFCondition> conditions){
		this.m_conditions = conditions;
	}
	
	public IFConditionOr(){
		//Nothing special
	}
	
	public boolean evaluate(IFGameState game,IFStoryState story) {
		for(IFCondition c:m_conditions) if (c.evaluate(game,story)) return true;
		return false;
	}
	
	public static IFConditionOr loadFromXML(Element root,String path) {
		IFConditionOr oc = new IFConditionOr();
		
		for(Object o:root.getChildren("condition")) {
			Element e = (Element)o;
			oc.m_conditions.add(IFCondition.loadFromXML(e, path));
		}
		
		return oc;
	}		

}

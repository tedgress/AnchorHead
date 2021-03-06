package storyengine;

import ifgameengine.IFAction;
import ifgameengine.IFHint;

import java.util.LinkedList;
import java.util.List;

import org.jdom.Element;

public class IFPlotPoint {
	public static final int READY = 0;
	public static final int EXECUTING = 1;
	public static final int SUSPENDED = 2;
	public static final int FINISHED = 3;
	public static final int DENIED = 4;
	
	String m_name = null;
	String m_plot = null;
	IFCondition m_precondition = null; 
	IFCondition m_trigger = null;
	LinkedList<IFAction> m_effects = new LinkedList<IFAction>();
	LinkedList<IFHint> m_hints = new LinkedList<IFHint>();
	int m_endgame = -1;		// if this is different than -1, the game will end in 'm_endgame' cycles
	
	/**
	 * Used for PCG 
	 * 
	 * @param name
	 * @param plot
	 * @param precondition
	 * @param trigger
	 * @param effects
	 * @param hints
	 */
	public IFPlotPoint(String name, String plot, IFCondition precondition, IFCondition trigger, LinkedList<IFAction> effects, LinkedList<IFHint> hints){
		
		if (name == null || plot == null  || trigger == null){
			throw new NullPointerException("Null parameters in plotpoint");
		}
		
		this.m_name = name;
		this.m_plot = plot;
		this.m_precondition = precondition;
		this.m_trigger = trigger;
		
		if (null != effects)
			this.m_effects = effects;
		
		if (null != hints)
			this.m_hints = hints;
		
	}
	private IFPlotPoint() {
		
	}
	
	public static IFPlotPoint loadFromXML(Element root,String path) {
		IFPlotPoint pp = new IFPlotPoint();

		pp.m_name = root.getAttributeValue("name");
		pp.m_plot = root.getAttributeValue("plot");
		
		// preconditions
		Element ppe = root.getChild("preconditions");
		if (ppe!=null) {
			ppe = ppe.getChild("condition");
			pp.m_precondition = IFCondition.loadFromXML(ppe,path);
		}
		
		// trigger:
		Element te = root.getChild("trigger");
		if (te!=null) {
			te = te.getChild("condition");
			pp.m_trigger = IFCondition.loadFromXML(te,path);
		}
		
		// effects:
		Element ee = root.getChild("effects");
		if (ee!=null) {
			for(Object o:ee.getChildren("action")) {
				Element ae = (Element)o;
				IFAction a = IFAction.loadFromXML(ae,path);
				pp.m_effects.add(a);
			}
			for(Object o:ee.getChildren("endgame")) {
				Element ae = (Element)o;
				pp.m_endgame = Integer.parseInt(ae.getAttributeValue("delay"));				
			}			
		}
		
		// hints
		Element he = root.getChild("hints");
		if (he != null){
			for(Object o:he.getChildren("hint")) {
				Element ae = (Element)o;
				
				IFHint hint = IFHint.loadFromXML(ae, path);
				
				pp.m_hints.add(hint);
				

			}
			
		}
				
		return pp;
	}	
	
	public String toString() {
		return "<plotpoint name=\"" + m_name + "\"/>";
	}
	
	public String getName() {
		return m_name;
	}

	public String getPlot() {
		return m_plot;
	}
	
	public LinkedList<IFAction> getEffects() {
		return m_effects;
	}
	
	public int getEndGame() {
		return m_endgame;
	}
	
	public List<IFHint> getHints() {
		return m_hints;
	}
	
	public boolean hasHints(){
		if (!this.getHints().isEmpty())
			return true;
		return false;
	}
	
	public void replaceEffects(LinkedList<IFAction> newEffects){
		//This is a kludge to update the story speech for the quest giver
		
		this.m_effects = newEffects;
		
	}
	
}

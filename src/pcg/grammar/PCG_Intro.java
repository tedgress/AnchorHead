package pcg.grammar;

import pcg.Quest;
import storyengine.IFPlotPoint;

public class PCG_Intro implements PCG_TerminalSymbol {
	
	IFPlotPoint pp = null;
	
	public PCG_Intro(IFPlotPoint plot){
		this.pp = plot;
	}

	@Override
	public void expand() {
		Quest.getQuest().addToQuest(this);

	}

	@Override
	public IFPlotPoint evaluate() {
		
		return this.pp;
	}

	@Override
	public String describe() {
		
		return this.toString();
	}

}

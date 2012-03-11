package pcg.grammar;

import storyengine.IFPlotPoint;

public interface PCG_TerminalSymbol extends Abstract_PCG_Symbol {

	IFPlotPoint evaluate();
	
	public String describe();

}

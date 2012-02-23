package ifgameengine;

import org.jdom.Element;

public class IFHint {
	
	String text = null;
	int subteltyRanking = 0;
	boolean hintGiven = false;
	
	IFHint(String hint_text, int ranking){
		this.text = hint_text;
		this.subteltyRanking = ranking;
	}
	
	public static IFHint loadFromXML(Element root,String path) {
		
		String s = root.getAttributeValue("text");
		
		int subtelty = Integer.valueOf(root.getAttributeValue("subtlety"));
		
		return new IFHint(s, subtelty);
		
	}
	
	public String toString(){
		return this.text;
	}

}

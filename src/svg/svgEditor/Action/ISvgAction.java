package svg.svgEditor.Action;

import org.w3c.dom.events.Event;


public interface ISvgAction {
	
	public void run(String eventType, Event arg0);
	
}
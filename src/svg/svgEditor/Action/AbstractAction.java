package svg.svgEditor.Action;

import org.apache.batik.util.SVGConstants;
import org.w3c.dom.events.Event;

public abstract class AbstractAction implements ISvgAction {
	public void run(String eventType, Event evt) {
		switch(eventType) {
		case SVGConstants.SVG_MOUSEUP_EVENT_TYPE:
			onClick(evt);
		break;
		default:
		break;
		}
		
	}
	
	protected void onClick(Event evt) {}
}

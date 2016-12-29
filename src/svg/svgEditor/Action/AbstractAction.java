package svg.svgEditor.Action;

import org.apache.batik.util.SVGConstants;
import org.w3c.dom.events.Event;

public abstract class AbstractAction implements ISvgAction {
	public void run(String eventType, Event evt) {
		switch(eventType) {
		case SVGConstants.SVG_MOUSEUP_EVENT_TYPE:
			onMouseUp(evt);
		break;
		case SVGConstants.SVG_MOUSEDOWN_EVENT_TYPE:
			onMouseDown(evt);
		break;
		default:
		break;
		}
		
	}
	
	protected void onMouseUp(Event evt) {}
	protected void onMouseDown(Event evt) {}
}

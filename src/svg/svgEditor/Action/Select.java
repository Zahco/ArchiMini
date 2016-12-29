package svg.svgEditor.Action;

import org.w3c.dom.Element;
import org.w3c.dom.events.Event;

import svg.svgEditor.CurrentState;


public class Select extends AbstractAction {
	
	protected void onMouseUp(Event evt) {
		CurrentState.getInstance().setSelectedNode((Element)evt.getTarget());
	}
	
}
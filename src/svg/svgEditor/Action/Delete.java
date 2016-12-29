package svg.svgEditor.Action;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;

public class Delete extends AbstractAction {

	protected void onMouseUp(Event evt) {
		Element node = (Element)evt.getTarget();
		Node parent = node.getParentNode();
		parent.removeChild(node);
	}

}

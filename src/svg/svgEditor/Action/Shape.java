package svg.svgEditor.Action;

import org.w3c.dom.events.Event;
import org.w3c.dom.svg.SVGLocatable;
import org.w3c.dom.svg.SVGMatrix;
import org.apache.batik.dom.events.DOMMouseEvent;
import org.apache.batik.dom.svg.SVGOMPoint;

public abstract class Shape extends AbstractAction {
	
	protected SVGOMPoint startedPoint;
	protected SVGOMPoint endedPoint;
	
	// http://www.programcreek.com/java-api-examples/index.php?api=org.apache.batik.dom.events.DOMMouseEvent
	private SVGOMPoint relativePoint(Event evt) {
		DOMMouseEvent dme = (DOMMouseEvent)evt;
		int nowToX = dme.getClientX();
		int nowToY = dme.getClientY();
		SVGOMPoint pt = new SVGOMPoint(nowToX, nowToY);
		SVGMatrix mat = ((SVGLocatable)evt.getTarget()).getScreenCTM();
		mat = mat.inverse();
		return (SVGOMPoint)pt.matrixTransform(mat);
	}
	
	protected void onMouseDown(Event evt) {
		startedPoint = relativePoint(evt);
	}
	protected void onMouseUp(Event evt) {
		endedPoint = relativePoint(evt);
		draw(evt);
	}
	
	protected void draw(Event evt) {}
	
}
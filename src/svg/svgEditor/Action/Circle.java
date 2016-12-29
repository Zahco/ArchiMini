package svg.svgEditor.Action;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;

public class Circle extends Shape {
	
	protected void draw(Event evt) {
		Document doc = ((Element)evt.getTarget()).getOwnerDocument();
		Element circle = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "circle");
		circle.setAttribute("cx", ""+startedPoint.getX());
		circle.setAttribute("r", ""+ Math.sqrt(
				Math.pow(startedPoint.getX() - endedPoint.getX(), 2) + 
				Math.pow(startedPoint.getY() - endedPoint.getY(), 2)));
		circle.setAttribute("cy", ""+startedPoint.getY());
		((Element)evt.getTarget()).getParentNode().appendChild(circle);
	}
	
}
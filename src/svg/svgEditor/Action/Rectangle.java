package svg.svgEditor.Action;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;



public class Rectangle extends Shape {

	protected void draw(Event evt) {
		Document doc = ((Element)evt.getTarget()).getOwnerDocument();
		Element rect = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "rect");
		rect.setAttribute("x", ""+startedPoint.getX());
		rect.setAttribute("y", ""+startedPoint.getY());
		rect.setAttribute("width", ""+ Math.sqrt(Math.pow(startedPoint.getX() - endedPoint.getX(), 2)));
		rect.setAttribute("height", ""+ Math.sqrt(Math.pow(startedPoint.getY() - endedPoint.getY(), 2)));
		rect.setAttribute("style", "fill:blue;");
		((Element)evt.getTarget()).getParentNode().appendChild(rect);
	}
	
}
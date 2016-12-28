package svg.svgEditor;

import java.io.File;

import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.Element;

public class CurrentState {
	protected static CurrentState instance;
	
	protected static Element selectedNode;
	protected static JSVGCanvas svgCanvas;
	protected static File file;
	
	synchronized public static CurrentState getInstance() {
		if (instance == null) {
			instance = new CurrentState();
		}
		return instance;
	}
	
	protected CurrentState() {}
	
	synchronized public Element getSelectedNode() {
		return selectedNode;
	}
	
	synchronized public void setSelectedNode(Element item) {
		selectedNode = item;
	}
	
	synchronized public JSVGCanvas getSvgCanvas() {
		return svgCanvas;
	}
	
	synchronized public void setSvgCanvas(JSVGCanvas item) {
		svgCanvas = item;
	}
	
	synchronized public File getFile() {
		return file;
	}
	
	synchronized public void setFile(File item) {
		file = item;
	}
}

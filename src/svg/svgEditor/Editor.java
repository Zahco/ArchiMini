 
package svg.svgEditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.svg2svg.SVGTranscoder;
import org.apache.batik.util.SVGConstants;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import svg.svgEditor.Action.Circle;
import svg.svgEditor.Action.Delete;
import svg.svgEditor.Action.ISvgAction;
import svg.svgEditor.Action.Rectangle;
import svg.svgEditor.Action.Select;


public class Editor {
	
	private class ActionButton {
		private String title;
		private ISvgAction action;
		private JButton button;
		
		public ActionButton(String title, ISvgAction action) {
			this.title = title;
			this.action = action;
		}
		
		public String getTitle() {
			return title;
		}
		public ISvgAction getAction() {
			return action;
		}
		public JButton getButton() {
			return button;
		}
		
		public void setButton(JButton button) {
			this.button = button;
		}
	}
	
	private JFrame mainFrame;
	private JSVGCanvas svgCanvas;
	
	private JButton save;
	private JButton load;
	private JLabel loadStatus;
	
	private ISvgAction currentMode;
	private List<ActionButton> actionButtons;
	
	private String[] eventsToCatch = {
		SVGConstants.SVG_MOUSEUP_EVENT_TYPE,
		SVGConstants.SVG_MOUSEDOWN_EVENT_TYPE,
	};
	
	// CONSTRUCTEUR
	public Editor() {
		createModel();
        createView();
        placeComponents();
        createController();
    }
	
	// COMMANDE
	public void display() {
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
	}
	
	// OUTILS
	private void createModel() {
		currentMode = new Select();
		actionButtons = new ArrayList<ActionButton>();
		actionButtons.add(new ActionButton("Select", new Select()));
		actionButtons.add(new ActionButton("Circle", new Circle()));
		actionButtons.add(new ActionButton("Rectangle", new Rectangle()));
		actionButtons.add(new ActionButton("Delete", new Delete()));
	}
	
	private void createView() {
		mainFrame = new JFrame("Editor");
		mainFrame.setPreferredSize(new Dimension(800, 800));
		svgCanvas = new JSVGCanvas();
		svgCanvas.setAutoscrolls(true);
		svgCanvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		save = new JButton("Save");
		load = new JButton("Open");
		loadStatus = new JLabel();
		
		for (ActionButton actionButton : actionButtons) {
			actionButton.setButton(new JButton(actionButton.getTitle()));
		}
	}
	
	private void placeComponents() {
		JPanel north = new JPanel(); {
			north.add(save);
			north.add(load);
			north.add(loadStatus);
			for (ActionButton actionButton : actionButtons) {
				north.add(actionButton.getButton());
			}
		}
		mainFrame.add(north, BorderLayout.NORTH);
		JPanel center = new JPanel(); {
			center.add(svgCanvas);
		}
		mainFrame.add(center, BorderLayout.CENTER);
	}
	
	// https://xmlgraphics.apache.org/batik/using/swing.html
	private void createController() {
		CurrentState.getInstance().setSvgCanvas(svgCanvas);
		createSVGController();
		
		load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc = new JFileChooser(".");
                int choice = fc.showOpenDialog(mainFrame);
                if (choice == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    CurrentState.getInstance().setFile(f);
                    svgCanvas.setURI(f.toURI().toString());
                }
            }
        });
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SVGTranscoder transcoder = new SVGTranscoder();
				File file = CurrentState.getInstance().getFile();
				TranscoderInput transcoderInput = new TranscoderInput(svgCanvas.getSVGDocument());
				try {
					TranscoderOutput transcoderOutput = new TranscoderOutput(new FileWriter(file));
					transcoder.transcode(transcoderInput, transcoderOutput);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		for (ActionButton actionButton : actionButtons) {
			final ActionButton actionButtonFinal = actionButton;
			actionButton.getButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					currentMode = actionButtonFinal.getAction();
				}
			});
		}
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void createSVGController() {
		svgCanvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter() {
            public void gvtBuildStarted(GVTTreeBuilderEvent e) {
            	loadStatus.setText("Build Started...");
            }
            public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
            	loadStatus.setText("Build Done.");
                mainFrame.pack();
            }
        });

        svgCanvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
            public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
            	loadStatus.setText("Rendering Started...");
            }
            public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
            	loadStatus.setText("");
            	EventTarget root = (EventTarget)svgCanvas.getSVGDocument().getRootElement();
            	for (String eventType : eventsToCatch) {
            		final String eventTypeFinal = eventType;
            		root.addEventListener(eventTypeFinal, new EventListener() {
            			public void handleEvent(Event evt) {
            				final Event event = evt;
            				currentMode.run(eventTypeFinal, event);
            			}
            		}, false);
            	}
            }
        });
	}
	
	// POINT D'ENTREE    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Editor().display();
            }
        });
    }
}

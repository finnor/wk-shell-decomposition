package edu.uab.mukhtarlab.wkshelldecomposition.internal.view;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;

import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.util.swing.OpenBrowser;

/**
 * The about panel that shows information on the app
 */
public class AboutDialog extends JDialog {

	private final OpenBrowser openBrowser;

	private JEditorPane mainContainer;
	private JPanel buttonPanel;


	public AboutDialog(final CySwingApplication swingApplication,
					   final OpenBrowser openBrowser) {
		super(swingApplication.getJFrame(), "About", false);
		this.openBrowser = openBrowser;

		setResizable(false);
		getContentPane().add(getMainContainer(), BorderLayout.CENTER);
		getContentPane().add(getButtonPanel(), BorderLayout.SOUTH);
		pack();
	}

	private JEditorPane getMainContainer() {
		if (mainContainer == null) {
			mainContainer = new JEditorPane();
			mainContainer.setMargin(new Insets(10, 10, 10, 10));
			mainContainer.setEditable(false);
			mainContainer.setEditorKit(new HTMLEditorKit());
			mainContainer.addHyperlinkListener(new HyperlinkAction());

			String text = "<html><body>" +
						  "<P align=center><b>wk-shell-decomposition</b><BR>" +

						  "University of Alabama at Birmingham<BR>" +
						  "</P></body></html>";

			mainContainer.setText(text);
			
			mainContainer.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					switch (e.getKeyCode()) {
						case KeyEvent.VK_ENTER:
						case KeyEvent.VK_ESCAPE:
							dispose();
							break;
					}
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
				}
			});
		}

		return mainContainer;
	}

	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			
			JButton button = new JButton("Close");
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			button.setAlignmentX(CENTER_ALIGNMENT);
			
			buttonPanel.add(button);
		}
		
		return buttonPanel;
	}
	
	private class HyperlinkAction implements HyperlinkListener {
		@Override
		public void hyperlinkUpdate(HyperlinkEvent event) {
			if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				openBrowser.openURL(event.getURL().toString());
			}
		}
	}
}

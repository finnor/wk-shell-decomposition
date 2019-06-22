package edu.uab.mukhtarlab.wkshelldecomposition.internal.action;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.ActionEnableSupport;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.util.swing.OpenBrowser;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.view.AboutDialog;

/**
 * Shows the about popup
 */
public class AboutAction extends AbstractAppAction {

	private static final long serialVersionUID = -8445425993916988045L;

	private AboutDialog aboutDialog;

	public AboutAction(String name, CyServiceRegistrar serviceRegistrar) {
		super(name, ActionEnableSupport.ENABLE_FOR_ALWAYS, serviceRegistrar);
		setPreferredMenu("Apps.wk-shell decomposition");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//display about box
		synchronized (this) {
			if (aboutDialog == null)
				aboutDialog = new AboutDialog(swingApplication, serviceRegistrar.getService(OpenBrowser.class));

			if (!aboutDialog.isVisible()) {
				aboutDialog.setLocationRelativeTo(null);
				aboutDialog.setVisible(true);
			}
		}
		
		aboutDialog.toFront();
	}
}

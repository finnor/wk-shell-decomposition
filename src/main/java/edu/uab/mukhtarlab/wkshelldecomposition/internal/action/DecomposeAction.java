package edu.uab.mukhtarlab.wkshelldecomposition.internal.action;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.ActionEnableSupport;
import org.cytoscape.service.util.CyServiceRegistrar;

/**
 * Performs the decomposition action
 */
public class DecomposeAction extends AbstractAppAction {


	private final CyServiceRegistrar registrar;

	public DecomposeAction(
			String title,
			CyServiceRegistrar registrar
	) {
		super(title, ActionEnableSupport.ENABLE_FOR_NETWORK, registrar);
		this.registrar = registrar;
	}

	/**
	 * This method is called when the user clicks Analyze.
	 *
	 * @param event Click of the analyzeButton on the MainPanel.
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {

	}
}

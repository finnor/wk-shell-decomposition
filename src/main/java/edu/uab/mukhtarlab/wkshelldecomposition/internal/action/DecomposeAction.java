package edu.uab.mukhtarlab.wkshelldecomposition.internal.action;

import java.awt.event.ActionEvent;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.task.DecomposeTaskFactory;
import org.cytoscape.model.*;
import org.cytoscape.application.swing.ActionEnableSupport;
import org.cytoscape.service.util.CyServiceRegistrar;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.model.Parameters;
import org.cytoscape.work.swing.DialogTaskManager;

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
		final CyNetwork network = applicationManager.getCurrentNetwork();
		final Parameters params =
				getMainPanel() != null ? getMainPanel().getCurrentParamsCopy() : new Parameters();

		execute(network, params);
	}

	private void execute(CyNetwork network, Parameters currentParams) {
		DecomposeTaskFactory tf = new DecomposeTaskFactory(network, currentParams);
		registrar.getService(DialogTaskManager.class).execute(tf.createTaskIterator());
	}
}

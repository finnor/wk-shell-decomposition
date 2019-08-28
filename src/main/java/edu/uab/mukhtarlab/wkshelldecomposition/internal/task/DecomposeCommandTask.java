package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.ContainsTunables;
import org.cytoscape.work.TaskMonitor;

/**
 * Performs the weighted k-shell decomposition
 */

public class DecomposeCommandTask extends AbstractTask {

	@ContainsTunables

	private final CyServiceRegistrar registrar;
	
	public DecomposeCommandTask(
			CyServiceRegistrar registrar
	) {
		this.registrar = registrar;
	}
	
	@Override
	public void run(TaskMonitor tm) throws Exception {
		CyNetwork network = registrar.getService(CyApplicationManager.class).getCurrentNetwork();
		CyNetworkView nView = registrar.getService(CyApplicationManager.class).getCurrentNetworkView();

		if (network == null) {
			throw new RuntimeException("No network is selected.");
		}

		DecomposeTask decomposeTask = new DecomposeTask(network, nView);
		insertTasksAfterCurrentTask(decomposeTask);
	}
}

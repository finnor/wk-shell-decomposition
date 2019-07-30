package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;

import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.ContainsTunables;
import org.cytoscape.work.TaskMonitor;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.action.DecomposeAction;

/**
 * Performs the weighted k-shell decomposition
 */

public class DecomposeCommandTask extends AbstractTask {

	@ContainsTunables
	
	private final DecomposeAction action;
	private final CyServiceRegistrar registrar;
	
	public DecomposeCommandTask(
			DecomposeAction action,
			CyServiceRegistrar registrar
	) {
		this.action = action;
		this.registrar = registrar;
	}
	
	@Override
	public void run(TaskMonitor tm) throws Exception {

	}
}

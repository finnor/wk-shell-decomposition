package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;

import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.ContainsTunables;
import org.cytoscape.work.TaskMonitor;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.action.DecomposeAction;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.model.Parameters;

/**
 * Performs the weighted k-shell decomposition
 */

public class DecomposeCommandTask extends AbstractTask {

	@ContainsTunables
	public Parameters params = new Parameters();
	
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

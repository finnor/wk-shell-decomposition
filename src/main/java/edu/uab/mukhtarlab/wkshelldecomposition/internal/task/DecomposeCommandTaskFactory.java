package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.action.DecomposeAction;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;

/**
 * Factory to create the task that will perform the weighted k-shell decomposition
 */

public class DecomposeCommandTaskFactory implements TaskFactory {

	private final DecomposeAction action;
	private final CyServiceRegistrar registrar;
	
	public DecomposeCommandTaskFactory(
			DecomposeAction action,
			CyServiceRegistrar registrar
	) {
		this.action = action;
		this.registrar = registrar;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(new DecomposeCommandTask(action, registrar));
	}

	@Override
	public boolean isReady() {
		return true;
	}
}

package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.model.Parameters;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;

/**
 * Factory to create the task that will perform the weighted k-shell decomposition
 */

public class DecomposeTaskFactory implements TaskFactory {

	private final CyNetwork network;
	private final Parameters parameters;

	public DecomposeTaskFactory(
			CyNetwork network,
			Parameters parameters
	) {
		this.network = network;
		this.parameters = parameters;
	}

	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(new DecomposeTask(network));
	}

	@Override
	public boolean isReady() {
		return true;
	}
}

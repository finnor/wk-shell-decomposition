package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;

/**
 * Factory to create the task that will perform the weighted k-shell decomposition
 */

public class DecomposeTaskFactory implements TaskFactory {

	private final CyNetwork network;

	public DecomposeTaskFactory(
			CyNetwork network
	) {
		this.network = network;
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

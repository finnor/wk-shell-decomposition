package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;

/**
 * Factory to create the task that will perform the weighted k-shell decomposition
 */

public class DecomposeTaskFactory implements TaskFactory {

	private final CyNetwork network;
	private final CyNetworkView nView;

	public DecomposeTaskFactory(
			CyNetwork network,
			CyNetworkView nView
	) {
		this.network = network;
		this.nView = nView;
	}

	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(new DecomposeTask(network, nView));
	}

	@Override
	public boolean isReady() {
		return true;
	}
}

package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.ObservableTask;
import org.cytoscape.work.TaskMonitor;


/**
 * Performs the weighted k-shell decomposition
 */

public class DecomposeTask implements ObservableTask {

	private boolean cancelled;
	private CyNetwork network;


	public DecomposeTask(
			CyNetwork network
	) {
		this.network = network;
	}

	/**
	 * Run App (Both score and find steps).
	 */
	@Override
	public void run(TaskMonitor tm) throws Exception {

	}

	@Override
	public void cancel() {
		cancelled = true;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getResults(Class type) {
		return null;
	}
}

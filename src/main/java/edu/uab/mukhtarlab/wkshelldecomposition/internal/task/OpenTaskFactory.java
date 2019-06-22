package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.action.DecomposeAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;

/**
 * Factory to create the task that will open the main panel
 */

public class OpenTaskFactory implements TaskFactory {

	private final CySwingApplication swingApplication;
	private final CyServiceRegistrar registrar;
	private final DecomposeAction decomposeAction;
	
	public OpenTaskFactory(final CySwingApplication swingApplication,
						   final CyServiceRegistrar registrar,
						   final DecomposeAction decomposeAction) {
		this.swingApplication = swingApplication;
		this.registrar = registrar;
		this.decomposeAction = decomposeAction;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(new OpenTask(swingApplication, registrar, decomposeAction));
	}

	@Override
	public boolean isReady() {
		return true;
	}
}

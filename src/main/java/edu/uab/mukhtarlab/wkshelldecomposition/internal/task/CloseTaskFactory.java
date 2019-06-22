package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;

import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;


/**
 * Factory to create the task that will close the main panel
 */

public class CloseTaskFactory implements TaskFactory {
	
	private final CySwingApplication swingApplication;
	private final CyServiceRegistrar registrar;
	
	public CloseTaskFactory(
			CySwingApplication swingApplication,
			CyServiceRegistrar registrar
	) {
		this.swingApplication = swingApplication;
		this.registrar = registrar;
	}

	@Override
	public TaskIterator createTaskIterator() {
		final TaskIterator taskIterator = new TaskIterator();


		taskIterator.append(new CloseTask(swingApplication, registrar));
		
		return taskIterator;
	}

	@Override
	public boolean isReady() {
		return true;
	}
}

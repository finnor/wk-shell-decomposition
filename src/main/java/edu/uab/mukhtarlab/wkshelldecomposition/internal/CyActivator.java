package edu.uab.mukhtarlab.wkshelldecomposition.internal;

import static org.cytoscape.work.ServiceProperties.COMMAND;
import static org.cytoscape.work.ServiceProperties.COMMAND_NAMESPACE;
import static org.cytoscape.work.ServiceProperties.COMMAND_DESCRIPTION;
import static org.cytoscape.work.ServiceProperties.COMMAND_LONG_DESCRIPTION;

import java.util.Properties;

import org.cytoscape.application.swing.CyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.work.TaskFactory;
import org.osgi.framework.BundleContext;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.action.DecomposeAction;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.task.DecomposeCommandTaskFactory;

/**
 * CyActivator for app
 */

public class CyActivator extends AbstractCyActivator {

	private CyServiceRegistrar registrar;
	
	@Override
	@SuppressWarnings("unchecked")
	public void start(BundleContext bc) {
		registrar = getService(bc, CyServiceRegistrar.class);
		
		CySwingApplication swingApp = getService(bc, CySwingApplication.class);
		
		DecomposeAction decomposeAction = new DecomposeAction("wk-shell decomposition", registrar);

		registerService(bc, decomposeAction, CyAction.class);
		
		// Commands
		{
			DecomposeCommandTaskFactory factory = new DecomposeCommandTaskFactory(decomposeAction, registrar);
			Properties props = new Properties();
			props.setProperty(COMMAND, "decompose");
			props.setProperty(COMMAND_NAMESPACE, "wkshell");
			props.setProperty(COMMAND_DESCRIPTION, "Finds weighted k-shells in a network.");
			props.setProperty(COMMAND_LONG_DESCRIPTION, "Analyzes the network for weighted k-shells.");
			
			registerService(bc, factory, TaskFactory.class, props);
		}
	}
	
	@Override
	public void shutDown() {
		super.shutDown();
	}
}

package edu.uab.mukhtarlab.wkshelldecomposition.internal;

import static org.cytoscape.work.ServiceProperties.INSERT_SEPARATOR_AFTER;
import static org.cytoscape.work.ServiceProperties.MENU_GRAVITY;
import static org.cytoscape.work.ServiceProperties.PREFERRED_MENU;
import static org.cytoscape.work.ServiceProperties.TITLE;
import static org.cytoscape.work.ServiceProperties.COMMAND;
import static org.cytoscape.work.ServiceProperties.COMMAND_NAMESPACE;
import static org.cytoscape.work.ServiceProperties.COMMAND_DESCRIPTION;
import static org.cytoscape.work.ServiceProperties.COMMAND_LONG_DESCRIPTION;

import java.awt.Component;
import java.util.Properties;

import org.cytoscape.application.swing.CyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.work.TaskFactory;
import org.osgi.framework.BundleContext;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.action.AboutAction;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.action.DecomposeAction;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.task.CloseTaskFactory;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.task.OpenTaskFactory;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.task.DecomposeCommandTaskFactory;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.view.MainPanel;

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
		
		closeAppPanels(swingApp);
		
		DecomposeAction decomposeAction = new DecomposeAction("Decompose Network", registrar);
		AboutAction aboutAction = new AboutAction("About", registrar);

		registerService(bc, aboutAction, CyAction.class);
		registerAllServices(bc, decomposeAction);
		
		{
			OpenTaskFactory factory = new OpenTaskFactory(swingApp, registrar, decomposeAction);
			Properties props = new Properties();
			props.setProperty(PREFERRED_MENU, "Apps.wk-shell decomposition");
			props.setProperty(TITLE, "Open App");
			props.setProperty(MENU_GRAVITY, "1.0");
			
			registerService(bc, factory, TaskFactory.class, props);
		}
		{
			CloseTaskFactory factory = new CloseTaskFactory(swingApp, registrar);
			Properties props = new Properties();
			props.setProperty(PREFERRED_MENU, "Apps.wk-shell decomposition");
			props.setProperty(TITLE, "Close App");
			props.setProperty(MENU_GRAVITY, "2.0");
			props.setProperty(INSERT_SEPARATOR_AFTER, "true");
			
			registerService(bc, factory, TaskFactory.class, props);
		}
		
		// Commands
		{
			DecomposeCommandTaskFactory factory = new DecomposeCommandTaskFactory(decomposeAction, registrar);
			Properties props = new Properties();
			props.setProperty(COMMAND, "cluster");
			props.setProperty(COMMAND_NAMESPACE, "wkshell");
			props.setProperty(COMMAND_DESCRIPTION, "Finds clusters in a network.");
			props.setProperty(COMMAND_LONG_DESCRIPTION, "Analyzes the specified network in order to find clusters.");
			
			registerService(bc, factory, TaskFactory.class, props);
		}
	}
	
	@Override
	public void shutDown() {
		super.shutDown();
	}
	
	private void closeAppPanels(CySwingApplication swingApp) {
		
		// Now, unregister main panels...
		final CytoPanel ctrlPanel = swingApp.getCytoPanel(CytoPanelName.WEST);
		
		if (ctrlPanel != null) {
			int count = ctrlPanel.getCytoPanelComponentCount();
	
			for (int i = 0; i < count; i++) {
				try {
					final Component comp = ctrlPanel.getComponentAt(i);
					
					// Compare the class names to also get panels that may have been left by old versions of App
					if (comp.getClass().getName().equals(MainPanel.class.getName()))
						registrar.unregisterAllServices(comp);
				} catch (Exception e) {
				}
			}
		}
	}
}

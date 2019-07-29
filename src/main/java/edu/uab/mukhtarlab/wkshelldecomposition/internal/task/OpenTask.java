package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;

import java.util.Properties;
import java.awt.Component;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.action.DecomposeAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.view.MainPanel;

/**
 * Opens the main panel
 */

public class OpenTask implements Task {

	private final CySwingApplication swingApplication;
	private final CyServiceRegistrar registrar;
	private final DecomposeAction decomposeAction;
	
	public OpenTask(final CySwingApplication swingApplication,
					final CyServiceRegistrar registrar,
					final DecomposeAction decomposeAction) {
		this.swingApplication = swingApplication;
		this.registrar = registrar;
		this.decomposeAction = decomposeAction;
	}

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		// Display MainPanel in left cytopanel
		synchronized (this) {
			MainPanel mainPanel;

			// First we must make sure that the app is not already open
			if (getMainPanel() == null) {
				mainPanel = new MainPanel(swingApplication, registrar, decomposeAction);

				registrar.registerService(mainPanel, CytoPanelComponent.class, new Properties());
				decomposeAction.updateEnableState();
			} else {
				mainPanel = getMainPanel();
			}

			if (mainPanel != null) {
				CytoPanel cytoPanel = swingApplication.getCytoPanel(CytoPanelName.WEST);
				int index = cytoPanel.indexOfComponent(mainPanel);
				cytoPanel.setSelectedIndex(index);
			}
		}
	}

	private MainPanel getMainPanel() {
		CytoPanel cytoPanel = swingApplication.getCytoPanel(CytoPanelName.WEST);
		int count = cytoPanel.getCytoPanelComponentCount();

		for (int i = 0; i < count; i++) {
			final Component comp = cytoPanel.getComponentAt(i);

			if (comp instanceof MainPanel)
				return (MainPanel) comp;
		}

		return null;
	}

	@Override
	public void cancel() {
		// Do nothing
	}
}

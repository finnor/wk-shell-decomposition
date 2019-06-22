package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.view.MainPanel;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import java.awt.*;

/**
 * Closes the main panel
 */
public class CloseTask implements Task {

	private final CySwingApplication swingApplication;
	private final CyServiceRegistrar registrar;
	
	public CloseTask(
			CySwingApplication swingApplication,
			CyServiceRegistrar registrar
	) {
		this.swingApplication = swingApplication;
		this.registrar = registrar;
	}

	@Override
	public void run(final TaskMonitor taskMonitor) throws Exception {
		MainPanel mainPanel = getMainPanel();

		if (mainPanel != null)
			registrar.unregisterService(mainPanel, CytoPanelComponent.class);
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

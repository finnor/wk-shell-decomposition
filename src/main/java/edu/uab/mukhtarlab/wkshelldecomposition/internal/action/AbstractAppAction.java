package edu.uab.mukhtarlab.wkshelldecomposition.internal.action;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.view.MainPanel;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.view.ResultsPanel;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkViewManager;

public abstract class AbstractAppAction extends AbstractCyAction {

	private static final long serialVersionUID = 844755168181859513L;

	protected final CySwingApplication swingApplication;
	protected final CyApplicationManager applicationManager;
	protected final CyNetworkViewManager netViewManager;
	protected final CyServiceRegistrar serviceRegistrar;

	public AbstractAppAction(String name, String enableFor, CyServiceRegistrar serviceRegistrar) {
		super(name, serviceRegistrar.getService(CyApplicationManager.class), enableFor,
				serviceRegistrar.getService(CyNetworkViewManager.class));
		
		this.applicationManager = serviceRegistrar.getService(CyApplicationManager.class);
		this.swingApplication = serviceRegistrar.getService(CySwingApplication.class);
		this.netViewManager = serviceRegistrar.getService(CyNetworkViewManager.class);
		this.serviceRegistrar = serviceRegistrar;
	}
	
	/**
	 * @return Cytoscape's control panel
	 */
	protected CytoPanel getControlCytoPanel() {
		return swingApplication.getCytoPanel(CytoPanelName.WEST);
	}

	/**
	 * @return Cytoscape's results panel
	 */
	protected CytoPanel getResultsCytoPanel() {
		return swingApplication.getCytoPanel(CytoPanelName.EAST);
	}

	/**
	 * @return The main panel of the app if it is opened, and null otherwise
	 */
	protected MainPanel getMainPanel() {
		CytoPanel cytoPanel = getControlCytoPanel();
		int count = cytoPanel.getCytoPanelComponentCount();

		for (int i = 0; i < count; i++) {
			if (cytoPanel.getComponentAt(i) instanceof MainPanel)
				return (MainPanel) cytoPanel.getComponentAt(i);
		}

		return null;
	}

	protected ResultsPanel getResultsPanel() {
		CytoPanel cytoPanel = getResultsCytoPanel();
		int count = cytoPanel.getCytoPanelComponentCount();

		for (int i = 0; i < count; i++) {
			if (cytoPanel.getComponentAt(i) instanceof ResultsPanel)
				return (ResultsPanel) cytoPanel.getComponentAt(i);
		}

		return null;
	}

	/**
	 * @return true if the app is opened and false otherwise
	 */
	protected boolean isOpened() {
		return getMainPanel() != null;
	}
}

package edu.uab.mukhtarlab.wkshelldecomposition.internal.action;

import java.awt.event.ActionEvent;
import java.util.Properties;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.model.Result;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.task.DecomposeTaskFactory;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.view.ResultsPanel;
import org.cytoscape.application.swing.*;
import org.cytoscape.model.*;
import org.cytoscape.service.util.CyServiceRegistrar;

import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.FinishStatus;
import org.cytoscape.work.ObservableTask;
import org.cytoscape.work.TaskObserver;
import org.cytoscape.work.swing.DialogTaskManager;

/**
 * Performs the decomposition action
 */
public class DecomposeAction extends AbstractAppAction {


	private final CyServiceRegistrar registrar;
	public ResultsPanel resultsPanel;

	public DecomposeAction(
			String title,
			CyServiceRegistrar registrar
	) {
		super(title, ActionEnableSupport.ENABLE_FOR_NETWORK, registrar);
		this.registrar = registrar;
		setPreferredMenu("Apps");

	}

	/**
	 * Runs when the menu item is clicked
	 *
	 * @param event Click of the menu button for decomposition
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		final CyNetwork network = applicationManager.getCurrentNetwork();
		CyNetworkView nView = applicationManager.getCurrentNetworkView();

		TaskObserver taskObserver = new TaskObserver() {
			@Override
			public void taskFinished(ObservableTask task) {
				/*Result result = null;
				if (task.getResultClasses().contains(Result.class))
					result = task.getResults(Result.class);

				resultsPanel = new ResultsPanel(registrar, result);
				registrar.registerService(resultsPanel, CytoPanelComponent.class, new Properties());


				if (resultsPanel != null) {
					CytoPanel cytoPanel = swingApplication.getCytoPanel(CytoPanelName.EAST);
					int index = cytoPanel.indexOfComponent(resultsPanel);
					cytoPanel.setSelectedIndex(index);
					if (cytoPanel.getState() == CytoPanelState.HIDE)
						cytoPanel.setState(CytoPanelState.DOCK);
				}*/
			}

			@Override
			public void allFinished(FinishStatus finishStatus) {
				//
			}
		};

		execute(network, nView, taskObserver);
	}

	private void execute(CyNetwork network, CyNetworkView nView, TaskObserver taskObserver) {
		DecomposeTaskFactory tf = new DecomposeTaskFactory(network, nView);
		registrar.getService(DialogTaskManager.class).execute(tf.createTaskIterator(), taskObserver);
	}
}

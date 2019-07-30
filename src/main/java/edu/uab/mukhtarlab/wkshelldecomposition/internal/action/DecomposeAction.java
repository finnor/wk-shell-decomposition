package edu.uab.mukhtarlab.wkshelldecomposition.internal.action;

import java.awt.event.ActionEvent;
import java.util.Properties;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.model.Result;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.task.DecomposeTaskFactory;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.view.ResultsPanel;
import org.cytoscape.application.swing.*;
import org.cytoscape.model.*;
import org.cytoscape.service.util.CyServiceRegistrar;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.model.Parameters;
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

	}

	/**
	 * This method is called when the user clicks Analyze.
	 *
	 * @param event Click of the analyzeButton on the MainPanel.
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		final CyNetwork network = applicationManager.getCurrentNetwork();
		final Parameters params =
				getMainPanel() != null ? getMainPanel().getCurrentParamsCopy() : new Parameters();

		TaskObserver taskObserver = new TaskObserver() {

			Result result = null;

			@Override
			public void taskFinished(ObservableTask task) {
				//TODO HOW TO PASS RESULTS INTO THIS
				//In instantiating or have a method to update
				if (task.getResultClasses().contains(Result.class))
					result = task.getResults(Result.class);

				if (getResultsPanel() == null) {
					resultsPanel = new ResultsPanel(registrar);

					registrar.registerService(resultsPanel, CytoPanelComponent.class, new Properties());
				} else {
					resultsPanel = getResultsPanel();
				}

				if (resultsPanel != null) {
					resultsPanel.setResult(result);
					CytoPanel cytoPanel = swingApplication.getCytoPanel(CytoPanelName.EAST);
					int index = cytoPanel.indexOfComponent(resultsPanel);
					cytoPanel.setSelectedIndex(index);
					if (cytoPanel.getState() == CytoPanelState.HIDE)
						cytoPanel.setState(CytoPanelState.DOCK);
				}
			}

			@Override
			public void allFinished(FinishStatus finishStatus) {
				//
			}
		};

		execute(network, params, taskObserver);
	}

	private void execute(CyNetwork network, Parameters currentParams, TaskObserver taskObserver) {
		DecomposeTaskFactory tf = new DecomposeTaskFactory(network, currentParams);
		registrar.getService(DialogTaskManager.class).execute(tf.createTaskIterator(), taskObserver);
	}
}

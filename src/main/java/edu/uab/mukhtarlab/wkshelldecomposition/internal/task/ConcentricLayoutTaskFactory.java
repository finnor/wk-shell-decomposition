package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;


import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;

/**
 * Applies ConcentricLayout to the given CyNetworkView
 */
public class ConcentricLayoutTaskFactory implements TaskFactory {
    private CyNetwork network;
    private CyNetworkView nView;

    public ConcentricLayoutTaskFactory(CyNetwork network, CyNetworkView nView) {
        this.network = network;
        this.nView = nView;
    }

    @Override
    public TaskIterator createTaskIterator() {
        return new TaskIterator(new ConcentricLayoutTask(network, nView));
    }

    @Override
    public boolean isReady() {
        return true;
    }


    public boolean isReady(CyNetworkView view) {
        return view != null;
    };
}
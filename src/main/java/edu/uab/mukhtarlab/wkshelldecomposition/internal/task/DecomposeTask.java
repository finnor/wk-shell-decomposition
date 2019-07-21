package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;

import org.cytoscape.model.*;
import org.cytoscape.work.ObservableTask;
import org.cytoscape.work.TaskMonitor;

import java.util.Collection;


/**
 * Performs the weighted k-shell decomposition
 */

public class DecomposeTask implements ObservableTask {

	private boolean cancelled;
	private CyNetwork network;


	public DecomposeTask(
			CyNetwork network
	) {
		this.network = network;
	}

	/**
	 * Run App (Both score and find steps).
	 */
	@Override
	public void run(TaskMonitor tm) throws Exception {
		//Iterate over the network and remove k=1; keep reiterating until no k=1
		//Do this for all k until you reach a k where all the remaining network = k
		/*int k=1;
		boolean isLastShell = false;
		boolean hasMoreNodes = true;
		//Iterate over network until you reach a k-core where all the remaining nodes have that k
		while(!isLastShell) {
			isLastShell = true;
			//clone network

			//iterate over the network until you don't have to remove anymore nodes
			while (hasMoreNodes) {

			}


			k++;
		}*/
		CyTable table = network.getDefaultNodeTable();

		//Normalize weights; calculate mean weight and divide each weight by the mean
		//Then divide each resulting weight from their minimum

		if(table.getColumn("kShell")==null) {
			table.createColumn("kShell", Integer.class, false);
		}
		if(table.getColumn("isPruned")==null) {
			table.createColumn("isPruned", Boolean.class, false);
		}
		for(CyRow row : table.getAllRows()) {
			row.set("kShell", 0);
			row.set("isPruned", false);
		}
		int kShell = 0;
		int k = 0;
		int degree = 0;
		int sumWeights = 0;
		String primaryNodeKey = table.getPrimaryKey().getName();
		String weightAttribute = "test";
		CyNode node = null;
		CyRow lastRow = null;
		Collection<CyRow> remainingRows = null;
		boolean done = false;
		boolean nodeWasPruned = true;
		int i = 0;
		//Keep iterating until all rows are assigned to a shell
		while(!done) {
			i++;
			done = true;
			//Have to keep iterating on a shell until no more nodes are pruned at this level
			nodeWasPruned = true;
			while(nodeWasPruned) {
				nodeWasPruned = false;
				remainingRows = table.getMatchingRows("isPruned", false);
				//done = remainingRows.isEmpty();
				//For each remaining node, see if it belongs to this k-shell
				for (CyRow row : remainingRows) {
					node = network.getNode(row.get(primaryNodeKey, Long.class));
					//calculate k
					//Get list of edges
					//Calculate based on those not pruned yet
					//for iterating rows, use that matching thing and only pull not soft deleted ones
					degree = 0;
					sumWeights = 0;
					for(CyEdge edge : network.getAdjacentEdgeIterable(node, CyEdge.Type.ANY)) {
						if(!network.getRow(edge.getSource()).get("isPruned", Boolean.class) && !network.getRow(edge.getTarget()).get("isPruned", Boolean.class)) {
							degree++;
							sumWeights = 1;// += network.getRow(edge).get(weightAttribute, Double.class);
						}

					}
					k = degree;//(int) Math.round(Math.sqrt(degree * sumWeights));

					//if weighted degree is less than or equal to this k-shell, assign to this k-shell and prune
					if(kShell>=k) {
						row.set("kShell", kShell);
						//delete node
						// or maybe collect all nodes to delete and delete them at end
						row.set("isPruned", true);
						nodeWasPruned = true;
					} else {
						done = false;
					}
				}
			}
			kShell++; //?
		}
		table.deleteColumn("isPruned");

		//TODO normalize weights
		//Use parameters for alpha and beta
		//Get weight column
		//show results
	}

	@Override
	public void cancel() {
		cancelled = true;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getResults(Class type) {
		return null;
	}
}

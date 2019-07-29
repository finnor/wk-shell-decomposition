package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.model.Parameters;
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
	private Parameters params;


	public DecomposeTask(
			CyNetwork network,
			Parameters params
	) {
		this.network = network;
		this.params = params;
	}

	/**
	 * Run App (Both score and find steps).
	 */
	@Override
	public void run(TaskMonitor tm) throws Exception {


		//Normalize weights; calculate mean weight and divide each weight by the mean
		//Then divide each resulting weight from their minimum
		CyTable edgeTable = network.getDefaultEdgeTable();
		if(edgeTable.getColumn("_wksdc_normalizedWeight")==null) {
			edgeTable.createColumn("_wksdc_normalizedWeight", Integer.class, false);
		}

		String weightAttributeName = params.getWeightColumn();
		CyColumn weightColumn = (weightAttributeName!=null) ? edgeTable.getColumn(weightAttributeName) : null;

		//Weight Normalization, do i need to do this for each partition
		double sumWeights = 0;
		Object weight = null;
		double minWeight = Double.MAX_VALUE;
		if(weightColumn!=null) {

			//Calculate mean weight
			for (CyRow row : edgeTable.getAllRows()) {
				weight = row.get(weightColumn.getName(), weightColumn.getType());
				if (weight instanceof Double) {
					sumWeights += (double) weight;
					if((double) weight < minWeight)
						minWeight = (double) weight;
				} else if (weight instanceof Integer) {
					sumWeights += (int) weight;
					if((int) weight < minWeight)
						minWeight = (int) weight;
				} else if (weight instanceof Long) {
					sumWeights += (long) weight;
					if((long) weight < minWeight)
						minWeight = (long) weight;
				}
			}
			double meanWeight = sumWeights/edgeTable.getRowCount();
			double normalizedMin = minWeight/meanWeight;
			double currentWeight = 0;

			//Divide by min to have unit weights
			for (CyRow row : edgeTable.getAllRows()) {
				weight = row.get(weightColumn.getName(), weightColumn.getType());
				if (weight instanceof Double) {
					currentWeight = (double) weight;
				} else if (weight instanceof Integer) {
					currentWeight = (int) weight;
				} else if (weight instanceof Long) {
					currentWeight = (long) weight;
				}
				row.set("_wksdc_normalizedWeight", (int) Math.round((currentWeight/meanWeight)/normalizedMin));
			}
		}


		CyTable table = network.getDefaultNodeTable();
		if(table.getColumn("_wksdc_kShell")==null) {
			table.createColumn("_wksdc_kShell", Integer.class, false);
		}
		if(table.getColumn("_wksdc_isPruned")==null) {
			table.createColumn("_wksdc_isPruned", Boolean.class, false);
		}
		for(CyRow row : table.getAllRows()) {
			row.set("_wksdc_kShell", 0);
			row.set("_wksdc_isPruned", false);
		}
		String primaryNodeKey = table.getPrimaryKey().getName();
		int kShell = 0;
		int k = 0;
		int degree = 0;
		double alpha = params.getDegreeExponent();
		double beta = params.getWeightExponent();
		CyNode node = null;
		Collection<CyRow> remainingRows = null;
		boolean done = false;
		boolean nodeWasPruned = true;
		//Keep iterating until all rows are assigned to a shell
		while(!done) {
			done = true;
			//Have to keep iterating on a shell until no more nodes are pruned at this level
			nodeWasPruned = true;
			while(nodeWasPruned) {
				nodeWasPruned = false;
				remainingRows = table.getMatchingRows("_wksdc_isPruned", false);
				//For each remaining node, see if it belongs to this k-shell
				for (CyRow row : remainingRows) {
					node = network.getNode(row.get(primaryNodeKey, Long.class));
					//calculate k
					degree = 0;
					sumWeights = 0;
					//Iterate list of edges
					for(CyEdge edge : network.getAdjacentEdgeIterable(node, CyEdge.Type.ANY)) {
						//Calculate based on those not pruned yet
						if(!network.getRow(edge.getSource()).get("_wksdc_isPruned", Boolean.class) && !network.getRow(edge.getTarget()).get("_wksdc_isPruned", Boolean.class)) {
							degree++;
							if(weightColumn!=null) {
								weight = network.getRow(edge).get("_wksdc_normalizedWeight", Integer.class);
								sumWeights += (int) weight;
							} else {
								sumWeights++;
							}
						}

					}

					k = (int) Math.round(Math.pow(Math.pow(degree, alpha) * Math.pow(sumWeights, beta), 1/(alpha+beta)));


					//if weighted degree is less than or equal to this k-shell, assign to this k-shell and prune
					if(kShell>=k) {
						row.set("_wksdc_kShell", kShell);
						row.set("_wksdc_isPruned", true);
						nodeWasPruned = true;
					} else {
						done = false;
					}
				}
			}
			kShell++; //?
		}
		table.deleteColumn("_wksdc_isPruned");
		edgeTable.deleteColumn("_wksdc_normalizedWeight");
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

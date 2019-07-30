package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;

import com.google.gson.Gson;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.model.Result;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.model.Shell;
import org.cytoscape.model.*;
import org.cytoscape.work.ObservableTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.json.JSONResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * Performs the weighted k-shell decomposition
 */

public class DecomposeTask implements ObservableTask {

	private boolean cancelled;
	private CyNetwork network;

	private Result result;


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
		//Convert network to a graph

		//Perform the algorithm on the graph

		//Write results to network and result class
		result = null;
	}

	@Override
	public void cancel() {
		cancelled = true;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getResults(Class type) {
		/*if (type == Result.class)
			return result;

		if (type == String.class) {
			StringBuilder sb = new StringBuilder();

			if (result == null) {
				sb.append("The decomposition failed"
				);
			} else {
				List<Shell> shells = result.getShells();

				sb.append(
						"<html><body>"
								+ "<span style='font-family: monospace; color: %1$s;'>Result #" + resultId + ":</span><br /> <br />"
								+ "<table style='font-family: monospace; color: %1$s;'>"
								+ "<tr style='font-weight: bold; border-width: 0px 0px 1px 0px; border-style: dotted;'>"
								+ "<th style='text-align: left;'>K</th>"
								+ "<th style='text-align: left;'>Nodes</th>"
								+ "<th style='text-align: left;'>Edges</th>"
								+ "</tr>")
				));

				for (Shell shell : shells)
					sb.append(String.format(
							"<tr>"
									+ "<td style='text-align: right;'>%d</td>"
									+ "<td style='text-align: right;'>%d</td>"
									+ "<td style='text-align: right;'>%d</td></tr>",
							shell.getK(),
							shell.getGraph().getNodeCount(),
							shell.getGraph().getEdgeCount()
					));

				sb.append("</table></body></html>");
			}

			return sb.toString();
		}

		if (type == JSONResult.class) {
			Gson gson = new Gson();
			JSONResult res = () -> { return gson.toJson(result); };

			return res;
		}*/

		return null;
	}

	@Override
	public List<Class<?>> getResultClasses() {
		return Arrays.asList(Result.class, String.class, JSONResult.class);
	}
}

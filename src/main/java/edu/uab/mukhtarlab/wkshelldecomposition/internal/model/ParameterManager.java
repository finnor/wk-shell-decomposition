package edu.uab.mukhtarlab.wkshelldecomposition.internal.model;

import java.util.HashMap;
import java.util.Map;

import org.cytoscape.model.CyNetwork;

/**
 * Stores parameters for a decomposition
 */
public class ParameterManager {

	private Map<Long, Parameters> currentParams = new HashMap<>();

	/**
	 * Get a copy of the current parameters for a particular network. Only a copy of the current param object is
	 * returned to avoid side effects.  The user should use the following code to get their
	 * own copy of the current parameters:
	 * AppCurrentParameters.getInstance().getParamsCopy();
	 * <p/>
	 * Note: parameters can be changed by the user after you have your own copy,
	 * so if you always need the latest, you should get the updated parameters again.                                                    
	 *
	 * @param networkID Id of the network
	 * @return A copy of the parameters
	 */
	public Parameters getParamsCopy(Long networkID) {
		if (networkID != null && currentParams.get(networkID) != null)
			return currentParams.get(networkID).copy();
			
		return new Parameters();
	}

	/**
	 * Current parameters can only be updated using this method.
	 * This method is called by DecomposeAction after comparisons have been conducted
	 * between the last saved version of the parameters and the current user's version.
	 *
	 * @param newParams The new current parameters to set
	 * @param resultId Id of the result set
	 * @param network The target network
	 */
	public void setParams(Parameters newParams, int resultId, CyNetwork network) {
		//cannot simply equate the params and newParams classes since that creates a permanent reference
		//and prevents us from keeping 2 sets of the class such that the saved version is not altered
		//until this method is called
		Parameters currentParamSet = new Parameters(
				network,
				newParams.getSelectedNodes(),
				newParams.getDegreeExponent(),
				newParams.getWeightExponent(),
				newParams.getCoreThreshold(),
				newParams.getDegreeThreshold(),
				newParams.getWeightThreshold());

		currentParams.put(network.getSUID(), currentParamSet);
	}
}

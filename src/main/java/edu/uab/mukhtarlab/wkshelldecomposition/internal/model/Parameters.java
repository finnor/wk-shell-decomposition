package edu.uab.mukhtarlab.wkshelldecomposition.internal.model;

import org.cytoscape.command.StringToModel;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.Tunable;

/**
 * Parameters for the decomposition
 */
public class Parameters {

	// scope
	private CyNetwork network;

	// used in scoring stage
	private int degreeExponent;
	private int weightExponent;
	private int coreThreshold;
	private int degreeThreshold;
	private int weightThreshold;

	/** Caches the SUID of selected nodes. */
	private Long[] selectedNodes;

	/**
	 * Constructor for the parameter set object. Default parameters are:
	 * network = null, ('use the current network')
	 * selectedNodes = new Integer[0],
	 * degree exponent = 1
	 * weight exponent = 1
	 * k-core threshold = 0
	 * degree threshold = 0
	 * weight threshold = 0
	 */
	public Parameters() {
		setDefaultParams();
	}

	/**
	 * Constructor for non-default algorithm parameters.
	 * Once an alalysis is conducted, new parameters must be saved so that they can be retrieved in the result panel
	 * for exploration and export purposes.
	 *
	 * @param network {@link CyNetwork} to be analyzed
	 * @param selectedNodes Node selection for selection-based scope
	 * @param degreeExponent degree exponent
	 * @param weightExponent weight exponent
	 * @param coreThreshold core threshold
	 * @param degreeThreshold degree threshold
	 * @param weightThreshold weight threshold
	 */
	public Parameters(
			CyNetwork network,
			Long[] selectedNodes,
			int degreeExponent,
			int weightExponent,
			int coreThreshold,
			int degreeThreshold,
			int weightThreshold
	) {
		setAllAlgorithmParams(network, selectedNodes, degreeExponent,
				weightExponent, coreThreshold, degreeThreshold, weightThreshold);
	}

	/**
	 * Method for setting all parameters to their default values
	 */
	public void setDefaultParams() {
		setAllAlgorithmParams(null, new Long[0], 1, 1, 0, 0, 0);
	}

	/**
	 * Convenience method to set all the main algorithm parameters
	 *
	 * @param network {@link CyNetwork} to be analyzed
	 * @param selectedNodes Node selection for selection-based scopes
	 * @param degreeExponent degree exponent
	 * @param weightExponent weight exponent
	 * @param coreThreshold core threshold
	 * @param degreeThreshold degree threshold
	 * @param weightThreshold weight threshold
	 */
	public void setAllAlgorithmParams(
			CyNetwork network,
			Long[] selectedNodes,
			int degreeExponent,
			int weightExponent,
			int coreThreshold,
			int degreeThreshold,
			int weightThreshold
	) {
		this.network = network;
		this.selectedNodes = selectedNodes;
		this.degreeExponent = degreeExponent;
		this.weightExponent = weightExponent;
		this.coreThreshold = coreThreshold;
		this.degreeThreshold = degreeThreshold;
		this.weightThreshold = weightThreshold;
	}

	/**
	 * Copies a parameter set object
	 *
	 * @return A copy of the parameter set
	 */
	public Parameters copy() {
		Parameters newParam = new Parameters();
		newParam.setNetwork(network);
		newParam.setSelectedNodes(selectedNodes);
		newParam.setDegreeExponent(degreeExponent);
		newParam.setWeightExponent(weightExponent);
		newParam.setCoreThreshold(coreThreshold);
		newParam.setDegreeThreshold(degreeThreshold);
		newParam.setWeightThreshold(weightThreshold);
		
		return newParam;
	}
	
	@Tunable(
			description = "Network",
			longDescription = StringToModel.CY_NETWORK_LONG_DESCRIPTION,
			exampleStringValue = StringToModel.CY_NETWORK_EXAMPLE_STRING,
			context = "nogui"
	)
	public CyNetwork getNetwork() {
		return network;
	}
	
	public void setNetwork(CyNetwork network) {
		this.network = network;
	}

	@Tunable(
			description = "α (Degree Exponent)",
			longDescription = "Sets the exponent to raise the degree by.",
			exampleStringValue = "1",
			context = "nogui"
	)
	public int getDegreeExponent() {
		return degreeExponent;
	}

	public void setDegreeExponent(int degreeExponent) {
		this.degreeExponent = degreeExponent;
	}

	@Tunable(
			description = "β (Weight Exponent)",
			longDescription = "Sets the exponent to raise the weight by.",
			exampleStringValue = "1",
			context = "nogui"
	)
	public int getWeightExponent() {
		return degreeExponent;
	}

	public void setWeightExponent(int weightExponent) {
		this.weightExponent = weightExponent;
	}

	@Tunable(
			description = "K-Core Threshold",
			longDescription = "Sets the k-core to threshold out of results.",
			exampleStringValue = "0",
			context = "nogui"
	)
	public int getCoreThreshold() {
		return coreThreshold;
	}

	public void setCoreThreshold(int coreThreshold) {
		this.coreThreshold = coreThreshold;
	}

	@Tunable(
			description = "Degree Threshold",
			longDescription = "Sets the degree by which to threshold out nodes.",
			exampleStringValue = "0",
			context = "nogui"
	)
	public int getDegreeThreshold() {
		return degreeThreshold;
	}

	public void setDegreeThreshold(int degreeThreshold) {
		this.degreeThreshold = degreeThreshold;
	}

	@Tunable(
			description = "Weight Threshold",
			longDescription = "Sets the weight by which to threshold out nodes.",
			exampleStringValue = "0",
			context = "nogui"
	)
	public int getWeightThreshold() {
		return weightThreshold;
	}

	public void setWeightThreshold(int weightThreshold) {
		this.weightThreshold = weightThreshold;
	}

	public Long[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(Long[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

	/**
	 * Generates a summary of the parameters. Only parameters that are necessary are included.
	 * For example, if fluff is not turned on, the fluff density cutoff will not be included.
	 *
	 * @return Buffered string summarizing the parameters
	 */
	@Override
	public String toString() {
		String lineSep = System.getProperty("line.separator");
		StringBuffer sb = new StringBuffer();
		sb.append("   Parameters:" + lineSep + "      α (Degree Exponent): " + degreeExponent + "  β (Weight Exponent): " +
				weightExponent + lineSep + "  Core Threshold: " + coreThreshold + "  Degree Threshold " +
				degreeThreshold + "  Weight Threshold " + weightThreshold + lineSep);
		return sb.toString();
	}
}

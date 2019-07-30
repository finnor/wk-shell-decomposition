package edu.uab.mukhtarlab.wkshelldecomposition.internal.model;

import org.cytoscape.command.StringToModel;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.Tunable;

/**
 * Parameters for the decomposition
 */
public class Parameters {

	private CyNetwork network;

	private String weightColumn;
	private double degreeExponent;
	private double weightExponent;


	/**
	 * Constructor for the parameter set object. Default parameters are:
	 * network = null, ('use the current network')
	 * selectedNodes = new Integer[0],
	 * degree exponent = 1
	 * weight exponent = 1
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
	 * @param weightColumn Which column is the weight
	 * @param degreeExponent degree exponent
	 * @param weightExponent weight exponent
	 */
	public Parameters(
			CyNetwork network,
			String weightColumn,
			double degreeExponent,
			double weightExponent
	) {
		setAllAlgorithmParams(network, weightColumn, degreeExponent, weightExponent);
	}

	/**
	 * Method for setting all parameters to their default values
	 */
	public void setDefaultParams() {
		setAllAlgorithmParams(null, null, 1.0, 1.0);
	}

	/**
	 * Convenience method to set all the main algorithm parameters
	 *
	 * @param network {@link CyNetwork} to be analyzed
	 * @param weightColumn Which column is the weight
	 * @param degreeExponent degree exponent
	 * @param weightExponent weight exponent
	 */
	public void setAllAlgorithmParams(
			CyNetwork network,
			String weightColumn,
			double degreeExponent,
			double weightExponent
	) {
		this.network = network;
		this.weightColumn = weightColumn;
		this.degreeExponent = degreeExponent;
		this.weightExponent = weightExponent;
	}

	/**
	 * Copies a parameter set object
	 *
	 * @return A copy of the parameter set
	 */
	public Parameters copy() {
		Parameters newParam = new Parameters();
		newParam.setNetwork(network);
		newParam.setWeightColumn(weightColumn);
		newParam.setDegreeExponent(degreeExponent);
		newParam.setWeightExponent(weightExponent);
		
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
	public double getDegreeExponent() {
		return degreeExponent;
	}

	public void setDegreeExponent(double degreeExponent) {
		this.degreeExponent = degreeExponent;
	}

	@Tunable(
			description = "β (Weight Exponent)",
			longDescription = "Sets the exponent to raise the weight by.",
			exampleStringValue = "1",
			context = "nogui"
	)
	public double getWeightExponent() {
		return weightExponent;
	}

	public void setWeightExponent(double weightExponent) {
		this.weightExponent = weightExponent;
	}

	public String getWeightColumn() {
		return weightColumn;
	}

	public void setWeightColumn(String weightColumn) {
		this.weightColumn = weightColumn;
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
				weightExponent + lineSep + "  Weight Column: " + weightColumn + lineSep);
		return sb.toString();
	}
}

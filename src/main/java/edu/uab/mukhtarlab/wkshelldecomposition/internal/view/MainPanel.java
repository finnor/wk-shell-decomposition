package edu.uab.mukhtarlab.wkshelldecomposition.internal.view;

import static javax.swing.GroupLayout.DEFAULT_SIZE;
import static javax.swing.GroupLayout.PREFERRED_SIZE;
import static org.cytoscape.util.swing.LookAndFeelUtil.createTitledBorder;
import static org.cytoscape.util.swing.LookAndFeelUtil.isAquaLAF;

import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.text.DecimalFormat;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.action.DecomposeAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.model.Parameters;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.model.ParameterManager;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.util.AppResources;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.util.AppResources.ImageName;

/**
 * The main panel where a user inputs parameters and starts decomposition
 */
public class MainPanel extends JPanel implements CytoPanelComponent {
	
	private final CySwingApplication swingApplication;

	private JPanel parametersPanel;
	private JFormattedTextField degreeExponentTxt;
	private JFormattedTextField weightExponentTxt;
	private JFormattedTextField coreThresholdTxt;
	private JFormattedTextField degreeThresholdTxt;
	private JFormattedTextField weightThresholdTxt;

	private Parameters currentParamsCopy; // stores current parameters - populates panel fields
	private DecimalFormat decFormat; // used in the formatted text fields

	public MainPanel(CySwingApplication swingApplication, DecomposeAction decomposeAction) {
		this.swingApplication = swingApplication;
		
		if (isAquaLAF())
			setOpaque(false);

		setMinimumSize(new Dimension(340, 400));
		setPreferredSize(new Dimension(380, 400));
		
		// get the current parameters
		currentParamsCopy = new ParameterManager().getParamsCopy(null);
		currentParamsCopy.setDefaultParams();

		decFormat = new DecimalFormat();
		decFormat.setParseIntegerOnly(true);
		
		final JButton decomposeBtn = new JButton(decomposeAction);
		
		// Create the three main panels: scope, advanced options, and bottom
		// Add all the vertically aligned components to the main panel
		final GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateContainerGaps(false);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.CENTER, true)
				.addComponent(getParametersPanel(), DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(decomposeBtn)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(getParametersPanel(), DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
				.addGap(0, 1, Short.MAX_VALUE)
				.addComponent(decomposeBtn)
		);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public CytoPanelName getCytoPanelName() {
		return CytoPanelName.WEST;
	}

	@Override
	public Icon getIcon() {
		URL iconURL = AppResources.getUrl(ImageName.LOGO);
		return new ImageIcon(iconURL);
	}

	@Override
	public String getTitle() {
		return "";
	}

	public Parameters getCurrentParamsCopy() {
		return currentParamsCopy;
	}

	private JPanel getParametersPanel() {
		if (parametersPanel == null) {
			parametersPanel = new JPanel();

			if (isAquaLAF())
				parametersPanel.setOpaque(false);

			parametersPanel.setBorder(createTitledBorder("Parameters"));
			parametersPanel.setMaximumSize(
					new Dimension(Short.MAX_VALUE, parametersPanel.getPreferredSize().height));

			final JLabel degreeExponentLabel = new JLabel("α (Degree Exponent):");
			degreeExponentLabel.setMinimumSize(getDegreeExponentTxt().getMinimumSize());
			degreeExponentLabel.setToolTipText(getDegreeExponentTxt().getToolTipText());

			final JLabel weightExponentLabel = new JLabel("β (Weight Exponent):");
			weightExponentLabel.setMinimumSize(getWeightExponentTxt().getMinimumSize());
			weightExponentLabel.setToolTipText(getWeightExponentTxt().getToolTipText());

			final JLabel coreThresholdLabel = new JLabel("K-Core Threshold:");
			coreThresholdLabel.setMinimumSize(getCoreThresholdTxt().getMinimumSize());
			coreThresholdLabel.setToolTipText(getCoreThresholdTxt().getToolTipText());

			final JLabel degreeThresholdLabel = new JLabel("Degree Threshold:");
			degreeThresholdLabel.setMinimumSize(getDegreeThresholdTxt().getMinimumSize());
			degreeThresholdLabel.setToolTipText(getDegreeThresholdTxt().getToolTipText());

			final JLabel weightThresholdLabel = new JLabel("Weight Threshold:");
			weightThresholdLabel.setMinimumSize(getWeightThresholdTxt().getMinimumSize());
			weightThresholdLabel.setToolTipText(getWeightThresholdTxt().getToolTipText());

			final GroupLayout layout = new GroupLayout(parametersPanel);
			parametersPanel.setLayout(layout);
			layout.setAutoCreateContainerGaps(true);
			layout.setAutoCreateGaps(true);

			layout.setHorizontalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(Alignment.TRAILING, true)
							.addComponent(degreeExponentLabel)
							.addComponent(weightExponentLabel)
							.addComponent(coreThresholdLabel)
							.addComponent(degreeThresholdLabel)
							.addComponent(weightThresholdLabel)
					).addGroup(layout.createParallelGroup(Alignment.LEADING, true)
							.addComponent(getDegreeExponentTxt(), PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
							.addComponent(getWeightExponentTxt(), PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
							.addComponent(getCoreThresholdTxt(), PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
							.addComponent(getDegreeThresholdTxt(), PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
							.addComponent(getWeightThresholdTxt(), PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
					)
			);
			layout.setVerticalGroup(layout.createParallelGroup(Alignment.TRAILING, false)
					.addGroup(layout.createSequentialGroup()
							.addComponent(degreeExponentLabel)
							.addComponent(weightExponentLabel)
							.addComponent(coreThresholdLabel)
							.addComponent(degreeThresholdLabel)
							.addComponent(weightThresholdLabel)
					).addGroup(layout.createSequentialGroup()
							.addComponent(getDegreeExponentTxt())
							.addComponent(getWeightExponentTxt(), PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
							.addComponent(getCoreThresholdTxt(), PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
							.addComponent(getDegreeThresholdTxt(), PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
							.addComponent(getWeightThresholdTxt(), PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
					)
			);
		}

		return parametersPanel;
	}

	private JFormattedTextField getDegreeExponentTxt() {
		if (degreeExponentTxt == null) {
			degreeExponentTxt = new JFormattedTextField(decFormat);
			degreeExponentTxt.setColumns(3);
			degreeExponentTxt.setHorizontalAlignment(SwingConstants.RIGHT);
			degreeExponentTxt.addPropertyChangeListener("value", new FormattedTextFieldAction());
			degreeExponentTxt.setToolTipText(
					"<html>Sets the exponent to raise the degree by.</html>");
			degreeExponentTxt.setText(String.valueOf(currentParamsCopy.getDegreeExponent()));
		}

		return degreeExponentTxt;
	}

	private JFormattedTextField getWeightExponentTxt() {
		if (weightExponentTxt == null) {
			weightExponentTxt = new JFormattedTextField(decFormat);
			weightExponentTxt.setColumns(3);
			weightExponentTxt.setHorizontalAlignment(SwingConstants.RIGHT);
			weightExponentTxt.addPropertyChangeListener("value", new FormattedTextFieldAction());
			weightExponentTxt.setToolTipText(
					"<html>Sets the exponent to raise the weight by.</html>");
			weightExponentTxt.setText(String.valueOf(currentParamsCopy.getWeightExponent()));
		}

		return weightExponentTxt;
	}

	private JFormattedTextField getCoreThresholdTxt() {
		if (coreThresholdTxt == null) {
			coreThresholdTxt = new JFormattedTextField(decFormat);
			coreThresholdTxt.setColumns(3);
			coreThresholdTxt.setHorizontalAlignment(SwingConstants.RIGHT);
			coreThresholdTxt.addPropertyChangeListener("value", new FormattedTextFieldAction());
			coreThresholdTxt.setToolTipText(
					"<html>Sets the k-core to threshold out of results.</html>");
			coreThresholdTxt.setText(String.valueOf(currentParamsCopy.getCoreThreshold()));
		}

		return coreThresholdTxt;
	}

	private JFormattedTextField getDegreeThresholdTxt() {
		if (degreeThresholdTxt == null) {
			degreeThresholdTxt = new JFormattedTextField(decFormat);
			degreeThresholdTxt.setColumns(3);
			degreeThresholdTxt.setHorizontalAlignment(SwingConstants.RIGHT);
			degreeThresholdTxt.addPropertyChangeListener("value", new FormattedTextFieldAction());
			degreeThresholdTxt.setToolTipText(
					"<html>Sets the degree by which to threshold out nodes.</html>");
			degreeThresholdTxt.setText(String.valueOf(currentParamsCopy.getDegreeThreshold()));
		}

		return degreeThresholdTxt;
	}

	private JFormattedTextField getWeightThresholdTxt() {
		if (weightThresholdTxt == null) {
			weightThresholdTxt = new JFormattedTextField(decFormat);
			weightThresholdTxt.setColumns(3);
			weightThresholdTxt.setHorizontalAlignment(SwingConstants.RIGHT);
			weightThresholdTxt.addPropertyChangeListener("value", new FormattedTextFieldAction());
			weightThresholdTxt.setToolTipText(
					"<html>Sets the weight by which to threshold out nodes.</html>");
			weightThresholdTxt.setText(String.valueOf(currentParamsCopy.getWeightThreshold()));
		}

		return weightThresholdTxt;
	}

	/**
	 * Validation
	 */
	private class FormattedTextFieldAction implements PropertyChangeListener {
		@Override
		public void propertyChange(PropertyChangeEvent e) {
			final JFormattedTextField source = (JFormattedTextField) e.getSource();

			String message = "The value you have entered is invalid.\n";
			boolean invalid = false;

			if (source == degreeExponentTxt) {
				Number value = (Number) degreeExponentTxt.getValue();
				
				if ((value != null) && (value.intValue() > 0)) {
					currentParamsCopy.setDegreeExponent(value.intValue());
				} else {
					source.setValue(1);
					message += "The degree exponent must be greater than 0.";
					invalid = true;
				}
			} else if (source == weightExponentTxt) {
				Number value = (Number) weightExponentTxt.getValue();

				if ((value != null) && (value.intValue() > 0)) {
					currentParamsCopy.setWeightExponent(value.intValue());
				} else {
					source.setValue(1);
					message += "The weight exponent must be greater than 0.";
					invalid = true;
				}
			} else if (source == coreThresholdTxt) {
				Number value = (Number) coreThresholdTxt.getValue();

				if ((value != null) && (value.intValue() >= 0)) {
					currentParamsCopy.setCoreThreshold(value.intValue());
				} else {
					source.setValue(0);
					message += "The core threshold must be greater than or equal to 0.";
					invalid = true;
				}
			} else if (source == degreeThresholdTxt) {
				Number value = (Number) degreeThresholdTxt.getValue();

				if ((value != null) && (value.intValue() >= 0)) {
					currentParamsCopy.setDegreeThreshold(value.intValue());
				} else {
					source.setValue(0);
					message += "The degree threshold must be greater than or equal to 0.";
					invalid = true;
				}
			} else if (source == weightThresholdTxt) {
				Number value = (Number) weightThresholdTxt.getValue();

				if ((value != null) && (value.intValue() >= 0)) {
					currentParamsCopy.setWeightThreshold(value.intValue());
				} else {
					source.setValue(0);
					message += "The weight threshold must be greater than or equal to 0.";
					invalid = true;
				}
			}

			if (invalid) {
				JOptionPane.showMessageDialog(swingApplication.getJFrame(),
											  message,
											  "Parameter out of bounds",
											  JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}

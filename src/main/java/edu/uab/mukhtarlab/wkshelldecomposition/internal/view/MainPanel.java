package edu.uab.mukhtarlab.wkshelldecomposition.internal.view;

import static javax.swing.GroupLayout.DEFAULT_SIZE;
import static javax.swing.GroupLayout.PREFERRED_SIZE;
import static org.cytoscape.util.swing.LookAndFeelUtil.createTitledBorder;
import static org.cytoscape.util.swing.LookAndFeelUtil.isAquaLAF;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.action.DecomposeAction;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.model.Parameters;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.model.ParameterManager;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.util.AppResources;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.util.AppResources.ImageName;
import org.cytoscape.model.CyColumn;
import org.cytoscape.service.util.CyServiceRegistrar;

/**
 * The main panel where a user inputs parameters and starts decomposition
 */
public class MainPanel extends JPanel implements CytoPanelComponent {
	
	private final CySwingApplication swingApplication;
	private final CyServiceRegistrar registrar;

	private JPanel parametersPanel;
	private JComboBox  weightColumn;
	private JFormattedTextField degreeExponentTxt;
	private JFormattedTextField weightExponentTxt;

	private Parameters currentParamsCopy; // stores current parameters - populates panel fields
	private DecimalFormat decFormat; // used in the formatted text fields

	public MainPanel(CySwingApplication swingApplication, CyServiceRegistrar registrar, DecomposeAction decomposeAction) {
		this.swingApplication = swingApplication;
		this.registrar = registrar;
		
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

			final JLabel weightColumnLabel = new JLabel("Weight Column:");
			Collection<CyColumn> columns = registrar.getService(CyApplicationManager.class).getCurrentNetwork().getDefaultEdgeTable().getColumns();
			ArrayList<String> colList = new ArrayList<>();
			colList.add(null);
			for(CyColumn column : columns) {
				if(column.getType()==Integer.class) {
					colList.add(column.getName());
				}
			}

			// create dropdown
			weightColumn = new JComboBox(colList.toArray());
			weightColumn.addItemListener(new ComboBoxAction());


			final GroupLayout layout = new GroupLayout(parametersPanel);
			parametersPanel.setLayout(layout);
			layout.setAutoCreateContainerGaps(true);
			layout.setAutoCreateGaps(true);

			layout.setHorizontalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(Alignment.TRAILING, true)
							.addComponent(weightColumnLabel)
							.addComponent(degreeExponentLabel)
							.addComponent(weightExponentLabel)
					).addGroup(layout.createParallelGroup(Alignment.LEADING, true)
							.addComponent(weightColumn, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
							.addComponent(getDegreeExponentTxt(), PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
							.addComponent(getWeightExponentTxt(), PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
					)
			);
			layout.setVerticalGroup(layout.createParallelGroup(Alignment.TRAILING, false)
					.addGroup(layout.createSequentialGroup()
							.addComponent(weightColumnLabel)
							.addComponent(degreeExponentLabel)
							.addComponent(weightExponentLabel)
					).addGroup(layout.createSequentialGroup()
							.addComponent(weightColumn)
							.addComponent(getDegreeExponentTxt(), PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
							.addComponent(getWeightExponentTxt(), PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
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

	private class ComboBoxAction extends JFrame implements ItemListener {
		public void itemStateChanged(ItemEvent e)
		{
			if (e.getSource() == weightColumn) {

				currentParamsCopy.setWeightColumn((String) weightColumn.getSelectedItem());
			}
		}
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
				
				if ((value != null) && (value.doubleValue() > 0)) {
					currentParamsCopy.setDegreeExponent(value.doubleValue());
				} else {
					source.setValue(1);
					message += "The degree exponent must be greater than 0.";
					invalid = true;
				}
			} else if (source == weightExponentTxt) {
				Number value = (Number) weightExponentTxt.getValue();

				if ((value != null) && (value.doubleValue() > 0)) {
					currentParamsCopy.setWeightExponent(value.doubleValue());
				} else {
					source.setValue(1);
					message += "The weight exponent must be greater than 0.";
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

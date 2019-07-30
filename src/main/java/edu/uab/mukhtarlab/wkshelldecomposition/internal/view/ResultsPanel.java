package edu.uab.mukhtarlab.wkshelldecomposition.internal.view;

import static javax.swing.GroupLayout.DEFAULT_SIZE;
import static org.cytoscape.util.swing.LookAndFeelUtil.isAquaLAF;

import java.awt.Component;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.cytoscape.application.CyUserLog;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.util.swing.BasicCollapsiblePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class ResultsPanel extends JPanel implements CytoPanelComponent {


    // Graphical classes
    private JScrollPane clusterBrowserScroll;
    private BasicCollapsiblePanel explorePnl;
    private JButton closeBtn;

    private final CyServiceRegistrar registrar;

    private static final Logger logger = LoggerFactory.getLogger(CyUserLog.class);

    /**
     * Constructor for the Results Panel which displays the clusters in a
     * browser table and explore panels for each cluster.
     *
     */
    public ResultsPanel(
            final CyServiceRegistrar registrar
    ) {

        this.registrar = registrar;

        clusterBrowserScroll = new JScrollPane();

        if (isAquaLAF())
            setOpaque(false);

        final GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateContainerGaps(false);
        layout.setAutoCreateGaps(false);

        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.CENTER, true)
                        .addComponent(clusterBrowserScroll, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(clusterBrowserScroll, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public CytoPanelName getCytoPanelName() {
        return CytoPanelName.EAST;
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public String getTitle() {
        return "Test";
    }


    private BasicCollapsiblePanel getExplorePnl() {
        if (explorePnl == null) {
            explorePnl = new BasicCollapsiblePanel("Explore");
            explorePnl.setCollapsed(false);
            explorePnl.setVisible(false);

            if (isAquaLAF())
                explorePnl.setOpaque(false);
        }

        return explorePnl;
    }

}

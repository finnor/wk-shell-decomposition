package edu.uab.mukhtarlab.wkshelldecomposition.internal.task;

import com.google.gson.Gson;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.model.Result;
import edu.uab.mukhtarlab.wkshelldecomposition.internal.model.Shell;
import org.cytoscape.model.*;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.ObservableTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.json.JSONResult;

import java.util.*;


/**
 * Performs the weighted k-shell decomposition
 */

public class ConcentricLayoutTask implements ObservableTask {

    private boolean cancelled;
    private CyNetwork network;
    private CyNetworkView nView;

    private Result result = new Result();


    public ConcentricLayoutTask(
            CyNetwork network,
            CyNetworkView nView
    ) {
        this.network = network;
        this.nView = nView;
    }

    /**
     * Runs the layout
     */
    @Override
    public void run(TaskMonitor tm) throws Exception {
        double nodeDiameter = 20.0;

        int layers = 20;

        int currentNode = 0;
        int nodesPerCircle = 1;
        int currentCircle = 0;
        double circleGap = 5.0;
        int currentLayer = 0;
        double layerGap = 50.0;

        double currentPositionRadius = 0;
        double angle = 0;
        double x = 0;
        double y = 0;
        double angleOffset = 0;

        CyRow row;
        Integer percentileBucket;
        double circlePerimeter;

        List<CyNode> nodes = network.getNodeList();
        //sort nodes by bucket, shell
        Comparator<CyNode> comparator = new Comparator<CyNode>() {
            @Override
            public int compare(CyNode a, CyNode b) {
                CyRow aRow = network.getRow(a);
                CyRow bRow = network.getRow(b);

                Integer aShell = aRow.get("_wkshell", Integer.class);
                Integer bShell = bRow.get("_wkshell", Integer.class);
                aShell = (aShell!=null) ? aShell : 0;
                bShell = (bShell!=null) ? bShell : 0;
                if(bShell!=aShell) {
                    return bShell - aShell;
                } else {
                    Integer aBucket = aRow.get("_wks_percentile_bucket", Integer.class);
                    Integer bBucket = bRow.get("_wks_percentile_bucket", Integer.class);
                    aBucket = (aBucket!=null) ? aBucket : 0;
                    bBucket = (bBucket!=null) ? bBucket : 0;
                    return bBucket - aBucket;
                }

            }
        };
        nodes.sort(comparator);

        boolean layerChange = false;
        boolean circleChange = false;

        for (final CyNode node : nodes) {
            View<CyNode> nodeView = nView.getNodeView(node);
            row = network.getRow(node);
            percentileBucket = row.get("_wks_percentile_bucket", Integer.class);
            percentileBucket = (percentileBucket!=null) ? percentileBucket : 0;

            layerChange = false;//((layers-1-(percentileBucket/5))>currentLayer);
            if(layerChange) {
                currentLayer = layers-1-(percentileBucket/5);
            }

            circleChange = (currentNode>=(nodesPerCircle));
            if(circleChange) {
                currentCircle++;
            }

            if(layerChange || circleChange){
                currentNode = 0;
                currentPositionRadius = (((double) currentCircle)*(nodeDiameter+circleGap)) + (((double) currentLayer)*layerGap);
                circlePerimeter = Math.PI*currentPositionRadius*2.0;
                nodesPerCircle = (int) (circlePerimeter/((nodeDiameter+circleGap)));
            }

            //TODO Maybe
            //Add an extra angle offset for the last circle if its empty to evenly space out the nodes within the circle
            //Would have to if on last circle and how many nodes there are in the last circle


            angle = Math.toRadians((((double) currentNode)/((double) nodesPerCircle))*360.0);
            x = Math.cos(angle) * currentPositionRadius;
            y = Math.sin(angle) * currentPositionRadius;

            nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION,x);
            nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION,y);

            currentNode++;
        }

        //TODO
        //reiterate through all nodes and color by heat scale on shell

        nView.updateView();
        nView.fitContent();
    }

    @Override
    public void cancel() {
        cancelled = true;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object getResults(Class type) {
        if (type == Result.class)
            return this.result;

        if (type == String.class) {
            StringBuilder sb = new StringBuilder();

            if (result == null) {
                sb.append("The decomposition failed"
                );
            } else {
                List<Shell> shells = result.getShells();

                sb.append(String.format(
                        "<html><body>"
                                + "<table style='font-family: monospace;'>"
                                + "<tr style='font-weight: bold; border-width: 0px 0px 1px 0px; border-style: dotted;'>"
                                + "<th style='text-align: left;'>K</th>"
                                + "<th style='text-align: left;'>Nodes</th>"
                                + "</tr>"
                ));

                for (Shell shell : shells)
                    sb.append(String.format(
                            "<tr>"
                                    + "<td style='text-align: right;'>%d</td>"
                                    + "<td style='text-align: right;'>%d</td></tr>",
                            shell.getK(),
                            shell.getSize()
                    ));

                sb.append("</table></body></html>");
            }

            return sb.toString();
        }

        if (type == JSONResult.class) {
            Gson gson = new Gson();
            JSONResult res = () -> { return gson.toJson(result); };

            return res;
        }

        return null;
    }

    @Override
    public List<Class<?>> getResultClasses() {
        return Arrays.asList(Result.class, String.class, JSONResult.class);
    }
}

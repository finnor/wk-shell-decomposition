package edu.uab.mukhtarlab.wkshelldecomposition.internal.model;

import org.cytoscape.model.CyNode;

import java.util.ArrayList;

public class Shell {
    private ArrayList<CyNode> nodes;

    public Shell(
            ArrayList<CyNode> nodes
    ) {
        this.nodes = nodes;
    }

    public ArrayList<CyNode> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<CyNode> nodes) {
        this.nodes = nodes;
    }


}

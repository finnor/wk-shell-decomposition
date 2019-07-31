package edu.uab.mukhtarlab.wkshelldecomposition.internal.model;

import org.cytoscape.model.CyNode;

import java.util.ArrayList;

public class Shell {
    private ArrayList<CyNode> nodes;
    private int k;

    public Shell(int k) {
        this.k = k;
    }

    public ArrayList<CyNode> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<CyNode> nodes) {
        this.nodes = nodes;
    }

    public void addNode(CyNode node) {
        this.nodes.add(node);
    }
}

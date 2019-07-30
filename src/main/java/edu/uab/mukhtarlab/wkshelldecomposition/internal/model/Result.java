package edu.uab.mukhtarlab.wkshelldecomposition.internal.model;

import edu.uab.mukhtarlab.wkshelldecomposition.internal.model.Shell;

import java.util.ArrayList;

public class Result {
    private ArrayList<Shell> shells;

    public Result(
            ArrayList<Shell> shells
    ) {
        this.shells = shells;
    }

    public ArrayList<Shell> getShells() {
        return shells;
    }

    public void setShells(ArrayList<Shell> nodes) {
        this.shells = nodes;
    }

    public ArrayList<Shell> addShell(Shell shell) {
        shells.add(shell);
        return shells;
    }


}

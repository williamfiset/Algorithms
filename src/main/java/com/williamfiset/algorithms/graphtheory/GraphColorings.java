/**
 *
 *
 * @author Zehao Jiang
 */

package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;

import com.williamfiset.algorithms.datastructures.graph.Graph;
import com.williamfiset.algorithms.datastructures.graph.Graph.Node;

public interface GraphColorings {
    /**
     * Main function to call for graph coloring
     *
     * @param G - desired graph
     */
    public boolean colorGraph(Graph G, int colorsMax);

    /**
     * Judge if graph is colorable based on different algorithms
     *
     * @param G - desired graph
     * @param noOfColors - number of colors given
     */
    public boolean isGraphColorable(Graph G, int noOfColors);

    /**
     * Shuffle the list of nodes
     *
     * @param nodeList - List of node needs shuffling
     */
    public ArrayList<Node> shuffleVertices(ArrayList<Node> nodeList);

    /**
     * Choose random or not
     */
    public void toggleShuffle();
}
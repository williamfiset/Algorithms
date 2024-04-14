/**
 *
 *
 * @author Zehao Jiang
 */

package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;

import com.williamfiset.algorithms.datastructures.graph.Graph;
import com.williamfiset.algorithms.datastructures.graph.Graph.Node;
import com.williamfiset.algorithms.datastructures.graph.Graph.Edge;


public class GraphColoringBackTracking extends GraphColoringAbstract {

   boolean shuffle = false;
   int colorsAvailable;

   @Override
   public boolean colorGraph(Graph G, int colorsMax) {
    colorsAvailable = colorsMax;
       if(isGraphColorable(G, colorsAvailable)){
           return true;
       }
       else{
           return false;
       }
   }

   /**
    * Check if current graph coloring status is correct, e.g. no
    * neighbor nodes have the same graph
    *
    * @param edges - List of edges
    */
   public boolean isSafe(ArrayList<Edge> edges, Graph g){
       for (Edge e: edges){
           if (g.getNode(e.getFrom()).color == g.getNode(e.getTo()).color)
               return false;
       }
       return true;
   }

   public ArrayList<Node> getNodes(Graph G) {
       return G.getNodes();
   }

   public boolean colorGraphUsingBackTrack(Graph G, ArrayList<Node> vertices, int index, int numberOfColors){

       //if all vertices are covered
       if (index == vertices.size())
           return true;

       for (int i = 0; i < numberOfColors; i++){

           //color vertex
           vertices.get(index).color = i;

           //check if it safe
           if (this.isSafe((ArrayList<Edge>) vertices.get(index).getEdges(), G)){
               //color next vertex
               if (colorGraphUsingBackTrack(G, vertices, index+1, numberOfColors))
                   return true;
               //if next vertex was not colorable. reset the color and try next one
               vertices.get(index).color = -1; //reset color
           }
       }

       return false;
   }

   public void toggleShuffle(){
       shuffle = !shuffle;
   }

   @Override
   public boolean isGraphColorable(Graph G, int noOfColors) {
       ArrayList<Node> vertices;

       if (this.shuffle)
           vertices = this.shuffleVertices(G.getNodes());
       else
           vertices = G.getNodes();

       return this.colorGraphUsingBackTrack(G, vertices, 0, noOfColors);

   }

   public static void main(String[] args) {
    // create new graph
    Graph graph = new Graph();

    // add nodes and edges
    graph.addNode(1);
	graph.addNode(2);
	graph.addNode(3);
	
	graph.addEdge(1,2);
	graph.addEdge(1,3);
	graph.addEdge(2,3);
	graph.addEdge(2,3); 

    GraphColoringBackTracking bt = new GraphColoringBackTracking();

    // Since 4 colors are enough for this graph, result would be true
    boolean result = bt.colorGraph(graph, 4);
   }
}
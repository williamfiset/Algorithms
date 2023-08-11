package com.williamfiset.algorithms.graphtheory;
import java.util.ArrayList;
import java.util.Random;

import com.williamfiset.algorithms.datastructures.graph.Graph;
import com.williamfiset.algorithms.datastructures.graph.Graph.Node;

public abstract class GraphColoringAbstract implements GraphColorings{

	boolean shuffle = false;
	
	public void toggleShuffle(){
		shuffle = !shuffle;
	}
	
	public ArrayList<Node> shuffleVertices(ArrayList<Node> vertices){
		int n = vertices.size();
		Random rn = new Random();
		while(n > 1){
			Node x = vertices.get(n-1);
			int ind = rn.nextInt(n-1);
			vertices.set(n-1, vertices.get(ind));
			vertices.set(ind, x);
			n--;
		}
		return vertices;
	}
	
	@Override
	public abstract boolean colorGraph(Graph G, int colorsMax);

	@Override
	public abstract boolean isGraphColorable(Graph G, int noOfColors);

}
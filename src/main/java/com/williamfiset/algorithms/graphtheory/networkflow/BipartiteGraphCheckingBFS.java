import java.util.*;
import java.lang.*;
import java.io.*;

// Program to Check whether Graph is a Bipartite using BFS
public class BipartiteBfs
{
    private int numberOfVertices;
    private Queue<Integer> queue;
 
    public static final int NO_COLOR = 0;
    public static final int RED = 1;
    public static final int BLUE = 2;
 
    public BipartiteBfs(int numberOfVertices)
    {
        this.numberOfVertices = numberOfVertices;
        queue = new LinkedList<Integer>();
    }
 
    public boolean isBipartite(int adjacencyMatrix[][], int source)
    {
        int[] colored = new int[numberOfVertices +  1];
        for (int vertex = 1; vertex <= numberOfVertices; vertex++)
        {
            colored[vertex] = NO_COLOR;
        }
        colored[source] = RED;
        queue.add(source);
 
        int element, neighbour;
        while (!queue.isEmpty())
        {
            element = queue.remove();
            neighbour = 1;
            while (neighbour <= numberOfVertices)
            { 
                if (adjacencyMatrix[element][neighbour] == 1 && colored[element]== colored[neighbour])
                {
                    return false;
                }
                if (adjacencyMatrix[element][neighbour] == 1 && colored[neighbour]== NO_COLOR)
                {
                    colored[neighbour] = (colored[element] == RED ) ? BLUE :RED;
                    queue.add(neighbour);
                }
                neighbour++;
            }
        }
        return true;
    }
 
    //The Main function
    public static void main(String[] args)
    {
        int number_of_nodes, source;
        Scanner scanner = new Scanner(System.in);
        try 
        {
           System.out.println("Enter the number of nodes in the graph");
           number_of_nodes = scanner.nextInt();
 
           int adjacency_matrix[][] = new int[number_of_nodes + 1][number_of_nodes + 1];
           System.out.println("Enter the adjacency matrix");
           for (int i = 1; i <= number_of_nodes; i++)
           {
               for (int j = 1; j <= number_of_nodes; j++)
               {    
                   adjacency_matrix[i][j] = scanner.nextInt();
               }
           }
 
           for (int i = 1; i <= number_of_nodes; i++)
           {
               for (int j = 1; j <= number_of_nodes; j++)
               {    
                   if(adjacency_matrix[i][j] == 1 && adjacency_matrix[j][i] == 0)
                   {
                       adjacency_matrix[j][i] = 1;
                   }
               }
           }
 
           System.out.println("Enter the source for the graph");
           source = scanner.nextInt();
 
           BipartiteBfs bipartiteBfs = new BipartiteBfs(number_of_nodes);
           if (bipartiteBfs.isBipartite(adjacency_matrix, source)) 
           {
               System.out.println("The given graph is bipartite");
           } else
           {
               System.out.println("The given graph is not bipartite");
           }
       } catch (InputMismatchException inputMismatch) 
       {
           System.out.println("Wrong Input format");
       }
    }
}

/*
Sample Case ::
Input ::
Enter the number of nodes in the graph
4
Enter the adjacency matrix
0 1 0 1
1 0 1 0
0 1 0 1
1 0 1 0
Enter the source for the graph
1

Output ::
The given graph is bipartite
*/
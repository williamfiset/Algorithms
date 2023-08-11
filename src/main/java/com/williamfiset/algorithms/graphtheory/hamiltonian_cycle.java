import java.util.Scanner;

class HamiltonCycle {
    final static int MAX = 10;
    static int NO_VER = MAX;
    int path[];

    public static void main(String args[]) {
        HamiltonCycle hamiltonian = new HamiltonCycle();
        Scanner in = new Scanner(System.in);
        System.out.println("Backtracking Hamiltonian Cycle Algorithm:\n");

        // Accept number of vertices
        System.out.print("Enter number of vertices:\t");
        NO_VER = in.nextInt();

        // Get graph
        System.out.println("\nEnter the adjacency matrix:\n");
        int[][] graph = new int[NO_VER][NO_VER];
        for (int i = 0; i < NO_VER; i++)
            for (int j = 0; j < NO_VER; j++)
                graph[i][j] = in.nextInt();

        // Print the solution
        hamiltonian.hamCycle(graph);

    }

    boolean isSafe(int v, int graph[][], int path[], int pos) {

        // Check if this vertex is an adjacent vertex
        if (graph[path[pos - 1]][v] == 0)
            return false;

        // Check if the vertex has already been included.
        for (int i = 0; i < pos; i++)
            if (path[i] == v)
                return false;

        return true;
    }

    boolean findHamCycle(int graph[][], int path[], int pos) {

        // Check if all vertices have been included
        if (pos == NO_VER) {

            // Check if there is an edge from the last vertex to the source vertex
            if (graph[path[pos - 1]][path[0]] == 1)
                return true;
            else
                return false;
        }

        // Try different vertices as a next vertex
        for (int v = 1; v < NO_VER; v++) {

            // Check if this vertex can be added to Hamiltonian Cycle
            if (isSafe(v, graph, path, pos)) {
                path[pos] = v;

                // Construct rest of the path
                if (findHamCycle(graph, path, pos + 1) == true)
                    return true;

                // If adding vertex v doesn't lead to a solution, then remove it
                path[pos] = -1;
            }
        }

        // If no vertex can be added, then return false
        return false;
    }

    int hamCycle(int graph[][]) {
        path = new int[NO_VER];

        //Initialize
        for (int i = 0; i < NO_VER; i++)
            path[i] = -1;

        //Set source
        path[0] = 0;
        if (findHamCycle(graph, path, 1) == false) {
            System.out.println("\nSolution does not exist!");
            return 0;
        }
        printSolution(path);
        return 1;
    }

    void printSolution(int path[]) {
        System.out.println("Solution Exists! \nFollowing is one of Hamiltonian Cycle:\n");
        for (int i = 0; i < NO_VER; i++)
            System.out.print(" " + path[i] + " --> ");

        // Print the first vertex again to show the complete cycle
        System.out.println(path[0] + " ");
    }
}
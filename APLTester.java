import util.Graph;
import util.Node;
import util.TarjanSCC;
import util.APL;

public class APLTester {

  public static void main(String[] args) {
    Graph<Integer> graph = new Graph<>();
    int[][] edges
        = {{0, 1}, {1, 2}, {2, 3}, {3, 2},
           {3, 7}, {7, 3}, {2, 6}, {7, 6},
           {5, 6}, {6, 5}, {1, 5}, {4, 5},
           {4, 0}, {1, 4}};
        // 3 Strongly Connected Components

        // = {{1, 2}, {2, 3}, {3, 1}, {4, 2},
        //    {4, 3}, {5, 4}, {4, 5}, {6, 3},
        //    {6, 7}, {7, 6}, {8, 7}, {8, 5},
        //    {8, 8}, {5, 6}};  
        // 4 Strongly Connected Components

        // = {{1, 2}, {1, 3}, {1, 4}, {1, 5},
        //    {2, 1}, {2, 3}, {2, 4}, {2, 5},
        //    {3, 1}, {3, 2}, {3, 4}, {3, 5},
        //    {4, 1}, {4, 2}, {4, 3}, {4, 5},
        //    {5, 1}, {5, 2}, {5, 3}, {5, 4}};  
        // 1 Strongly Connected Component

        // = {{1, 2}, {1, 3}, {1, 4}, {1, 5},
        //    {2, 3}, {2, 4}, {2, 5}, {3, 4},
        //    {3, 5}, {4, 5}};    
        // 5 Strongly Connected Components

    for (int[] edge : edges) {
      graph.addEdge(edge[0], edge[1]);
    }

    // APL.compute(graph, graph.getNode(1), graph.getNode(5));

    TarjanSCC<Integer> scc = new TarjanSCC<Integer>(graph);
    System.out.println(scc.count() + " Strongly Connected Components");
    System.out.println(scc);

  }
}

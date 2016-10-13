package util;

public class APL {

  public static void compute(Graph graph, Node u, Node v) {
    TarjanSCC<Integer> scc = new TarjanSCC<>(graph);
    if (scc.count() == graph.size()) {
      System.out.println("Work is complete. Use Tobi's Algorithm.");
    } else {
      System.out.println("Constructing Supergraph");
      Graph<Integer> supergraph = new Graph<>();
      
    }
  }

}

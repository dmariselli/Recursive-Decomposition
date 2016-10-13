package util;

import java.util.List;

public class APL {

  public static <T> void compute(Graph<T> graph, Node<T> u, Node<T> v) {
    TarjanSCC<T> tarjan = new TarjanSCC<>(graph);
    System.out.println(tarjan);
    List<SCC<T>> sccs = tarjan.getSCCs();

    if (sccs.size() == graph.size()) {
      System.out.println("Work is complete. Use Tobi's Algorithm.");
    } else {
      System.out.println("Constructing Supergraph");
      Graph<T> supergraph = new Graph<>();
      populateGraph(supergraph, sccs, u, v);
      System.out.println("Node Graph: " + supergraph);
    }
  }

  private static <T> void populateGraph(Graph<T> graph, List<SCC<T>> sccs, Node<T> u, Node<T> v) {
    for (SCC<T> scc : sccs) {
        for (Node<T> entryNode : scc.getEntryNodes()) {
          graph.addNode(entryNode.getValue());
        }
        for (Node<T> exitNode : scc.getExitNodes()) {
          graph.addNode(exitNode.getValue());
        }
      }
      graph.addNode(u.getValue());
      graph.addNode(v.getValue());
  }

}

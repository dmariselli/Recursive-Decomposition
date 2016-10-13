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
      System.out.println("SuperGraph: \n" + supergraph);

    }
  }

  private static <T> void populateGraph(Graph<T> graph, List<SCC<T>> sccs, Node<T> u, Node<T> v) {
    for (SCC<T> scc : sccs) {
        for (Node<T> entryNode : scc.getEntryNodes()) {
          // System.out.println("Entry Node: " + entryNode);
          graph.addNode(entryNode.getValue());
        }
        for (Node<T> exitNode : scc.getExitNodes()) {
          // System.out.println("Exit Node: " + exitNode);
          graph.addNode(exitNode.getValue());
        }
      }

    for (SCC<T> scc : sccs) {
      for (Node<T> exitNode : scc.getExitNodes()) {
        // System.out.println("Exit Node To Analyze: "  + exitNode);
        for (Node<T> adjNode : exitNode.getAdjacents()) {
          // System.out.println("Adjacent Node To Analyze: "  + adjNode);
          if (!scc.contains(adjNode) && graph.containsNode(adjNode.getValue())) {
            // System.out.println("Adding edge between " + exitNode + " and " + adjNode);
            graph.addEdge(exitNode.getValue(), adjNode.getValue());
          }
        }
      }
    }

      graph.addNode(u.getValue());
      graph.addNode(v.getValue());
  }

}

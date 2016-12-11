package util;

import java.util.List;

public class APL {

  public static <T> double[] compute(Graph<T> graph, Node<T> u, Node<T> v) {
    double[] result = computeHelper(graph, u, v);
    result[1] = result[1] / result[0];
    return result;
  }

  public static <T> double[] computeHelper(Graph<T> graph, Node<T> u, Node<T> v) {
    TarjanSCC<T> tarjan = new TarjanSCC<>(graph);
    System.out.println("Tarjan's Algorithm: " + tarjan);
    List<SCC<T>> sccs = tarjan.getSCCs();

    if (sccs.size() == graph.size()) {
      return PathFinder.superDagTraversal(graph, u.getValue(), v.getValue());
    } else {
      // System.out.println("Constructing Supergraph");
      Graph<T> supergraph = new Graph<>();
      populateGraph(supergraph, sccs, u, v);
      decomposeGraph(supergraph, sccs, u, v);
      System.out.println("SuperGraph: \n" + supergraph);
      return PathFinder.superDagTraversal(supergraph, u.getValue(), v.getValue());
    }
  }

  private static <T> void decomposeGraph(Graph<T> graph, List<SCC<T>> sccs, Node<T> u, Node<T> v) {
    for (SCC<T> scc : sccs) {
      for (Node<T> entryNode : scc.getEntryNodes()) {
        for (Node<T> exitNode : scc.getExitNodes()) {
          System.out.println("X Node: " + entryNode);
          System.out.println("Y Node: " + exitNode);
          if (!entryNode.equalValue(exitNode)) {
            Graph<T> subgraph = new Graph<>();
            makeSubgraph(graph, subgraph, scc, entryNode, exitNode);
            // add edge with value found in APL calculation
            double[] values = APL.computeHelper(subgraph, entryNode, exitNode);
            System.out.println("Values: " + values[0] + " " + values[1]);
            System.out.println("Adding super edge between " + entryNode + " and " + exitNode);

            // split
            // check if exit should be split
            // if so, split, make edge between split parts, create edge between entry and new exit
            // move cross edge to that new node
            // else, add super edge

            graph.addSuperEdge(entryNode.getValue(), exitNode.getValue(), values[0], values[1]);
          }
        }
      }
    }
  }

  // subgraph = copy of SCC with (*, x), (y, *), crossEdges, and nodes with not edges removed
  private static <T> void makeSubgraph(Graph<T> graph, Graph<T> subgraph, SCC<T> scc, Node<T> x, Node<T> y) {
    for (Node<T> node : scc.getNodes()) {
      // System.out.println(node);
      if (!node.equals(y)) {
        for (Node<T> adjNode : node.getAdjacents()) {
          // System.out.println("Node: " + node + " adj: " + adjNode);
          if (scc.contains(adjNode) && !adjNode.equals(x)) {
            if (node.equalValue(adjNode)) {
              subgraph.addSuperEdge(node.getValue(), adjNode.getValue(), 1.0, 0.0);
            } else {
              subgraph.addEdge(node.getValue(), adjNode.getValue()); // adding edge will also create the nodes
            }
          }
        }
      }
    }
    System.out.println("Subgraph:\n" + subgraph);
  }

  private static <T> void populateGraph(Graph<T> graph, List<SCC<T>> sccs, Node<T> u, Node<T> v) {
    addSpecialNodes(graph, sccs, u, v);
    addCrossEdges(graph, sccs);
    System.out.println("Populated Graph:\n" + graph);
  }

  private static <T> void addSpecialNodes(Graph<T> graph, List<SCC<T>> sccs, Node<T> u, Node<T> v) {
    for (SCC<T> scc : sccs) {
      if (scc.contains(u) && !scc.containsEntryNode(u)) {
        scc.addEntryNode(u);
      }
      if (scc.contains(v) && !scc.containsExitNode(v)) {
        scc.addExitNode(v);
      }

      // scc.computeSpecialNodes();

      for (Node<T> entryNode : scc.getEntryNodes()) {
        // if (entryNode.isExitNode()) {
          // System.out.println("Should not happen");
        // }
        // System.out.println("Entry Node: " + entryNode);
        graph.addNode(entryNode.getValue());
      }
      for (Node<T> exitNode : scc.getExitNodes()) {
        // System.out.println("Exit Node: " + exitNode);
        graph.addNode(exitNode.getValue());
      }
    }
  }

  private static <T> void addCrossEdges(Graph<T> graph, List<SCC<T>> sccs) {
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
  }

}

package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Random;

public class PathFinder {

  private static Random generator = new Random();

  /**
   * Brute Force implementation for computing the number of paths in a
   * graph between two nodes.
   *
   * @param graph the graph for which the number of paths between two
   *     nodes is to be computed.
   * @param start the value representing the node to start our
   *     graph traversal from.
   * @param end the value representing the node to end our graph
   *     traversal on.
   *
   * @return the number of unique paths between <code>start</code>
   *     and <code>end</code>
   *
   */
  public static <T> int uniquePaths(Graph<T> graph, T start, T end) {
    Node<T> sNode = graph.getNode(start);
    Node<T> eNode = graph.getNode(end);
    return uniquePathsAux(sNode, eNode);
  }

  /**
   * Auxiliary function to help determine the number of paths between
   * two nodes in a graph.
   *
   * @param curr the node to start graph traversal from.
   * @param end the node to end our graph traversal on.
   *
   * @return the number of unique paths between <code>start</code>
   *     and <code>end</code>
   *
   */
  private static <T> int uniquePathsAux(Node<T> curr, Node<T> end) {
    if (curr.getValue() == end.getValue()) {
      return 1;
    }
    int pathCount = 0;
    curr.setVisited(true);
    for (Node<T> node : curr.getAdjacents()) {
      if (!node.getVisited()) {
        pathCount += uniquePathsAux(node, end);
      }
    }
    curr.setVisited(false);
    return pathCount;
  }

  /**
   * Brute Force implementation for computing the average length
   * of the paths in a graph between two nodes.
   *
   * @param graph the graph for which the average path length between
   *     two nodes is to be determined.
   * @param start the value representing the node to start our
   *     graph traversal from.
   * @param end the value representing the node to end our graph
   *     traversal on.
   *
   * @return the number of unique paths between <code>start</code>
   *     and <code>end</code>
   *
   */
  public static <T> int uniquePathsAverageLength(Graph<T> graph,
      T start, T end) {
    Node<T> sNode = graph.getNode(start);
    Node<T> eNode = graph.getNode(end);
    ArrayList<Integer> counts = new ArrayList<>();
    int numberOfPaths = uniquePathsAverageLengthAux(sNode,
        eNode, 0, counts);
    int sum = 0;
    for (int length : counts) {
      sum += length;
    }
    return sum/numberOfPaths;
  }

  /**
   * Auxiliary function to help determine the number of paths between
   * two nodes in a graph.
   *
   * @param curr the node to start graph traversal from.
   * @param end the node to end our graph traversal on.
   * @param length the current length of the path provided that the
   *     <code>curr</code> has a path that leads to <code>end</code>.
   * @param counts the record of path lengths that we have determined
   *     thus far.
   *
   * @return the number of unique paths between <code>start</code>
   *     and <code>end</code>
   *
   */
  private static <T> int uniquePathsAverageLengthAux(Node<T> curr,
      Node<T> end, int length, ArrayList<Integer> counts) {
    if (curr.getValue() == end.getValue()) {
      counts.add(length);
      return 1;
    }
    curr.setVisited(true);
    int pathCount = 0;
    for (Node<T> node : curr.getAdjacents()) {
      if (!node.getVisited()) {
        pathCount += uniquePathsAverageLengthAux(node, end,
            length + 1, counts);
      }
    }
    curr.setVisited(false);
    return pathCount;
  }

  public static <T> double[] dagTraversalDecider(Graph<T> graph,
      T start, T end) {
    // if (graph.hasSuperEdges()) {
      return superDagTraversal(graph, start, end);
    // }
    // return dagTraversal(graph, start, end);
  }

  /**
   * Finds the number of paths that exist between two nodes
   * in a graph as well as the average lengths of those paths.
   *
   * @param graph the Directed Acyclical Graph from which we
   *     are to determine the number of paths and average lengths
   *     of those paths.
   * @param start the value in the graph from which we are to
   *     begin traversal
   * @param end the value in the graph at which we are to end
   *     our traversal
   *
   * @return an array containing the number of paths from
   *     <code>start</code> to <code>end</code> in <code>graph</code>.
   *
   */
  public static <T> double[] dagTraversal(Graph<T> graph,
      T start, T end) {
    Node<T> sNode = graph.getNode(start);
    Node<T> eNode = graph.getNode(end);
    List<Node<T>> dfsOrdered = dfsTopoSort(graph, sNode);
    eNode.addDistance(0);
    eNode.setVisited(true);
    calculatePath(dfsOrdered, 0, sNode, eNode);
    double lengthSums = 0;
    double totalNumPaths = 0;
    for (Map.Entry<Double, Double> entry : sNode.getDistances().entrySet()) {
      lengthSums += entry.getValue() * entry.getKey();
      totalNumPaths += entry.getValue();
    }
    return new double[]{totalNumPaths, totalNumPaths == 0 ? 0
        : (lengthSums/totalNumPaths)};
  }

  // trial
  public static <T> double[] superDagTraversal(Graph<T> graph,
      T start, T end) {
    System.out.println("Computing using Super Edges");
    Node<T> sNode = graph.getNode(start);
    Node<T> eNode = graph.getNode(end);
    List<Node<T>> dfsOrdered = dfsTopoSort(graph, sNode);
    System.out.println("DFS Order: " + dfsOrdered);
    eNode.addDistance(0);
    eNode.setVisited(true);
    calculateSuperPath(dfsOrdered, 0, sNode, eNode);
    double lengthSums = 0;
    double totalNumPaths = 0;
    for (Map.Entry<Double, Double> entry : sNode.getDistances().entrySet()) {
      lengthSums += entry.getValue() * entry.getKey();
      totalNumPaths += entry.getValue();
    }
    System.out.println("Values being returned: " + totalNumPaths + " " + (totalNumPaths == 0 ? 0
        : lengthSums/totalNumPaths));
    return new double[]{totalNumPaths, totalNumPaths == 0 ? 0
        : lengthSums/totalNumPaths};
  }

  /**
   * Performs a Topologicial Sort of the nodes in graph in
   * order of Depth-First Search.
   *
   * @param graph the graph for which the Topological Sort
   *     is to take place on.
   * @param sNode the node to begin the Topological Sort at.
   *
   * @return an {@link List} of the nodes in order of Topological Sort
   *
   */
  private static <T> List<Node<T>> dfsTopoSort(Graph<T> graph,
      Node<T> sNode) {
    List<Node<T>> topoSorted = new ArrayList<>();
    dfsTopoSort(topoSorted, new HashSet<>(), sNode);
    // Since topological sort returns the list of nodes in order of
    // finishing times, the first node we exam (our start node), will
    // always be the last node in the list. For clarity in the algorithm
    // for computing the number of paths from s to t as well as the
    // average length of those paths between them, we will reverse the list.
    Collections.reverse(topoSorted);
    return topoSorted;
  }

  /** Auxiliary method to aid in computing the Topological Sort */
  private static <T> void dfsTopoSort(List<Node<T>> tSorted,
      Set<Node<T>> tSortSet, Node<T> node) {
    node.setVisited(true);
    for (Node<T> adj : node.getAdjacents()) {
      if (!adj.getVisited() && !tSortSet.contains(adj)) {
        dfsTopoSort(tSorted, tSortSet, adj);
      }
    }
    node.setVisited(false);
    tSorted.add(node);
    tSortSet.add(node);
  }

  /**
   * Computes the number of paths that exist between two nodes in a graph.
   * 
   * For this algorithm we perform a Depth-First Search (DFS) and only look
   * at unvisited adjacent nodes that succeed the current node in order of 
   * Topological Sort. Because this is a Directed Acyclical Graph (DAG) we
   * can guarantee that any valid-adjacent nodes are nodes that we would
   * have to repeatedly visit everytime we come across that node in the graph.
   * Due to this property we only have to visit each node in the graph once 
   * and make a record of the number of possible paths from that node to the 
   * destination node, any node that has a directed edge to that node can then 
   * aggregate its path count based off of this pre-existing record for its 
   * adjacent node.
   *
   * @param sorted a list of nodes sorted according to Topological Sort. The
   *     nodes we are computing the number of paths for in this graph must be 
   *     contained within this list.
   * @param position the current position in <code>sorted</code>
   * @param curr the node for which we are computing the number of paths
   *     to <code>end</code>.
   *
   */
  private static <T> void calculatePath(List<Node<T>> sorted,
      int position, Node<T> curr, Node<T> end) {
    if (!curr.getVisited()) {
      curr.setVisited(true);
      for (int i = position + 1; i < sorted.size(); i++) {
        if (curr.hasEdge(sorted.get(i))) {
          Node<T> adj = sorted.get(i);
          calculatePath(sorted, i, adj, end);
          for (Map.Entry<Double, Double> entry
              : adj.getDistances().entrySet()) {
            double currCount = curr.getDistanceCount(entry.getKey() + 1) == null ?
                0 : curr.getDistanceCount(entry.getKey() + 1);
            curr.addDistance(entry.getKey() + 1, entry.getValue() + currCount);
          }
        }
      }
    }
  }

  // private static <T> void calculateOriginalSuperPath(List<Node<T>> sorted,
  //     int position, Node<T> curr, Node<T> end) {
  //   if (!curr.getVisited()) {
  //     curr.setVisited(true);
  //     for (int i = position + 1; i < sorted.size(); i++) {
  //       System.out.println("There is an edge between " + curr + " and " + sorted.get(i) + ": " + curr.hasEdge(sorted.get(i)));
  //       if (curr.hasEdge(sorted.get(i))) {
  //         Node<T> adj = sorted.get(i);
  //         // System.out.println("Nodes to look at next: " + adj + " " + end);
  //         calculateSuperPath(sorted, i, adj, end);
  //         // length, num of times length happened
  //         for (Map.Entry<Double, Double> entry
  //             : adj.getDistances().entrySet()) {
  //           // System.out.println("Entry: " + entry);
  //           double currCount = curr.getDistanceCount(entry.getKey() + 1) == null ? 
  //               0 : curr.getDistanceCount(entry.getKey() + 1);
  //               // length, num of times length appeared
  //           Edge<T> adjEdge = null;
  //           if (curr.hasSuperEdge()) {
  //             // System.out.println("curr has superedge");
  //             for (Edge<T> edge : curr.getEdges()) {
  //               if (edge.contains(adj)) {
  //                 adjEdge = edge;
  //               }
  //             }
  //           }
  //           // System.out.println(curr);
  //           // System.out.println(adj);
  //           if (adjEdge != null) {
  //             System.out.println("Super " + curr + " " + adj + " AvgLength: " + adjEdge.getAvgLength() + " NumOfPaths: " + adjEdge.getNumOfPaths());
  //             curr.addDistance(adjEdge.getAvgLength(), adjEdge.getNumOfPaths());
  //           } else {
  //             System.out.println("Regular " + curr + " " + adj + " AvgLength: " + (entry.getKey() + 1)  + " NumOfPaths: " + (entry.getValue() + currCount));
  //             curr.addDistance(entry.getKey() + 1, entry.getValue() + currCount);
  //           }
  //         }
  //       }
  //     }
  //   }
  // }

  // private static <T> void calculateModifiedSuperPath(List<Node<T>> sorted,
  //     int position, Node<T> curr, Node<T> end) {
  //   if (!curr.getVisited()) {
  //     curr.setVisited(true);
  //     for (int i = position + 1; i < sorted.size(); i++) {
  //       System.out.println("There is an edge between " + curr + " and " + sorted.get(i) + ": " + curr.hasEdge(sorted.get(i)));
  //       if (curr.hasEdge(sorted.get(i))) {
  //         Node<T> adj = sorted.get(i);
  //         // System.out.println("Nodes to look at next: " + adj + " " + end);
  //         calculateSuperPath(sorted, i, adj, end);
  //         System.out.println("Calculating: " + curr + " and " + adj);
  //         Edge<T> adjEdge = null;
  //         if (curr.hasSuperEdge()) {
  //           // System.out.println("curr has superedge");
  //           for (Edge<T> edge : curr.getEdges()) {
  //             if (edge.contains(adj)) {
  //               adjEdge = edge;
  //             }
  //           }
  //         }

      //     if (adjEdge != null) {
      //       if (adj.getEdges().size() != 0) {
      //         for (Edge<T> edge : adj.getEdges()) {
      //           System.out.println("Super " + curr + " " + adj + " AvgLength: " + (adjEdge.getAvgLength()+adjEdge.getAvgLength()) + " NumOfPaths: " + (adjEdge.getNumOfPaths()*adjEdge.getNumOfPaths()));
      //           curr.addDistance(adjEdge.getAvgLength() + adjEdge.getAvgLength(), adjEdge.getNumOfPaths() * adjEdge.getNumOfPaths());
      //         }
      //       } else {
      //         System.out.println(adj.getDistances());
      //         for (Map.Entry<Double, Double> entry
      //             : adj.getDistances().entrySet()) {
      //           // System.out.println("Entry: " + entry);
      //           double currCount = curr.getDistanceCount(entry.getKey() + adjEdge.getAvgLength()) == null ? 
      //               0 : curr.getDistanceCount(entry.getKey() + adjEdge.getAvgLength());
      //           // System.out.println("Super " + curr + " " + adj + " AvgLength: " + (adjEdge.getAvgLength()+adjEdge.getAvgLength()) + " NumOfPaths: " + (adjEdge.getNumOfPaths()*adjEdge.getNumOfPaths()));
      //           System.out.println("Super with Regular " + curr + " " + adj + " AvgLength: " + (entry.getKey() + adjEdge.getAvgLength()) + " NumOfPaths: " + (entry.getValue() + currCount));
      //           // adding currCount at end of line doesn't make a difference? always adding 0?
      //           curr.addDistance(entry.getKey() + adjEdge.getAvgLength(), entry.getValue() + currCount);
      //         }
      //       }
      //     } else {
      //       // length, num of times length happened
      //       for (Map.Entry<Double, Double> entry
      //           : adj.getDistances().entrySet()) {
      //         // System.out.println("Entry: " + entry);
      //         double currCount = curr.getDistanceCount(entry.getKey() + 1) == null ? 
      //             0 : curr.getDistanceCount(entry.getKey() + 1);
      //             // length, num of times length appeared
      //         System.out.println("Regular " + curr + " " + adj + " AvgLength: " + (entry.getKey() + 1)  + " NumOfPaths: " + (entry.getValue() + currCount));
      //         curr.addDistance(entry.getKey() + 1, entry.getValue() + currCount);
      //       }
      //     }
      //   }
      // }
  //   }
  // }

  private static <T> void calculateSuperPath(List<Node<T>> sorted,
      int position, Node<T> curr, Node<T> end) {
    if (!curr.getVisited()) {
      curr.setVisited(true);
      for (int i = position + 1; i < sorted.size(); i++) {
        System.out.println("There is an edge between " + curr + " and " + sorted.get(i) + ": " + curr.hasEdge(sorted.get(i)));
        if (curr.hasEdge(sorted.get(i))) {
          Node<T> adj = sorted.get(i);
          // System.out.println("Nodes to look at next: " + adj + " " + end);
          calculateSuperPath(sorted, i, adj, end);

          Edge<T> adjEdge = null;
          // if (curr.hasSuperEdge()) {
            // System.out.println("curr has superedge");
            for (Edge<T> edge : curr.getEdges()) {
              if (edge.contains(adj)) {
                adjEdge = edge;
              }
            }

            if (adjEdge == null) {
              System.out.println("NOT SUPPOSED TO HAPPEN");
              adjEdge = curr.addSuperEdge(adj);
            }
          // } else {
            // System.out.println("NOT SUPPOSED TO HAPPEN");
            // adjEdge = curr.addSuperEdge(adj);
          // }

          // length, num of times length happened
          for (Map.Entry<Double, Double> entry
              : adj.getDistances().entrySet()) {
            // System.out.println("Entry: " + entry);
            double currCount = curr.getDistanceCount(entry.getKey() + adjEdge.getAvgLength()) == null ? 
                    0 : curr.getDistanceCount(entry.getKey() + adjEdge.getAvgLength());
                // length, num of times length appeared
            // System.out.println(curr);
            // System.out.println(adj);
            System.out.println("Super " + curr + " " + adj + " AvgLength: " + (entry.getKey() + adjEdge.getAvgLength()) + " NumOfPaths: " + (entry.getValue() + currCount));
            curr.addDistance(entry.getKey() + adjEdge.getAvgLength(), entry.getValue() + currCount);
            // if (adjEdge != null) {
            //   System.out.println("Super " + curr + " " + adj + " AvgLength: " + adjEdge.getAvgLength() + " NumOfPaths: " + adjEdge.getNumOfPaths());
            //   curr.addDistance(adjEdge.getAvgLength(), adjEdge.getNumOfPaths());
            // } else {
            //   System.out.println("Regular " + curr + " " + adj + " AvgLength: " + (entry.getKey() + 1)  + " NumOfPaths: " + (entry.getValue() + currCount));
            //   curr.addDistance(entry.getKey() + 1, entry.getValue() + currCount);
            // }
          }
        }
      }
    }
  }

  /** Implementation of algorithm 2 (Length-Distribution method) */
  public static <T> void lengthDistribution(Graph<T> graph, T start, T end) {

  }

  /** Implementation of algorithm 1 (Naive Path Generation). */
  public static <T> void naivePathGeneration(Graph<T> graph, T start, T end) {
    Node<T> sNode = graph.getNode(start);
    Node<T> eNode = graph.getNode(end);

    sNode.setLikelihood(1.0);
    sNode.setCounter(1);
    naivePathGeneration(sNode, eNode);
  }

  private static <T> void naivePathGeneration(Node<T> curr, Node<T> end) {
    curr.setVisited(true);
    if (curr.equals(end)) {
      return;
    }
    int index;
    Node<T> adj;
    List<Node<T>> adjList = new ArrayList<>(curr.getAdjacents());
    while (!adjList.isEmpty()) {
      index = generator.nextInt(adjList.size());
      adj = adjList.remove(index);
      if (adj.getVisited()) {
        continue;
      }
      adj.setCounter(curr.getCounter() + 1);
      double size = (double) adjList.size();
      adj.setLikelihood(curr.getLikelihood()/size);
      naivePathGeneration(adj, end);
    }
  }
}
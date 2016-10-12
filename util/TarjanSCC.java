package util;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import util.Graph;
import util.Node;

public class TarjanSCC<T> {

  private Map<Node<T>, Integer> low;
  private int pre;
  private Stack<Node<T>> stack;
  private List<List<Node<T>>> comps;
  // @Todo: list of component objects
  // @Todo: component object includes Node List, Incoming List, Outgoing List

  public TarjanSCC(Graph<T> graph) {
    comps = new ArrayList<>();
    low = new HashMap<>();
    stack = new Stack<>();
    int vertices = graph.size();
    for (T nodeValue : graph.getNodeValues()) {
      Node<T> node = graph.getNode(nodeValue);
      if (!node.getVisited()) {
        dfs(graph, node);
      }
    }
  }

  private void dfs(Graph<T> graph, Node<T> node) {
    node.setVisited(true);
    int min = pre;
    low.put(node, pre++);
    stack.push(node);
    for (Node<T> adjNode : node.getAdjacents()) {
      if (!adjNode.getVisited()) {
        dfs(graph, adjNode);
      }
      if (low.get(adjNode) < min) {
        min = low.get(adjNode);
      }
    }
    if (min < low.get(node)) {
      low.put(node, min);
      return;
    }
    List<Node<T>> component = new ArrayList<>();
    Node<T> w;
    do {
      w = stack.pop();
      component.add(w);
      low.put(w, graph.size());
    } while (!w.equals(node));
    comps.add(component);
  }

  /**
   * Returns the number of strong components.
   * @return the number of strong components
   */ 
  public int count() {
    return comps.size();
  }

  @Override
  public String toString() {
    return comps.toString();
  }

}

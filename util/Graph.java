package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Graph<T> {

  private Map<T, Node<T>> nodeMap;
  // private boolean hasSuperEdges;

  public Graph() {
    nodeMap = new HashMap<>();
    // hasSuperEdges = false;
  }

  // public boolean hasSuperEdges() {
  //   return hasSuperEdges;
  // }

  public void addNode(T value) {
    if (!containsNode(value)) {
      Node<T> node = new Node<>(value);
      nodeMap.put(value, node);
    }
  }

  public void addEdge(T start, T end) {
    Node<T> s = findOrConstructNode(start);
    Node<T> e = findOrConstructNode(end);
    Edge<T> edge = s.addAdjacent(e);
    e.addIncomingAdjacent(s, edge);
  }

  public void addSuperEdge(T start, T end, double num, double length) {
    Node<T> s = findOrConstructNode(start);
    Node<T> e = findOrConstructNode(end);
    Edge<T> edge = s.addSuperAdjacent(e, num, length);
    e.addIncomingAdjacent(s, edge);
    // hasSuperEdges = true;
  }

  public Node<T> getNode(T value) {
    return nodeMap.get(value);
  }

  public Set<T> getNodeValues() {
    return nodeMap.keySet();
  }

  public int size() {
    return nodeMap.size();
  }

  private Node<T> findOrConstructNode(T value) {
    if (!containsNode(value)) {
      addNode(value);
    }
    return getNode(value);
  }

  public boolean containsNode(T value) {
    return nodeMap.containsKey(value);
  }

  @Override
  public String toString() {
    StringBuilder strBldr = new StringBuilder();
    for (Map.Entry<T, Node<T>> entry : nodeMap.entrySet()) {
      strBldr.append(entry.getValue());
      strBldr.append(": ");
      strBldr.append(entry.getValue().getAdjacents());
      strBldr.append("\n");
    }
    return strBldr.toString();
  }
}

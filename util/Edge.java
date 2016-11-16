package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Edge<T> {

  Node<T> entryNode;
  Node<T> exitNode;
  double numOfPaths;
  double totalLength;
  Set<Node<T>> nodes; // have to include entry exit nodes?

  public Edge(Node<T> entry, Node<T> exit) {
    entryNode = entry;
    exitNode = exit;
    nodes = new HashSet<>();
    nodes.add(entryNode);
    nodes.add(exitNode);
    numOfPaths = 1;
    totalLength = 1;
  }

  public Edge(Node<T> entry, Node<T> exit, double num, double length) {
    entryNode = entry;
    exitNode = exit;
    nodes = new HashSet<>();
    nodes.add(entryNode);
    nodes.add(exitNode);
    numOfPaths = num;
    totalLength = length;
  }

  public boolean pathContains(Node<T> node) {
    return nodes.contains(node);
  }

  public boolean contains(Node<T> node) {
    return exitNode.equals(node);
  }

  public void setNumOfPaths(double num) {
    numOfPaths = num;
  }

  public void setTotalLength(double length) {
    totalLength = length;
  }

  public double getNumOfPaths() {
    return numOfPaths;
  }

  public double getTotalLength() {
    return totalLength;
  }

  public Node<T> getEntryNode() {
    return entryNode;
  }

  public Node<T> getExitNode() {
    return exitNode;
  }

}

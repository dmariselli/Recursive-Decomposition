package util;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class SCC<T> {

  Set<Node<T>> entryNodes;
  Set<Node<T>> exitNodes;
  Set<Node<T>> nodes;
  boolean isEntryNodesComputed;
  boolean isExitNodesComputed;

  public SCC() {
    entryNodes = new HashSet<>();
    exitNodes = new HashSet<>();
    nodes = new HashSet<>();
    isEntryNodesComputed = false;
    isExitNodesComputed = false;
  }

  public void add(Node<T> node) {
    nodes.add(node);
  }

  public boolean contains(Node<T> node) {
    return nodes.contains(node);
  }

  public Set<Node<T>> getEntryNodes() {
    if (!isEntryNodesComputed) {
      for (Node<T> node : nodes) {
        for (Node<T> inAdjNode : node.getIncomingAdjacents()) {
          if (!nodes.contains(inAdjNode)) {
            entryNodes.add(node);
          }
        }
      }
      isEntryNodesComputed = !isEntryNodesComputed;
    }
    return entryNodes;
  }

  public Set<Node<T>> getExitNodes() {
    if (!isExitNodesComputed) {
      for (Node<T> node : nodes) {
        for (Node<T> adjNode : node.getAdjacents()) {
          if (!nodes.contains(adjNode)) {
            exitNodes.add(node);
          }
        }
      }
      isExitNodesComputed = !isExitNodesComputed;
    }
    return exitNodes;
  }

  public Set<Node<T>> getNodes() {
    return nodes;
  }

  public void addEntryNode(Node<T> node) {
    entryNodes.add(node);
  }

  public void addExitNode(Node<T> node) {
    exitNodes.add(node);
  }

  public boolean containsEntryNode(Node<T> node) {
    return entryNodes.contains(node);
  }

  public boolean containsExitNode(Node<T> node) {
    return exitNodes.contains(node);
  }

  public int size() {
    return nodes.size();
  }

  @Override
  public String toString() {
    return nodes.toString();
  }

}

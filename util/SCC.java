package util;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class SCC<T> {

  Set<Node<T>> entryNodes;
  Set<Node<T>> exitNodes;
  Set<Node<T>> nodes;
  boolean isEntryNodesComputed = false;
  boolean isExitNodesComputed = false;

  public SCC() {
    entryNodes = new HashSet<>();
    exitNodes = new HashSet<>();
    nodes = new HashSet<>();
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

  public int size() {
    return nodes.size();
  }

  @Override
  public String toString() {
    return nodes.toString();
  }

}

package util;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class SCC<T> {

  List<Node<T>> entryNodes;
  List<Node<T>> exitNodes;
  Set<Node<T>> nodes;
  boolean isEntryNodesComputed = false;
  boolean isExitNodesComputed = false;

  public SCC() {
    entryNodes = new ArrayList<>();
    exitNodes = new ArrayList<>();
    nodes = new HashSet<>();
  }

  public void add(Node<T> node) {
    nodes.add(node);
  }

  public List<Node<T>> getEntryNodes() {
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

  public List<Node<T>> getExitNodes() {
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

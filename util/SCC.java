package util;

import java.util.List;
import java.util.ArrayList;
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

  public void computeSpecialNodes() {
    // Computing Entry Nodes
    for (Node<T> node : nodes) {
      for (Node<T> inAdjNode : node.getIncomingAdjacents()) {
        if (!nodes.contains(inAdjNode)) {
          addEntryNode(node);
        }
      }
    }

    // for (int i = 0; i < nodes.size(); i++) {
    //   Node<T> node = nodes.get(i);
    //   boolean isExit = false;
    //   for (Node<T> adjNode : node.getAdjacents()) {
    //     if (!nodes.contains(adjNode)) {
    //         isExit = true;
    //     }
    //   }
    //   if (node.isEntryNode() && isExit) {
    //     Node<T> exitNode = node.exitTransfer();
    //     nodes.add(exitNode);
    //     addExitNode(exitNode);
    //   } else if (!node.isEntryNode() && isExit) {
    //     addExitNode(node);
    //   }
    // }

    // Computing Exit Nodes

    List<Node<T>> exitNodesToAdd = new ArrayList<>();

    for (Node<T> node : nodes) {
      boolean isExit = false;
      for (Node<T> adjNode : node.getAdjacents()) {
        if (!nodes.contains(adjNode)) {
            isExit = true;
        }
      }
      if (node.isEntryNode() && isExit) {
        Node<T> exitNode = node.exitTransfer();
        exitNodesToAdd.add(exitNode);
      } else if (!node.isEntryNode() && isExit) {
        addExitNode(node);
      }
    }

    for (Node<T> node : exitNodesToAdd) {
      nodes.add(node);
      addExitNode(node);
    }

    isEntryNodesComputed = true;
  }

  public Set<Node<T>> getEntryNodes() {
    if (!isEntryNodesComputed) {
      computeSpecialNodes();
    }
    return entryNodes;
  }

  public Set<Node<T>> getExitNodes() {
    if (!isExitNodesComputed) {
      computeSpecialNodes();
    }
    return exitNodes;
  }

  public Set<Node<T>> getNodes() {
    return nodes;
  }

  public void addEntryNode(Node<T> node) {
    entryNodes.add(node);
    node.setEntry(true);
  }

  public void addExitNode(Node<T> node) {
    exitNodes.add(node);
    node.setExit(true);
  }

  public boolean containsEntryNode(Node<T> node) {
    if (!isEntryNodesComputed) {
      getEntryNodes();
    }
    return entryNodes.contains(node);
  }

  public boolean containsExitNode(Node<T> node) {
    if (!isExitNodesComputed) {
      getExitNodes();
    }
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

package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Node<T> {

  private T value;
  private boolean visited;
  private Map<Double, Double> distances;
  private List<Node<T>> adjacents;
  private List<Edge<T>> edges;
  private Set<Node<T>> adjacentSet;
  private List<Node<T>> incomingAdjacents;
  private double numOfPaths;
  private double lenOfPaths;

  // For use in naive path generation
  private double likelihood;
  private int counter;

  /** Constructor */
  public Node(T value) {
    this.value = value;
    this.visited = false;
    // this.hasSuperEdge = false;
    this.distances = new HashMap<>();
    this.adjacents = new ArrayList<>();
    this.edges = new ArrayList<>();
    this.adjacentSet = new HashSet<>();
    this.incomingAdjacents = new ArrayList<>();
    this.numOfPaths = 0;
    this.lenOfPaths = 0;
  }

  public void setNumOfPaths(double paths) {
    numOfPaths = paths;
  }

  public void setLengthOfPaths(double length) {
    lenOfPaths = length;
  }

  public double getNumOfPaths() {
    return numOfPaths;
  }

  public double getLengthOfPaths() {
    return lenOfPaths;
  }

  /** Adds a node to a node's adjacent list. */
  public void addAdjacent(Node<T> node) {
    adjacents.add(node);
    adjacentSet.add(node);
    edges.add(new Edge(this, node));
  }

  public void addSuperAdjacent(Node<T> node, double num, double length) {
    adjacents.add(node);
    adjacentSet.add(node);
    edges.add(new Edge(this, node, num, length));
  }

  public Edge<T> addSuperEdge(Node<T> node) {
    Edge<T> nEdge = new Edge(this, node, 1, 1);
    edges.add(nEdge);
    // hasSuperEdge = true;
    return nEdge;
  }

  public void addIncomingAdjacent(Node<T> node) {
    incomingAdjacents.add(node);
  }

  /** Retrieved the value contained by this node. */
  public T getValue() {
    return value;
  }

  /** Sets the value that determines whether or not a node has been visited. */
  public void setVisited(boolean visited) {
    this.visited = visited;
  }

  /** Returns a boolean value indicating that a node has been visited */
  public boolean getVisited() {
    return visited;
  }

  /** Returns a list of adjacent nodes. */
  public List<Node<T>> getAdjacents() {
    return adjacents;
  }

  public List<Node<T>> getIncomingAdjacents() {
    return incomingAdjacents;
  }

  public List<Edge<T>> getEdges() {
    return edges;
  }

  /**
   * Adds a length to the value to the map of nodes determined by the
   * distance to a destination
   *
   */
  public void addDistance(double length) {
    if (distances.containsKey(length)) {
      distances.put(length, distances.get(length) + 1);
    } else {
      distances.put(length, 1.0);
    }
  }

  /**
   * Increments the values associated with the key (length)
   * for the mapping of path-length to occurrences.
   *
   */
  public void addDistance(double length, double times) {
    distances.put(length, times);
  }

  /** Retrives the path-length to occurrence mappings. */
  public Map<Double, Double> getDistances() {
    return distances;
  }

  /** Gets the count for a partcular distance */
  public Double getDistanceCount(double count) {
    return distances.get(count);
  }

  /** Determines whether or not a node has an edge to another node */
  public boolean hasEdge(Node<T> node) {
    return adjacentSet.contains(node);
  }

  /** Sets the likelihood */
  public void setLikelihood(double likelihood) {
    this.likelihood = likelihood;
  }

  /** Retrieves the likelihood */
  public double getLikelihood() {
    return likelihood;
  }

  /** Sets the counter */
  public void setCounter(int counter) {
    this.counter = counter;
  }

  /** Retrieves the counter */
  public int getCounter() {
    return counter;
  }

  /** Rich output of information for this node */
  public void printDetail() {
    System.out.println("Value: " + value);
    System.out.println("\tLikelihood: " + likelihood);
    System.out.println("\tCounter: " + counter);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Node) {
      return this.value == ((Node<T>) obj).getValue();
    }
    return false;
  }

  @Override
  public String toString() {
    return value.toString();
  }
}

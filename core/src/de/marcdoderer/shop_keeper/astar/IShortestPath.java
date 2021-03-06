/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.marcdoderer.shop_keeper.astar;

/**
 *
 * @author Marc
 */
public interface IShortestPath<N,E> {
    
      /**
   * Computes the Bellman Ford algorithm on the weighted graph <tt>graph</tt>
   * from starting node <tt>startnode</tt>. This method is started after
   * initializing a ShortPath object.
   * 
   * @param graph       the weighted graph
   * @param startnode the starting node
   */
  public void aStar(IWeightedGraph<N,E> graph, int startnode, int endnode);

  /**
   * Returns the distance of the shortest path from the starting node
   * <tt>startnode</tt> to node <tt>destination</tt>.
   * (Distance in terms of edge weights)
   * 
   * @param destination the destination node
   * @return the length (weight) of a shortest path from the starting node
   *         <tt>startnode</tt> to node <tt>destination</tt>;
   *         <tt>Double.POSITIVE_INFINITY</tt> if no such path exists
   * @throws IllegalStateException if there is a negative cost cycle reachable
   *                               from the starting node <tt>startnode</tt>
   */
  public double distance();

  /**
   * Returns a boolean whether there is a path from the starting node
   * <tt>startnode</tt> to node <tt>destination</tt>?
   * 
   * @param destination the destination node
   * @return <tt>true</tt> if there is a path from the starting node
   *         <tt>startnode</tt> to node <tt>destination</tt>, and
   *         <tt>false</tt> otherwise
   */
  public boolean existsPath();

  /**
   * Returns the edges of the shortest path from the starting node
   * <tt>startnode</tt> to node <tt>destination</tt>.
   * Example: A path exists <tt>s--->u--->x--->y</tt> then the iterable will
   * begin with the edge from node <tt>s</tt> to node <tt>u</tt>, then the
   * edge from node <tt>u</tt> to node <tt>x</tt> and then the edge from
   * node <tt>x</tt> to node <tt>y</tt>.
   * 
   * @param destination the destination node
   * @return a shortest path from the starting node <tt>startnode</tt> to
   *         node <tt>destination</tt> in as an iterable of the
   *         edges, and <tt>null</tt> if no such path exists
   * @throws IllegalStateException if there is a negative cost cycle reachable
   *                               from the starting node <tt>startnode</tt>
   */
  public Iterable<IEdge<E>> path();
}

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
public interface IEdge<E> {
      /**
   * Returns the tail (source) vertex of the directed edge.
   * 
   * @return the tail (source) vertex of the edge
   */
  public int getSource();

  /**
   * Returns the head (destination) vertex of the directed edge.
   * 
   * @return the head (destination) vertex of the directed edge
   */
  public int getDestination();

  /**
   * Returns the weight of the directed edge.
   * 
   * @return the weight of the directed edge
   */
  public double getWeight();
  
  /**
   * @return meta data of this edge
   */
  public E getMetaData();
}

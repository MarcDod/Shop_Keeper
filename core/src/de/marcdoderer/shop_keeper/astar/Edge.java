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
public class Edge<E> implements IEdge<E>{
    private final int source;
    private final int destination;
    private final double weight;
    private E metaData;
    
    public Edge(final int source, final int destination, final double weight){
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public int getSource() {
        return this.source;
    }

    @Override
    public int getDestination() {
        return this.destination;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public E getMetaData() {
        return this.metaData;
    }
    
    public void setMetaData(final E metaData){
        this.metaData = metaData;
    }

    @Override
    public String toString() {
        return String.format("{%d --> %d [%s]}", getSource(), getDestination(), getMetaData());
    }
    
    
}

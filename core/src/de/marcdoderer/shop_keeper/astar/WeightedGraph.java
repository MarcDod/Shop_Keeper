
package de.marcdoderer.shop_keeper.astar;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Marc
 * @param <N>
 * @param <E>
 */
public class WeightedGraph<N extends Position,E> implements IWeightedGraph<N, E> {

    private int numNodes;
    private int numEdges;
    private final ArrayList<ArrayList<IEdge<E>>> adjancecyList;
    private final ArrayList<N> nodeMetaData;
    
    public WeightedGraph(final int n){
        if(n < 0) throw new IllegalArgumentException("Number of nodes in graph must be nonnegative");
        
        this.numNodes = n;
        this.numEdges = 0;
        this.nodeMetaData = new ArrayList<>(n);
        this.adjancecyList = new ArrayList<>(n);
        for(int i = 0; i < n; i++){
            this.nodeMetaData.add(null);
            this.adjancecyList.add(new ArrayList<IEdge<E>>(1));
        }
        
    }
    
    public WeightedGraph(){
        this(0);
    }
    
    public Edge<E> addEdge(final int source, final int destination, final double weight){
        final Edge<E> edge = new Edge(source, destination, weight);
        addEdge(edge);
        return edge;
    }
                
    public void addEdge(IEdge<E> edge){
        final int source = edge.getSource();
        this.adjancecyList.get(source).add(edge);
        this.numEdges++;
    }
    
    public void addNode(){
        this.addNode(null);
    }
    
    public void setNodeMetaData(int nodeIdx, N meta){
        this.nodeMetaData.set(nodeIdx, meta);
    }
    
    public void addNode(final N noteMetaData){
        this.nodeMetaData.add(noteMetaData);
        this.adjancecyList.add(new ArrayList<IEdge<E>>(1));
        this.numNodes++;
    }
    
    @Override
    public int numberOfEdges() {
        return this.numEdges;
    }

    @Override
    public int numberOfNodes() {
        return this.numNodes;
    }

    @Override
    public N getNodeMetaData(final int nodeIdx) {
        return this.nodeMetaData.get(nodeIdx);
    }

    @Override
    public Iterator<IEdge<E>> edgeIterator() {
        final ArrayList<IEdge<E>> edgeList = new ArrayList<>(numEdges);
        for(int i = 0; i < numNodes; i++){
            edgeList.addAll(this.adjancecyList.get(i));
        }
        return edgeList.iterator();
    }

    @Override
    public Iterator<IEdge<E>> outgoingEdges(int src) {
        return this.adjancecyList.get(src).iterator();
    }
    
}

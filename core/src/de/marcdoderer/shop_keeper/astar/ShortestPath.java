/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.marcdoderer.shop_keeper.astar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author Marc
 * @param <N>
 */
public class ShortestPath<N extends Position, E> implements IShortestPath<N, E> {

    private IWeightedGraph<N, E> graph;
    private int startNode;
    private int endNode;

    private PriorityQueue<Integer> open;
    private List<Integer> closed;
    private double[] distance;
    private double[] distanceToEnd;
    private Integer[] path;
    
    private boolean pathExists;

    public static <N extends Position, E> ShortestPath<N, E> calculateFor(
            final IWeightedGraph<N, E> g, final int startNode, final int endNode) {
        return new ShortestPath<>(g, startNode, endNode);
    }

    private ShortestPath(final IWeightedGraph<N, E> g, final int startNode,
            final int endNode) {
        this.graph = g;
        this.startNode = startNode;
        this.endNode = endNode;
        aStar(graph, startNode, endNode);
    }

    private int compareDefault(final Integer uno, final Integer dos) {
        return Double.compare(distance[uno] + distanceToEnd[uno], distance[dos]
                + distanceToEnd[dos]);
    }

    @Override
    public final void aStar(final IWeightedGraph<N, E> graph,
                            final int startNode, final int endNode) {
        final Comparator<Integer> comp = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return compareDefault(o1, o2);
            }
        };
        this.open = new PriorityQueue<Integer>(graph.numberOfNodes(), comp);
        this.closed = new ArrayList<>();
        this.distance = new double[graph.numberOfNodes()];
        this.path = new Integer[graph.numberOfNodes()];
        this.distanceToEnd = getAllDistanceToEnd();

        for (int i = 0; i < graph.numberOfNodes(); i++) {
            distance[i] = Double.POSITIVE_INFINITY;
        }
        distance[startNode] = 0.0;
        this.open.offer(this.startNode);
        while (!this.open.isEmpty()) {
            final int n = this.open.poll();
            if (n == endNode) {
                pathExists = true;
                return;
            }
            expand(n);
            this.closed.add(n);
        }
    }
    
    private double[] getAllDistanceToEnd() {
        final double[] d = new double[graph.numberOfNodes()];
        for (int i = 0; i < graph.numberOfNodes(); i++) {
            d[i] = predictDistanceToEnd(i);
        }
        return d;
    }

    private void expand(final int u) {
        Iterator<IEdge<E>> edgesIterator = graph.outgoingEdges(u);
        while (edgesIterator.hasNext()) {
            IEdge<E> edge = edgesIterator.next();
            final int v = edge.getDestination();
            final double weight = edge.getWeight();
            if (closed.contains(v)) {
                continue;
            }
            final double tf = distance[u] + weight + distanceToEnd[v];
            if (open.contains(v) && tf >= distance[v] + distanceToEnd[v]) {
                continue;
            }
            path[v] = u;
            distance[v] = distance[u] + weight;
            open.add(v);
        }
    }

    private double predictDistanceToEnd(final int v) {
        N nodeMetaData = this.graph.getNodeMetaData(v);
        final int xV = nodeMetaData.getX();
        final int yV = nodeMetaData.getY();
        nodeMetaData = this.graph.getNodeMetaData(endNode);
        final int xEnd = nodeMetaData.getX();
        final int yEnd = nodeMetaData.getY();

        return Math.sqrt(Math.pow(xV - xEnd, 2) + Math.pow(yV - yEnd, 2));
    }

    @Override
    public double distance() {
        return distance[endNode];
    }

    @Override
    public boolean existsPath() {
        return pathExists;
    }

    public Iterable<IEdge<E>> path() {
        if (!existsPath()) {
            return null;
        }

        List<IEdge<E>> list = new LinkedList<>();

        int dest = endNode;

        while (path[dest] != null) {
            final Iterator<IEdge<E>> iter = graph.edgeIterator();

            while (iter.hasNext()) {
                final IEdge<E> edge = iter.next();
                final int u = edge.getSource();
                final int v = edge.getDestination();
                if (u == path[dest] && v == dest) {
                    list.add(0, edge);
                    break;
                }
            }
            dest = path[dest];
        }

        return list;
    }
    
}

package com.graph;

import java.util.*;
import java.util.function.BiFunction;

import static java.util.Collections.emptyList;

public class Graph<V> {

    private final Map<V, Vertex<V>> vertices = new HashMap<>();

    private final Type type;

    public Graph(final Type type) {
        this.type = type;
    }

    public synchronized boolean addVertex(final V vertex) {
        Objects.requireNonNull(vertex, "vertex is required");

        if (vertices.containsKey(vertex)) {
            return false;
        }

        vertices.put(vertex, new Vertex<>(vertex));
        return true;
    }

    public synchronized boolean addEdge(final V source, final V destination) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(destination);

        if (vertices.containsKey(source) && vertices.containsKey(destination)) {
            Vertex<V> src = vertices.get(source);
            Vertex<V> dest = vertices.get(destination);

            if (type == Type.UNDIRECTED) {
                return setBidirectionalBond(src, dest);
            }
            return src.adjacent.add(dest);
        }
        return false;
    }

    private boolean setBidirectionalBond(final Vertex<V> src, final Vertex<V> dest) {
        return src.adjacent.add(dest) && dest.adjacent.add(src);
    }

    public synchronized List<Edge<V>> getPath(final V source,
                                              final V destination,
                                              final BiFunction<Vertex<V>, Vertex<V>, List<Edge<V>>> traverseFunction) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(destination);

        if (vertices.containsKey(source) && vertices.containsKey(destination)) {
            Vertex<V> src = vertices.get(source);
            Vertex<V> dest = vertices.get(destination);
            return traverseFunction.apply(src, dest);
        }
        return emptyList();
    }

    public static <V> Graph<V> directed() {
        return new Graph<>(Type.DIRECTED);
    }

    public static <V> Graph<V> undirected() {
        return new Graph<>(Type.UNDIRECTED);
    }

    public enum Type {
        DIRECTED, UNDIRECTED
    }

    public static class Edge<V> {
        private final V source;
        private final V destination;

        public Edge(final V source, final V destination) {
            Objects.requireNonNull(source);
            Objects.requireNonNull(destination);

            this.source = source;
            this.destination = destination;
        }

        public V getSource() {
            return source;
        }

        public V getDestination() {
            return destination;
        }

        @Override
        public String toString() {
            return source + "->" + destination;
        }
    }

    public static class Vertex<T> {
        private final Set<Vertex<T>> adjacent = new HashSet<>();

        private final T value;

        private Vertex(final T value) {
            Objects.requireNonNull(value);
            this.value = value;
        }

        public Set<Vertex<T>> getAdjacent() {
            return adjacent;
        }

        public T getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
}

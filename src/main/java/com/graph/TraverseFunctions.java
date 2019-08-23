package com.graph;

import com.graph.Graph.Edge;
import com.graph.Graph.Vertex;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import static java.util.Collections.emptyList;

public class TraverseFunctions {

    private TraverseFunctions() {
    }

    public static <T> BiFunction<Vertex<T>, Vertex<T>, List<Edge<T>>> dfs() {
        return (source, destination) -> {
            Set<T> visitedNodes = new HashSet<>();
            LinkedList<Edge<T>> path = new LinkedList<>();
            if (hasPath(source, destination, visitedNodes, path)) {
                return path;
            }
            return emptyList();
        };
    }

    private static <T> boolean hasPath(final Vertex<T> source,
                                       final Vertex<T> destination,
                                       final Set<T> visitedNodes,
                                       final LinkedList<Edge<T>> path) {
        if (source == destination) {
            return true;
        }

        if (visitedNodes.add(source.getValue())) {
            for (final Vertex<T> adjacent : source.getAdjacent()) {
                if (hasPath(adjacent, destination, visitedNodes, path)) {
                    path.addFirst(new Edge<>(source.getValue(), adjacent.getValue()));
                    return true;
                }
            }
        }
        return false;
    }

    public static <T> BiFunction<Vertex<T>, Vertex<T>, List<Edge<T>>> bfs() {
        return (source, destination) -> {
            Set<T> visitedNodes = new HashSet<>();
            LinkedList<Edge<T>> edges = new LinkedList<>();
            LinkedList<Vertex<T>> next = new LinkedList<>();
            next.add(source);
            while (!next.isEmpty()) {
                Vertex<T> vertex = next.poll();

                if (!visitedNodes.add(vertex.getValue())) {
                    continue;
                }

                for (final Vertex<T> adjacent : vertex.getAdjacent()) {
                    if (isNotVisited(adjacent, visitedNodes)) {
                        addEdge(adjacent, vertex, edges);
                        if (adjacent == destination) {
                            return getPath(edges);
                        }
                        next.add(adjacent);
                    }
                }
            }
            return emptyList();
        };
    }

    private static <T> void addEdge(final Vertex<T> source, final Vertex<T> destination, final LinkedList<Edge<T>> edges) {
        edges.addFirst(new Edge<>(destination.getValue(), source.getValue()));
    }

    private static <T> boolean isNotVisited(final Vertex<T> vertex, final Set<T> visited) {
        return !visited.contains(vertex.getValue());
    }

    private static <T> List<Edge<T>> getPath(final LinkedList<Edge<T>> edges) {
        LinkedList<Edge<T>> path = new LinkedList<>();
        path.push(edges.poll());
        while (!edges.isEmpty()) {
            Edge<T> edge = edges.poll();
            if (hasConnection(edge, path.peekFirst())) {
                path.push(edge);
            }
        }
        return path;
    }

    private static <T> boolean hasConnection(final Edge<T> from, final Edge<T> to) {
        return from.getDestination().equals(to.getSource());
    }
}

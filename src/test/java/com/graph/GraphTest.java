package com.graph;

import com.graph.Graph.Edge;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

@Execution(CONCURRENT)
class GraphTest {

    @DisplayName("Add vertex")
    @Test
    void testAddVertex() {
        Graph<Integer> graph = Graph.directed();

        assertTrue(graph.addVertex(1));
        assertTrue(graph.addVertex(2));
        assertFalse(graph.addVertex(1));
    }

    @DisplayName("Add edge in directed graph")
    @Test
    void testAddEdgeInDirectedGraph() {
        Graph<Integer> graph = Graph.directed();

        graph.addVertex(1);
        graph.addVertex(2);

        assertTrue(graph.addEdge(1, 2));
        assertTrue(graph.addEdge(2, 1));
        assertFalse(graph.addEdge(1, 2));
        assertFalse(graph.addEdge(4, 3));
    }

    @DisplayName("Add edge in undirected graph")
    @Test
    void testAddEdgeInUndirectedGraph() {
        Graph<Integer> graph = Graph.undirected();

        graph.addVertex(1);
        graph.addVertex(2);

        assertTrue(graph.addEdge(1, 2));
        assertFalse(graph.addEdge(2, 1));
    }

    @DisplayName("Find path in directed graph using DFS")
    @Test
    void testFindPathInDirectedGraphUsingDfs() {
        Graph<Integer> graph = Graph.directed();
        fill(graph);

        graph.addEdge(1, 2);
        graph.addEdge(2, 1);
        graph.addEdge(2, 3);
        graph.addEdge(2, 5);
        graph.addEdge(2, 6);
        graph.addEdge(3, 1);
        graph.addEdge(3, 2);
        graph.addEdge(3, 7);
        graph.addEdge(4, 3);
        graph.addEdge(4, 8);
        graph.addEdge(5, 9);
        graph.addEdge(3, 9);
        graph.addEdge(9, 3);

        List<Edge<Integer>> path = graph.getPath(4, 5, TraverseFunctions.dfs());
        assertThat(path.toString(), anyOf(is("[4->3, 3->2, 2->5]"), is("[4->3, 3->1, 1->2, 2->5]")));
    }

    @DisplayName("Find path in undirected graph using DFS")
    @Test
    void testFindPathInUndirectedGraphUsingDfs() {
        Graph<Integer> graph = Graph.undirected();
        fill(graph);

        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(2, 5);
        graph.addEdge(2, 6);
        graph.addEdge(3, 4);
        graph.addEdge(3, 7);
        graph.addEdge(4, 8);
        graph.addEdge(5, 9);

        List<Edge<Integer>> path = graph.getPath(4, 5, TraverseFunctions.dfs());
        assertThat(path.toString(), anyOf(is("[4->3, 3->2, 2->5]"), is("[4->3, 3->1, 1->2, 2->5]")));
    }

    @DisplayName("Find path in directed graph using BFS")
    @Test
    void testFindPathInDirectedGraphUsingBfs() {
        Graph<Integer> graph = Graph.directed();
        fill(graph);

        graph.addEdge(1, 2);
        graph.addEdge(3, 9);
        graph.addEdge(4, 3);
        graph.addEdge(4, 8);
        graph.addEdge(7, 5);
        graph.addEdge(7, 6);
        graph.addEdge(7, 7);
        graph.addEdge(7, 8);
        graph.addEdge(8, 1);
        graph.addEdge(8, 2);
        graph.addEdge(8, 7);
        graph.addEdge(9, 3);

        List<Edge<Integer>> path = graph.getPath(4, 5, TraverseFunctions.bfs());
        assertThat(path.toString(), is("[4->8, 8->7, 7->5]"));
    }

    @DisplayName("Find path in undirected graph using BFS")
    @Test
    void testFindPathInUndirectedGraphUsingBfs() {
        Graph<Integer> graph = Graph.undirected();
        fill(graph);

        graph.addEdge(1, 8);
        graph.addEdge(7, 2);
        graph.addEdge(7, 5);
        graph.addEdge(7, 6);
        graph.addEdge(7, 8);
        graph.addEdge(3, 4);
        graph.addEdge(3, 9);
        graph.addEdge(4, 8);

        List<Edge<Integer>> path = graph.getPath(4, 5, TraverseFunctions.bfs());
        assertThat(path.toString(), is("[4->8, 8->7, 7->5]"));
    }

    @DisplayName("Find path in directed graph when it doesn't exist")
    @Test
    void testFindPathInDirectedGraphWhenItNotExist() {
        Graph<Integer> graph = Graph.undirected();

        List<Edge<Integer>> path = graph.getPath(4, 5, TraverseFunctions.dfs());
        assertThat(path, is(empty()));
    }

    private void fill(final Graph<Integer> graph) {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);
        graph.addVertex(9);
    }
}

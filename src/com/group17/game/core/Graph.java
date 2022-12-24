package com.group17.game.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Models the graph structure of a level. Contains methods to generate paths for smart enemies.
 *
 * @author Vlad Kashtelyanov
 * @version 2.0
 */
public class Graph implements Serializable {
    private final HashMap<Node, LinkedList<Node>> adjMap;
    private final LinkedList<Node> pathList = new LinkedList<>();

    /**
     * Graph constructor.
     */
    public Graph() {
        adjMap = new HashMap<>();
    }

    /**
     * Creates an edge on the graph.
     *
     * @param source      edge start node.
     * @param destination edge end node.
     */
    public void addEdge(Node source, Node destination) {
        if (!adjMap.containsKey(source)) {
            adjMap.put(source, null);
        }

        if (!adjMap.containsKey(destination)) {
            adjMap.put(destination, null);
        }

        addEdgeHelper(source, destination);
        addEdgeHelper(destination, source);
    }

    /**
     * Find the shortest path between two points on the graph.
     *
     * @param start path start point.
     * @param goal  path end point.
     * @return linked list of nodes in the shortest path.
     */
    public LinkedList<Node> findShortestPath(Node start, Node goal) {
        if (start != null) {
            findShortestPathRecursive(start, goal);
            if (pathList.getLast().n != start.n) {
                return null;
            } else {
                return pathList;
            }
        } else {
            return null;
        }
    }

    /**
     * Breadth first search algorithm that searches for a specified node.
     *
     * @param node node to be searched for.
     */
    public void breadthFirstSearch(Node node) {
        if (node == null) {
            return;
        }

        LinkedList<Node> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            Node currentFirst = queue.removeFirst();

            if (currentFirst.isVisited()) {
                continue;
            }

            currentFirst.visit();

            LinkedList<Node> allNeighbors = adjMap.get(currentFirst);

            if (allNeighbors == null) {
                continue;
            }

            for (Node neighbor : allNeighbors) {
                if (!neighbor.isVisited()) {
                    queue.add(neighbor);
                    neighbor.setPrev(currentFirst);
                }
            }
        }
    }

    private void addEdgeHelper(Node a, Node b) {
        LinkedList<Node> tmp = adjMap.get(a);

        if (tmp != null) {
            tmp.remove(b);
        } else {
            tmp = new LinkedList<>();
        }
        tmp.add(b);
        adjMap.put(a, tmp);
    }

    private void findShortestPathRecursive(Node start, Node goal) {
        if (start != null) {
            pathList.add(goal);
            while (!goal.prevVisited && !goal.n.equals(start.n)) {
                goal.prevVisited = true;
                if (goal.prev != null) {
                    findShortestPathRecursive(start, goal.prev);
                }
            }
        }
    }
}


/*
* A collection of graph algorithms.
 */
package graphbase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author DEI-ESINF
 */
public class GraphAlgorithms {

    /**
     * Performs breadth-first search of a Graph starting in a Vertex
     *
     * @param g Graph instance
     * @param vert information of the Vertex that will be the source of the
     * search
     * @return qbfs a queue with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> BreadthFirstSearch(Graph<V, E> g, V vert) {
        LinkedList<V> qbfs = new LinkedList<>();
        ArrayList<V> queue = new ArrayList<>();
        if (!g.validVertex(vert)) {
            return null;
        }
        queue.add(vert);
        qbfs.add(vert);
        for (int i = 0; i < queue.size(); i++) {
            V v = queue.get(i);
            for (Edge<V, E> edge : g.outgoingEdges(v)) {
                if (!qbfs.contains(edge.getVDest())) {
                    queue.add(edge.getVDest());
                    qbfs.add(edge.getVDest());
                }
            }
        }
        return qbfs;
    }

    /**
     * Performs depth-first search starting in a Vertex
     *
     * @param g Graph instance
     * @param vOrig Vertex of graph g that will be the source of the search
     * @param visited set of discovered vertices
     * @param qdfs queue with vertices of depth-first search
     */
    private static <V, E> void DepthFirstSearch(Graph<V, E> g, V vOrig, LinkedList<V> qdfs) {
        qdfs.add(vOrig);
        for (V v : g.adjVertices(vOrig)) {
            if (!qdfs.contains(v)) {
                DepthFirstSearch(g, v, qdfs);
            }
        }
    }

    /**
     * @param g Graph instance
     * @param vInf information of the Vertex that will be the source of the
     * search
     * @return qdfs a queue with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {
        if (!g.validVertex(vert)) {
            return null;
        }
        LinkedList<V> qdfs = new LinkedList<>();
        DepthFirstSearch(g, vert, qdfs);
        return qdfs;
    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param g Graph instance
     * @param vOrig Vertex that will be the source of the path
     * @param vDest Vertex that will be the end of the path
     * @param visited set of discovered vertices
     * @param path stack with vertices of the current path (the path is in
     * reverse order)
     * @param paths ArrayList with all the paths (in correct order)
     */
    private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, LinkedList<V> visited,
            LinkedList<V> path, ArrayList<LinkedList<V>> paths) {
        path.push(vOrig);
        visited.add(vOrig);
        if (vOrig != vDest) {
            for (V v : g.adjVertices(vOrig)) {
                if (!visited.contains(v)) {
                    allPaths(g, v, vDest, visited, path, paths);
                }
            }
        }
        if (vOrig == vDest) {
            paths.add(path);
        }
        visited.remove(vOrig);
        path.remove(vOrig);
    }

    /**
     * @param g Graph instance
     * @param voInf information of the Vertex origin
     * @param vdInf information of the Vertex destination
     * @return paths ArrayList with all paths from voInf to vdInf
     */
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {
        if (!g.validVertex(vDest)) {
            return null;
        }
        LinkedList<V> visited = new LinkedList<V>();
        LinkedList<V> path = new LinkedList<V>();
        ArrayList<LinkedList<V>> paths = new ArrayList();
        allPaths(g, vOrig, vDest, visited, path, paths);
        return paths;
    }

    /**
     * Computes shoro all reachabltest-path distance from a source vertex te
     * vertices of a graph g with nonnegative edge weights This implementation
     * uses Dijkstra's algorithm
     *
     * @param g Graph instance
     * @param vOrig Vertex that will be the source of the path
     * @param visited set of discovered vertices
     * @param pathkeys minimum path vertices keys
     * @param dist minimum distances
     */
    protected static <V, E> void shortestPathLength(Graph<V, E> g, V vOrig, V[] vertices,
            boolean[] visited, int[] pathKeys, double[] dist) {

        int vKey = g.getKey(vOrig);
        System.out.println("-------   "+vKey+" -------------");
        dist[vKey] = 0;
        while (vKey != -1) {
            vOrig = vertices[vKey];
            visited[vKey] = true;
            for (V vAdj : g.adjVertices(vOrig)) {
                Edge<V, E> edge = g.getEdge(vOrig, vAdj);
                int vKeyAdj = g.getKey(vAdj);
                if (!visited[vKeyAdj] && dist[vKeyAdj] > dist[vKey] + edge.getWeight()) {
                    dist[vKeyAdj] = dist[vKey] + edge.getWeight();
                    pathKeys[vKeyAdj] = vKey;
                }
            }

            double minDist = Double.MAX_VALUE;
            vKey = -1;
            for (int i = 0; i < g.numVertices(); i++) {
                if (!visited[i] && dist[i] <= minDist) {
                    minDist = dist[i];
                    vKey = i;
                }
            }
        }
    }

    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf The path
     * is constructed from the end to the beginning
     *
     * @param g Graph instance
     * @param voInf information of the Vertex origin
     * @param vdInf information of the Vertex destination
     * @param pathkeys minimum path vertices keys
     * @param path stack with the minimum path (correct order)
     */
    protected static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest, V[] verts, int[] pathKeys, LinkedList<V> path) {
        if (!vOrig.equals(vDest)) {
            path.push(vDest);
            int vKey = g.getKey(vDest);
            int vPrvKey = pathKeys[vKey];
            vDest = verts[vPrvKey];
            getPath(g, vOrig, vDest, verts, pathKeys, path);
        } else {
            path.push(vOrig);
        }
    }

    //shortest-path between vOrig and vDest
    public static <V, E> double shortestPath(Graph<V, E> g, V vOrig, V vDest, LinkedList<V> shortPath) {
        int nVerts = g.numVertices();
        double[] dist = new double[nVerts];
        int[] pathKeys = new int[nVerts];
        boolean[] visited = new boolean[nVerts]; //default value = false
        V[] vertices = g.allkeyVerts();

        if (!(g.validVertex(vDest) && g.validVertex(vOrig))) {
            return 0;
        }

        for (int i = 0; i < nVerts; i++) {
            dist[i] = Double.MAX_VALUE;
            pathKeys[i] = -1;
        }

        shortestPathLength(g, vOrig, vertices, visited, pathKeys, dist);

        if (dist[g.getKey(vDest)] == Double.MAX_VALUE) {
            return 0;
        }

        getPath(g, vOrig, vDest, vertices, pathKeys, shortPath);
        
        return dist[g.getKey(vDest)];
    }

    //shortest-path between voInf and all other
    public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig, ArrayList<LinkedList<V>> paths, ArrayList<Double> dists) {

        if (!g.validVertex(vOrig)) {
            return false;
        }

        int nverts = g.numVertices();
        boolean[] visited = new boolean[nverts]; //default value: false
        int[] pathKeys = new int[nverts];
        double[] dist = new double[nverts];
        V[] vertices = g.allkeyVerts();

        for (int i = 0; i < nverts; i++) {
            dist[i] = Double.MAX_VALUE;
            pathKeys[i] = -1;
        }

        shortestPathLength(g, vOrig, vertices, visited, pathKeys, dist);
        dists.clear();
        paths.clear();
        for (int i = 0; i < nverts; i++) {
            paths.add(null);
            dists.add(null);
        }
        for (int i = 0; i < nverts; i++) {
            LinkedList<V> shortPath = new LinkedList<>();
            if (dist[i] != Double.MAX_VALUE) {
                getPath(g, vOrig, vertices[i], vertices, pathKeys, shortPath);
            }
            paths.set(i, shortPath);
            dists.set(i, dist[i]);
        }
        return true;
    }

    /**
     * Reverses the path
     *
     * @param path stack with path
     */
    private static <V, E> LinkedList<V> revPath(LinkedList<V> path) {

        LinkedList<V> pathcopy = new LinkedList<>(path);
        LinkedList<V> pathrev = new LinkedList<>();

        while (!pathcopy.isEmpty()) {
            pathrev.push(pathcopy.pop());
        }

        return pathrev;
    }
}

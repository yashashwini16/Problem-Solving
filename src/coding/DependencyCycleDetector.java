package coding;

import java.util.List;
import java.util.Map;

/**
 * PROBLEM 4: Graph Cycle Detection in Microservice Dependencies
 * 
 * Given a directed graph representing microservice configurations where an edge A -> B 
 * means service A depends on service B, write a method to detect if there is a cyclic 
 * dependency (e.g., A depends on B, B depends on C, and C depends on A).
 * 
 * If a cycle exists, any deployment or upgrade chain could cause a deadlock or infinite loop.
 */
public class DependencyCycleDetector {

    /**
     * Detects if the given dependency graph contains any cycles.
     * 
     * @param adjList Graph represented as an adjacency list: Key -> list of dependencies.
     *                For example, {"A": ["B"], "B": ["C"], "C": ["A"]} contains a cycle A -> B -> C -> A.
     * @return true if a cycle is detected, false otherwise.
     */
    public static boolean hasCycle(Map<String, List<String>> adjList) {
        // TODO: Implement cycle detection in a directed graph.
        // Tip: You can use Depth First Search (DFS) with a recursion stack or state tracking.
        // A node can be in three states: UNVISITED, VISITING (currently in recursion stack), and VISITED.
        // A cycle is detected if we encounter a node that is currently in the VISITING state.

        return false;
    }
}

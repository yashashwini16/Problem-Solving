package coding.solutions;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class DependencyCycleDetectorSolution {

    /**
     * DFS-based cycle detection in a directed graph.
     * Time Complexity: O(V + E) where V is vertices (services) and E is edges (dependencies).
     * Space Complexity: O(V) for visited and recursion states.
     */
    public static boolean hasCycle(Map<String, List<String>> adjList) {
        if (adjList == null) {
            return false;
        }

        Set<String> visiting = new HashSet<>();
        Set<String> visited = new HashSet<>();

        // Check for cycles starting from each node in the graph
        for (String node : adjList.keySet()) {
            if (dfs(node, adjList, visiting, visited)) {
                return true;
            }
        }
        
        return false;
    }

    private static boolean dfs(String node, Map<String, List<String>> adjList, 
                               Set<String> visiting, Set<String> visited) {
        // If the node is already in the recursion stack of the current DFS path, a cycle is found
        if (visiting.contains(node)) {
            return true;
        }

        // If the node has already been completely processed, no need to traverse again
        if (visited.contains(node)) {
            return false;
        }

        // Mark node as visiting (push to stack)
        visiting.add(node);

        List<String> dependencies = adjList.get(node);
        if (dependencies != null) {
            for (String neighbor : dependencies) {
                if (dfs(neighbor, adjList, visiting, visited)) {
                    return true;
                }
            }
        }

        // Done visiting this node, remove from stack and mark as visited
        visiting.remove(node);
        visited.add(node);
        
        return false;
    }
}

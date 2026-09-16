package coding.solutions;

public class CabRouteRotatorSolution {

    public static void rotate(String[] routes, int k) {
        if (routes == null || routes.length <= 1) {
            return;
        }

        int n = routes.length;
        k = k % n;
        if (k < 0) {
            k = k + n; // Convert left rotation to equivalent right rotation
        }

        if (k == 0) {
            return;
        }

        // Three-step reversal:
        // 1. Reverse entire array
        reverse(routes, 0, n - 1);
        // 2. Reverse first k elements
        reverse(routes, 0, k - 1);
        // 3. Reverse remaining n-k elements
        reverse(routes, k, n - 1);
    }

    private static void reverse(String[] arr, int start, int end) {
        while (start < end) {
            String temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
}

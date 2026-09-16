package coding;

/**
 * PROBLEM 3: Rotate Cab Route Identifiers
 * 
 * Rotate an array of cab route identifiers by N positions efficiently without using extra memory (in-place).
 * Rotation is rightward: rotating [A, B, C, D] by 1 results in [D, A, B, C].
 * 
 * Requirements:
 * - Time Complexity: O(N)
 * - Space Complexity: O(1) auxiliary space (in-place)
 * - Must handle N values larger than the array length, and negative N values (leftward rotation).
 */
public class CabRouteRotator {

    /**
     * Rotates the route identifiers array by k positions in-place.
     * 
     * @param routes Array of cab route identifiers
     * @param k      Number of positions to rotate (positive for right, negative for left)
     */
    public static void rotate(String[] routes, int k) {
        if (routes == null || routes.length <= 1) {
            return;
        }
        // TODO: Implement the in-place rotation algorithm.
        // Tip: You can achieve this in O(N) time and O(1) space using three array reversals.
        // 1. Normalize k: k = k % length. If k is negative, convert it to equivalent positive rotation.
        int n=routes.length;
        //if k>n, for ex: if k=5 then rotating the k 5 times is same as rotating 1 times
        k=k%n;
        if(k<0){
            //If k is negative, convert it to equivalent positive rotation.
            k=k+n;
        }
        // 2. Reverse the entire array.
        reverse(routes,0,n-1);
        // 3. Reverse the first k elements.
        reverse(routes,0,k-1);
        // 4. Reverse the remaining elements.
        reverse(routes,k,n-1);
    }

    public static void reverse(String[] arr,int start,int end){
        while(start<end){
            String temp=arr[start];
            arr[start]=arr[end];
            arr[end]=temp;
            start++;
            end--;
        }
    }
}

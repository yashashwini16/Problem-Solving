//42. Trapping Rain Water
class Solution {
    public int trap(int[] height) {
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int water = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                // Left wall is the bottleneck
                leftMax = Math.max(leftMax, height[left]);
                water += leftMax - height[left]; // Water above this bar
                left++;
            } else {
                // Right wall is the bottleneck
                rightMax = Math.max(rightMax, height[right]);
                water += rightMax - height[right];
                right--;
            }
        }

        return water;
    }
}

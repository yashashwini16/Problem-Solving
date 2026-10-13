#leetcode-209 Minimum Size Subarray Sum

class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int sum = 0;
        int minLen = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            // 1. EXPAND: Add current element to window sum
            sum += nums[right];

            // 2. SHRINK: While window is VALID (sum >= target), record and shrink
            while (sum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
}

// 15. 3Sum

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
         List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums); // Step 1: Sort
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicate fixed elements
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            // Early termination: if smallest number > 0, no triplet possible
            if (nums[i] > 0) break;
            int target = -nums[i];
            int left = i + 1, right = nums.length - 1;
            // Step 2: Two Sum II on the remaining subarray
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // Skip duplicates for left and right
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    
        
    }
}

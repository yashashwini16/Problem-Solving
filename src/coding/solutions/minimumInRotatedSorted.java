//153. Find Minimum in Rotated Sorted Array
// here left < right (not left<=right)
//here right=mid; (bcz minimum can be mid or in LEFT)

class Solution {
    public int findMin(int[] nums) {
        int left=0, right=nums.length-1;

        while(left<right){
            int mid=left+(right-left)/2;

            if(nums[mid]>nums[right]){
                left=mid+1;
            }else{
                right=mid;   //not mid-1 minimum is 
            }
        }
        return nums[left]; //left==right==always minimum
        
    }
}
